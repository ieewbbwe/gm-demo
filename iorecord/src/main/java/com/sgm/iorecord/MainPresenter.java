package com.sgm.iorecord;

import android.app.IProcessObserver;
import android.os.RemoteException;
import android.util.Log;

import com.sgm.iorecord.bean.IOBean;
import com.sgm.iorecord.databases.DbController;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by s2s8tb on 2019/9/26.
 */

public class MainPresenter implements MainContract.Persenter {

    private MainContract.View mView;
    private static final String TAG = "MainPresenter";

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
        return Utils.executeShell(shell);
    }

    @Override
    public CommandExecution.CommandResult executeShell(String shell, boolean isRoot) {
        CommandExecution.CommandResult result = CommandExecution.execCommand(shell, isRoot);
        Log.d(TAG, String.format("errorMsg:%s\nsuccessMsg:%s\nresult:%s\n",
                result.errorMsg, result.successMsg, result.result));
        return result;
    }

    /**
     * 注册监听应用被杀掉的服务
     */
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
