package com.sgm.iorecord;

import android.app.IProcessObserver;
import android.os.RemoteException;
import android.util.Log;

import com.sgm.iorecord.bean.IOBean;
import com.sgm.iorecord.databases.DbController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by s2s8tb on 2019/9/26.
 */

public class MainPresenter implements MainContract.Persenter {

    private MainContract.View mView;

    public MainPresenter(MainContract.View view) {
        this.mView = view;
    }

    @Override
    public void registerTimeRefreshService() {

    }

    @Override
    public void registerProcessListener() {

    }

    @Override
    public void insertIOList(List<IOBean> ioBeans) {
        DbController.getInstance().getSession().startAsyncSession().insertInTx(IOBean.class, ioBeans);
    }

    @Override
    public void insertIOData(IOBean ioBean) {
        long l = DbController.getInstance().getSession().getIOBeanDao().insert(ioBean);
        mView.showToast("插入成功：" + l);
    }

    @Override
    public IOBean queryById(String id) {
        return null;
    }

    @Override
    public List<IOBean> queryAll() {
        return DbController.getInstance().getSession().getIOBeanDao().loadAll();
    }

    @Override
    public String executeShell(String shell) {
        Runtime mRuntime = Runtime.getRuntime();
        try {
            //Process中封装了返回的结果和执行错误的结果
            Process mProcess = mRuntime.exec(shell);
            BufferedReader mReader = new BufferedReader(new InputStreamReader(mProcess.getInputStream()));
            StringBuffer mRespBuff = new StringBuffer();
            char[] buff = new char[1024];
            int ch = 0;
            while ((ch = mReader.read(buff)) != -1) {
                mRespBuff.append(buff, 0, ch);
            }
            mReader.close();
            System.out.print("result1" + mRespBuff.toString());
            return mRespBuff.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void registerService() {
        try {
            Class activityManagerNative = Class.forName("android.app.ActivityManagerNative");
            Method getDefaultMethod = activityManagerNative.getMethod("getDefault");
            Object iActivityManager = getDefaultMethod.invoke(null);//null as Array<Any>?, null as Array<Any>?
            if (iActivityManager != null) {
                Method registerMethod = activityManagerNative.getMethod("registerProcessObserver", IProcessObserver.class);
                registerMethod.invoke(iActivityManager, mProcessObserver);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private IProcessObserver mProcessObserver = new IProcessObserver.Stub() {
        @Override
        public void onForegroundActivitiesChanged(int pid, int uid, boolean foregroundActivities) throws RemoteException {
            Log.d("picher", String.format("onForegroundActivitiesChanged ->> pid:%s，uid：%s", pid, uid));
        }

        @Override
        public void onProcessDied(int pid, int uid) throws RemoteException {
            Log.d("picher", String.format("onProcessDied ->> pid:%s，uid：%s", pid, uid));

        }
    };
}
