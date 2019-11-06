package com.sgm.iorecord

import android.Manifest
import android.app.ActivityManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import com.sgm.iorecord.adapter.IOListAdapter
import com.sgm.iorecord.alarm.IORecordService
import com.sgm.iorecord.base.BaseActivity
import com.sgm.iorecord.chart.ChartActivity
import com.sgm.iorecord.databases.DataEngine
import com.sgm.iorecord.event.RXLoadIoTopAllEvent
import com.sgm.iorecord.event.rx.RxBus
import com.sgm.iorecord.model.IOTopBean
import com.sgm.iorecord.model.ProcessInfo
import com.sgm.iorecord.useCase.SimpleUseCaseCallBack
import com.sgm.iorecord.useCase.main.GetPIDTask
import com.sgm.iorecord.utils.Lg
import com.sgm.iorecord.utils.Utils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.databases_layout.*
import java.io.File
import java.util.*
import java.util.concurrent.Executors

class MainActivity : BaseActivity(), MainContract.View, View.OnClickListener {

    var TAG: String? = "MainActivity"

    var mPresenter: MainPresenter? = null
    var mAdapter: IOListAdapter? = IOListAdapter()
    var mList: MutableList<IOTopBean>? = mutableListOf()
    private val BACK_UP_FILE = (Environment.getExternalStorageDirectory().path + File.separator + "iorecord")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "IOTOP Test Panel"
        mPresenter = MainPresenter(this)

        m_register_bt.setOnClickListener(this)
        m_execute_bt.setOnClickListener(this)
        m_insert_bt.setOnClickListener(this)
        m_query_bt.setOnClickListener(this)
        m_fd_bt.setOnClickListener(this)
        m_pid_thread_bt.setOnClickListener(this)
        m_chart_bt.setOnClickListener(this)
        m_memory_bt.setOnClickListener(this)

        mAdapter!!.setData(mList)
        m_record_rlv.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = mAdapter
        }
        reqPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)

        //Regist queryAll callBack
        RxBus.get().doSubscribe(RXLoadIoTopAllEvent::class.java, {
            mList!!.clear()
            mList!!.addAll(it.ioTopBeans)
            showToast("Query succeed size:" + mList!!.size)
            mAdapter!!.setData(mList)
            mAdapter!!.notifyDataSetChanged()
        }, {
            it.printStackTrace()
        })

        mPresenter!!.registerTimeTicketReceiver()

        val recordService = Intent(this, IORecordService::class.java)
        startService(recordService)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.m_register_bt -> {
                val infos = requireAllProcess(getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager?)
                for (i in infos) {
                    Lg.d(TAG, "pid:${i.pid}-name:${i.processName}")
                }
                //mPresenter?.registerService()
            }
            R.id.m_execute_bt -> mPresenter?.requireIOAndDBAsync("sh $BACK_UP_FILE/iotop.sh", false)
            R.id.m_chart_bt -> startActivity(Intent(this@MainActivity, ChartActivity::class.java))
            R.id.m_insert_bt -> mPresenter?.insertIOList(DataEngine.gerInstance().createIOTopList(5))
            R.id.m_query_bt -> mPresenter?.queryAll()
            R.id.m_fd_bt -> {
                mPresenter!!.getProcessInfo(object : SimpleUseCaseCallBack<GetPIDTask.ResponseValue>() {
                    override fun onSuccess(response: GetPIDTask.ResponseValue?) {
                        mPresenter!!.requireFdNumByProcess(response!!.ioTopBeans)
                    }
                })
            }
            R.id.m_pid_thread_bt -> {
                mPresenter!!.getProcessInfo(object : SimpleUseCaseCallBack<GetPIDTask.ResponseValue>() {
                    override fun onSuccess(response: GetPIDTask.ResponseValue?) {
                        mPresenter!!.requireThreadNumByProcess(response!!.ioTopBeans)
                    }
                })
            }
            R.id.m_memory_bt -> {
                mPresenter!!.getProcessInfo(object : SimpleUseCaseCallBack<GetPIDTask.ResponseValue>() {
                    override fun onSuccess(response: GetPIDTask.ResponseValue?) {
                        mPresenter!!.requireProcessMemoryInfo(response!!.ioTopBeans)
                    }
                })
            }
        }
    }

    override fun registerMyReceiver(receiver: BroadcastReceiver?, filter: IntentFilter?) {
        if (receiver!!.abortBroadcast) {
            unregisterReceiver(receiver)
        }
        registerReceiver(receiver, filter)
    }

    override fun startMyService(service: Intent?) {
        startService(service)
    }

    private val REQUEST_CODE_ASK_STORAGE = 1

    private fun reqPermission(vararg pms: String) {
        for (premission in pms) {
            if (ContextCompat.checkSelfPermission(applicationContext, premission) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, premission)) {
                    AlertDialog.Builder(this)
                            .setTitle("提示")
                            .setMessage("需要开启存储的权限，是否继续？")
                            .setPositiveButton("确定") { _, _ ->
                                ActivityCompat.requestPermissions(this@MainActivity,
                                        arrayOf(premission), REQUEST_CODE_ASK_STORAGE)
                            }
                            .setNegativeButton("取消") { dialog, _ -> dialog.dismiss() }.show()
                } else {
                    //请求权限
                    ActivityCompat.requestPermissions(this,
                            arrayOf(premission), REQUEST_CODE_ASK_STORAGE)
                }
            } else {
                if (premission == Manifest.permission.WRITE_EXTERNAL_STORAGE) {
                    initAssets()
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE_ASK_STORAGE -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "授权同意：$grantResults[0]")
                initAssets()
            } else {
                Toast.makeText(this@MainActivity, "拒绝授权！", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initAssets() {
        Executors.newSingleThreadExecutor().submit { Utils.copyFileFromAssets(applicationContext, "iotop.sh", BACK_UP_FILE) }
    }

    override fun onDestroy() {
        super.onDestroy()
        RxBus.get().unSubscription(RXLoadIoTopAllEvent::class.java)
    }

    fun requireAllProcess(activityManager: ActivityManager?): List<ProcessInfo> {
        val infos = ArrayList<ProcessInfo>()
        if (activityManager != null) {
            var processInfo: ProcessInfo
            val runningAppProcesses = activityManager.runningAppProcesses
            for (info in runningAppProcesses) {
                processInfo = ProcessInfo(info.pid, info.processName)
                infos.add(processInfo)
            }
        }
        return infos
    }
}
