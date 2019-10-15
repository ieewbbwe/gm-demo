package com.sgm.iorecord

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import com.sgm.iorecord.adapter.IOListAdapter
import com.sgm.iorecord.base.BaseActivity
import com.sgm.iorecord.chart.ChartActivity
import com.sgm.iorecord.databases.DataEngine
import com.sgm.iorecord.event.RXLoadIoTopAllEvent
import com.sgm.iorecord.event.rx.RxBus
import com.sgm.iorecord.model.IOTopBean
import com.sgm.iorecord.utils.Utils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.databases_layout.*
import java.io.File
import java.util.concurrent.Executors

class MainActivity : BaseActivity(), MainContract.View, View.OnClickListener {

    override fun hideLoading() {
        showToast("加载完成！")
    }

    override fun showLoading() {
        showToast("Loadding...")
    }

    var mPresenter: MainPresenter? = null
    var mAdapter: IOListAdapter? = IOListAdapter()
    var mList: MutableList<IOTopBean>? = mutableListOf()
    private val BACK_UP_FILE = (Environment.getExternalStorageDirectory().path + File.separator + "iorecord")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mPresenter = MainPresenter(this)

        m_register_bt.setOnClickListener(this)
        m_execute_bt.setOnClickListener(this)
        m_insert_bt.setOnClickListener(this)
        m_query_bt.setOnClickListener(this)
        m_delete_bt.setOnClickListener(this)
        m_update_bt.setOnClickListener(this)
        m_chart_bt.setOnClickListener(this)

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
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.m_register_bt -> mPresenter?.registerService()
            R.id.m_execute_bt -> mPresenter?.executeShellAndDBAsync("sh $BACK_UP_FILE/iotop.sh", false)
            R.id.m_chart_bt -> startActivity(Intent(this@MainActivity, ChartActivity::class.java))
            R.id.m_insert_bt -> mPresenter?.insertIoTopBean(DataEngine.gerInstance().createIOTopBean())
            R.id.m_delete_bt -> showToast("doing")
            R.id.m_update_bt -> showToast("doing")
            R.id.m_query_bt -> mPresenter?.queryAll()
        }
    }

    override fun showToast(str: String?) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
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
                Log.d("picher", "授权同意：$grantResults[0]")
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
}
