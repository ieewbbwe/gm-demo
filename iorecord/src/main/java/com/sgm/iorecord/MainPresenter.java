package com.sgm.iorecord;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Debug;
import android.util.Log;

import com.sgm.iorecord.alarm.IORecordService;
import com.sgm.iorecord.alarm.TimeTicketReceiver;
import com.sgm.iorecord.base.BasePresenter;
import com.sgm.iorecord.chart.usecase.SumWrittenTopTask;
import com.sgm.iorecord.databases.DataEngine;
import com.sgm.iorecord.databases.DbController;
import com.sgm.iorecord.event.RXLoadIoTopAllEvent;
import com.sgm.iorecord.event.rx.RxBus;
import com.sgm.iorecord.listener.IShellCallBack;
import com.sgm.iorecord.model.IOTopBean;
import com.sgm.iorecord.model.ProcessInfo;
import com.sgm.iorecord.useCase.SimpleUseCaseCallBack;
import com.sgm.iorecord.useCase.UseCase;
import com.sgm.iorecord.useCase.main.InsertTopBeanTask;
import com.sgm.iorecord.useCase.main.InsertTopListTask;
import com.sgm.iorecord.useCase.main.QueryTask;
import com.sgm.iorecord.useCase.main.RegisterTask;
import com.sgm.iorecord.useCase.main.TopShellTask;
import com.sgm.iorecord.utils.CommandExecution;

import java.util.Date;
import java.util.List;

/**
 * Created by s2s8tb on 2019/9/26.
 */

