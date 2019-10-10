package com.sgm.iorecord;

import android.annotation.SuppressLint;
import android.app.IProcessObserver;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;

import com.sgm.iorecord.base.BasePresenter;
import com.sgm.iorecord.bean.IOBean;
import com.sgm.iorecord.bean.IOTopBean;
import com.sgm.iorecord.chart.ChartContract;
import com.sgm.iorecord.databases.DbController;
import com.sgm.iorecord.listener.IShellCallBack;

import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by s2s8tb on 2019/9/26.
 */

public class MainPresenter extends BasePresenter<MainContract.View>
        implements MainContract.Persenter {

    public MainPresenter(MainContract.View view) {
        super(view);
    }


    @Override
    public void registerTimeRefreshService() {

    }

    @Override
    public void registerProcessListener() {

    }

    @Override
    public void insertIOList(List<IOTopBean> ioBeans) {
        DbController.getInstance().getSession().startAsyncSession().insertInTx(IOTopBean.class, ioBeans);
    }

    @Override
    public void insertIOData(IOTopBean ioBean) {
        long l = DbController.getInstance().getSession().getIOTopBeanDao().insert(ioBean);
        mView.showToast("插入成功：" + l);
    }

    @Override
    public IOTopBean queryById(String id) {
        return null;
    }

    @Override
    public List<IOTopBean> queryAll() {
        return DbController.getInstance().getSession().getIOTopBeanDao().loadAll();
    }

    @Override
    public String executeShell(String shell) {
        return Utils.executeShell(shell);
    }

    @Override
    public void executeShell(final String shell, final boolean isRoot, final IShellCallBack shellCallBack) {
        shellCallBack.onShellStart();
        Observable.create(new ObservableOnSubscribe<CommandExecution.CommandResult>() {
            @Override
            public void subscribe(ObservableEmitter<CommandExecution.CommandResult> emitter) throws Exception {
                CommandExecution.CommandResult result = CommandExecution.execCommand(shell, isRoot);
                emitter.onNext(result);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.single()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CommandExecution.CommandResult>() {
                    @Override
                    public void accept(CommandExecution.CommandResult result) throws Exception {
                        Log.d(TAG, String.format("errorMsg:%s\nsuccessMsg:%s\nresult:%s\n",
                                result.errorMsg, result.successMsg, result.result));
                        shellCallBack.onShellExecuted(result);
                    }
                });
    }

    @Override
    public void executeShellAndDB(final String shell, final boolean isRoot) {
        mView.showLoadding();
        Observable.create(new ObservableOnSubscribe<CommandExecution.CommandResult>() {
            @Override
            public void subscribe(ObservableEmitter<CommandExecution.CommandResult> emitter) throws Exception {
                CommandExecution.CommandResult result = CommandExecution.execCommand(shell, isRoot);
                emitter.onNext(result);
                emitter.onComplete();
            }
        }).map(new Function<CommandExecution.CommandResult, List<IOTopBean>>() {
            @Override
            public List<IOTopBean> apply(CommandExecution.CommandResult result) throws Exception {
                Log.d(TAG, String.format("errorMsg:%s\nsuccessMsg:%s\nresult:%s\n",
                        result.errorMsg, result.successMsg, result.result));
                return convertToIOBeanListFromResult(result);
            }
        }).map(new Function<List<IOTopBean>, List<IOTopBean>>() {
            @Override
            public List<IOTopBean> apply(List<IOTopBean> ioTopBeans) throws Exception {
                DbController.getInstance().getSession().getIOTopBeanDao().insertInTx(ioTopBeans);
                return ioTopBeans;
            }
        }).subscribeOn(Schedulers.single()).observeOn(AndroidSchedulers.mainThread())
                .subscribe();
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

    @Override
    public List<IOTopBean> convertToIOBeanListFromResult(@Nullable CommandExecution.CommandResult result) {
        List<IOTopBean> ioBeans = new ArrayList<>();
        if (result != null && !TextUtils.isEmpty(result.successMsg)) {
            String[] lines = result.successMsg.split(";");
            IOTopBean ioBean;
            for (String line : lines) {
                String[] processLine = line.split(",");
                ioBean = new IOTopBean(processLine[0], processLine[1], processLine[2], processLine[3],
                        processLine[4], processLine[5], new Date());
                ioBeans.add(ioBean);
            }
        }
        return ioBeans;
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
