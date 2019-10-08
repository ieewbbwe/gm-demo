package com.sgm.iorecord

import android.app.IProcessObserver
import android.util.Log
import com.sgm.iorecord.bean.IOBean
import com.sgm.iorecord.databases.DbController

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.concurrent.Executors

/**
 * Created by s2s8tb on 2019/9/26.
 */

class MainPresenter(private val mView: MainContract.View) : MainContract.Persenter {

    override fun registerTimeRefreshService() {

    }

    override fun registerProcessListener() {

    }

    override fun insertIOList(ioBeans: List<IOBean>) {
        DbController.getInstance().session.startAsyncSession().insertInTx(IOBean::class.java, ioBeans)
    }

    override fun insertIOData(ioBean: IOBean) {
        val l = DbController.getInstance().session.ioBeanDao.insert(ioBean)
        mView.showToast("插入成功：" + l)
    }

    override fun queryById(id: String): IOBean? {
        return null
    }

    override fun queryAll(): List<IOBean> {
        return DbController.getInstance().session.ioBeanDao.loadAll()
    }

    override fun executeShell(shell: String): String? {
        val mRuntime = Runtime.getRuntime()
        try {
            //Process中封装了返回的结果和执行错误的结果
            val mProcess = mRuntime.exec(shell)
            val mReader = BufferedReader(InputStreamReader(mProcess.inputStream))
            val mRespBuff = StringBuffer()
            val buff = CharArray(1024)
            var ch = 0
            while ({ ch = mReader.read(buff);ch; }() != -1) {
                mRespBuff.append(buff, 0, ch)
            }
            mReader.close()
            print("result1" + mRespBuff.toString())
            return mRespBuff.toString()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null
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
    override fun registerService() {
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
