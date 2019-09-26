package com.sgm.iorecord

import android.app.IProcessObserver
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.sgm.iorecord.databases.DataEngine
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.databases_layout.*

class MainActivity : AppCompatActivity(), MainContract.View, View.OnClickListener {

    var mPresenter: MainPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mPresenter = MainPresenter(this)

        m_register_bt.setOnClickListener({
            registerService()
        })

        m_insert_bt.setOnClickListener(this)
        m_query_bt.setOnClickListener(this)
        m_delete_bt.setOnClickListener(this)
        m_update_bt.setOnClickListener(this)


    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.m_insert_bt -> mPresenter?.insertIOData(DataEngine.gerInstance().createIOBean())
            R.id.m_delete_bt -> showToast("doing")
            R.id.m_update_bt -> showToast("doing")
            R.id.m_query_bt -> {
                mPresenter?.queryAll()

            }
        }
    }

    override fun showToast(str: String?) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
    }


    private val mProcessObserver = object : IProcessObserver.Stub() {
        override fun onProcessDied(pid: Int, uid: Int) {
            Log.d("picher", String.format("onProcessDied ->> pid:%s，uid：%s", pid, uid))
        }

        override fun onForegroundActivitiesChanged(pid: Int, uid: Int, foregroundActivities: Boolean) {
            Log.d("picher", String.format("onForegroundActivitiesChanged ->> pid:%s，uid：%s，foregroundActivities：%s",
                    pid, uid, foregroundActivities))
        }
    }

    /**
     * 监听进程被杀消息，在进程被杀后记录该进程proc/{pid}/io下的数据
     */
    private fun registerService() {
        try {
            val activityManagerNative = Class.forName("android.app.ActivityManagerNative")
            val getDefaultMethod = activityManagerNative.getMethod("getDefault")
            val iActivityManager = getDefaultMethod.invoke(null)//null as Array<Any>?, null as Array<Any>?
            if (iActivityManager != null) {
                val registerMethod = activityManagerNative.getMethod("registerProcessObserver", IProcessObserver::class.java)
                registerMethod.invoke(iActivityManager, mProcessObserver)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}