public class MainPresenter extends BasePresenter<MainContract.View>
        implements MainContract.Presenter {

    private final TopShellTask mTopShellTask;
    private final InsertTopListTask mInsertTask;
    private final QueryTask mQueryTask;
    private final RegisterTask mRegisterTask;
    private final InsertTopBeanTask mInsertTopBeanTask;
    private final SumWrittenTopTask mSumWrittenTopTask;

    public MainPresenter(MainContract.View view) {
        super(view);
        mTopShellTask = new TopShellTask();
        mInsertTask = new InsertTopListTask();
        mQueryTask = new QueryTask();
        mRegisterTask = new RegisterTask();
        mInsertTopBeanTask = new InsertTopBeanTask();
        mSumWrittenTopTask = new SumWrittenTopTask();
    }


    @Override
    public void registerTimeRefreshService() {

    }

    @Override
    public void registerProcessListener() {

    }

    @Override
    public long insertIoTopBean(IOTopBean ioTopBean) {
        return DbController.getInstance().getSession().getIOTopBeanDao().insert(ioTopBean);
    }

    @Override
    public IOTopBean queryById(String id) {

        return null;
    }

    @Override
    public void insertIOList(List<IOTopBean> ioBeans) {
        mView.showLoading();
        mUseCaseHandler.execute(mInsertTask, new InsertTopListTask.RequestValues(ioBeans), new SimpleUseCaseCallBack<InsertTopListTask.ResponseValue>() {
            @Override
            public void onSuccess(InsertTopListTask.ResponseValue response) {
                mView.hideLoading();
            }
        });
    }

    @Override
    public void queryAll() {
        mView.showLoading();
        mUseCaseHandler.execute(mQueryTask, new QueryTask.RequestValues(), new SimpleUseCaseCallBack<QueryTask.ResponseValue>() {
            @Override
            public void onSuccess(QueryTask.ResponseValue response) {
                mView.hideLoading();
                RxBus.get().post(new RXLoadIoTopAllEvent(response.getIoTopBeans()));
            }
        });
    }

    @Override
    public void executeShell(final String shell, final boolean isRoot, final IShellCallBack shellCallBack) {
        shellCallBack.onShellStart();
        mUseCaseHandler.execute(mTopShellTask, new TopShellTask.RequestValues(shell,isRoot), new UseCase.UseCaseCallback<TopShellTask.ResponseValue>() {
            @Override
            public void onSuccess(TopShellTask.ResponseValue response) {
                CommandExecution.CommandResult result = response.getResult();
                Log.d(TAG, String.format("errorMsg:%s\nsuccessMsg:%s\nresult:%s\n",
                        result.errorMsg, result.successMsg, result.result));
                shellCallBack.onShellExecuted(result);
            }

            @Override
            public void onError() {
                mView.showToast("不支持IOTOP");
                Log.d(TAG, "executeShell Error!");
            }
        });
    }

    @Override
    public void requireIOAndDBAsync(final String shell, final boolean isRoot) {
        mView.showLoading();
        mUseCaseHandler.execute(mTopShellTask, new TopShellTask.RequestValues(shell,isRoot), new SimpleUseCaseCallBack<TopShellTask.ResponseValue>() {
            @Override
            public void onSuccess(TopShellTask.ResponseValue response) {
                CommandExecution.CommandResult result = response.getResult();
                Log.d(TAG, String.format("errorMsg:%s\nsuccessMsg:%s\nresult:%s\n",
                        result.errorMsg, result.successMsg, result.result));
                List<IOTopBean> ioTopBeans = DataEngine.gerInstance().convertToIOBeanListFromResult(response.getResult());
                if (!ioTopBeans.isEmpty()) {
                    insertIOList(ioTopBeans);
                }
            }

            @Override
            public void onError() {
                super.onError();
                mView.showToast("不支持IOTOP");
                Log.d(TAG, "executeShell Error!");
            }
        });
    }

    @Override
    public void queryIOTopByPackage(Date startTime, Date endTime) {
        mUseCaseHandler.execute(mSumWrittenTopTask, new SumWrittenTopTask.RequestValues(startTime, endTime),
                new SimpleUseCaseCallBack<SumWrittenTopTask.ResponseValue>() {
                    @Override
                    public void onSuccess(SumWrittenTopTask.ResponseValue response) {
                        Log.d(TAG, "queryIOTopByPackage Succeed! PackageSize:" + response.getPieEntries().size());
                        mView.hideLoading();
                    }
                });
    }

    /**
     * 注册监听应用被杀掉的服务
     */
    @Override
    public void registerService() {
        mView.showToast("Start Register...");
        mUseCaseHandler.execute(mRegisterTask, new RegisterTask.RequestValues(), new UseCase.UseCaseCallback<RegisterTask.ResponseValue>() {
            @Override
            public void onSuccess(RegisterTask.ResponseValue response) {
                mView.showToast("Register succeed");
            }

            @Override
            public void onError() {
                mView.showToast("Register error");
            }
        });
    }

    @Override
    public void registerTimeTicketReceiver() {
        TimeTicketReceiver ticketReceiver = new TimeTicketReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_TIME_TICK);
        intentFilter.addAction("pichertest");
        mView.registerMyReceiver(ticketReceiver, intentFilter);
    }

    @Override
    public void startRecordService() {
        Intent intent = new Intent(mView.getContext(), IORecordService.class);
        mView.startMyService(intent);
    }

    @Override
    public void requireProcessMemoryInfo(List<ProcessInfo> pids) {
        if (pids != null && pids.size() > 0) {
            int[] realPids = new int[pids.size()];
            String[] processNames = new String[pids.size()];
            for (int i = 0; i < pids.size(); i++) {
                realPids[i] = pids.get(i).getPid();
                processNames[i] = pids.get(i).getProcessName() + "-" + realPids[i];
            }

            ActivityManager activityManager = (ActivityManager) mView.getContext().getSystemService(Context.ACTIVITY_SERVICE);
            if (activityManager != null) {
                Debug.MemoryInfo[] memInfos = activityManager.getProcessMemoryInfo(realPids);
                if (memInfos != null && memInfos.length == pids.size()) {
                    for (int i = 0; i < realPids.length; i++) {
                        int pid = realPids[i];
                        Debug.MemoryInfo pidMemoryInfo = memInfos[i];
                    }
                }
                //activityManager.requireProcessMemoryInfo()
            }
        }

    }

    @Override
    public void requireThreadNumByProcess(List<ProcessInfo> pids) {

    }

    @Override
    public void requireFdNumByProcess(List<ProcessInfo> pids) {

    }
}
