package com.sgm.iorecord;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import com.sgm.iorecord.useCase.main.GetPIDTask;
import com.sgm.iorecord.useCase.main.InsertTopBeanTask;
import com.sgm.iorecord.useCase.main.InsertTopListTask;
import com.sgm.iorecord.useCase.main.QueryTask;
import com.sgm.iorecord.useCase.main.ReadIOByStreamTask;
import com.sgm.iorecord.useCase.main.RegisterTask;
import com.sgm.iorecord.useCase.main.RequireMemoryUseCase;
import com.sgm.iorecord.useCase.main.TopShellTask;
import com.sgm.iorecord.utils.CommandExecution;
import com.sgm.iorecord.utils.Lg;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
    private final GetPIDTask mGetPIDTask;
    private final RequireMemoryUseCase mMemoryUseCase;
    private final ReadIOByStreamTask mReadIOByStreamTask;

    public MainPresenter(MainContract.View view) {
        super(view);
        mTopShellTask = new TopShellTask();
        mInsertTask = new InsertTopListTask();
        mQueryTask = new QueryTask();
        mRegisterTask = new RegisterTask();
        mInsertTopBeanTask = new InsertTopBeanTask();
        mSumWrittenTopTask = new SumWrittenTopTask();
        mGetPIDTask = new GetPIDTask();
        mMemoryUseCase = new RequireMemoryUseCase();
        mReadIOByStreamTask = new ReadIOByStreamTask();
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
        mUseCaseHandler.execute(mTopShellTask, new TopShellTask.RequestValues(shell, isRoot), new UseCase.UseCaseCallback<TopShellTask.ResponseValue>() {
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
        mUseCaseHandler.execute(mTopShellTask, new TopShellTask.RequestValues(shell, isRoot), new SimpleUseCaseCallBack<TopShellTask.ResponseValue>() {
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

    @Override
    public void requireIOAndDbAsync(List<ProcessInfo> infos) {
        mView.showLoading();
        mUseCaseHandler.execute(mReadIOByStreamTask, new ReadIOByStreamTask.RequestValues(infos), new SimpleUseCaseCallBack<ReadIOByStreamTask.ResponseValue>() {
            @Override
            public void onSuccess(ReadIOByStreamTask.ResponseValue response) {
                List<IOTopBean> ioTopBeans = response.getResult();
                if (ioTopBeans != null) {
                    Lg.d(TAG, "RequireIO Size:" + response.getResult().size());
                    insertIOList(ioTopBeans);
                }
            }

            @Override
            public void onError() {
                super.onError();
                mView.showToast("Search error");
                Log.d(TAG, "executeShell Error!");
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
    public void getProcessInfo(ActivityManager activityManager, UseCase.UseCaseCallback<GetPIDTask.ResponseValue> useCaseCallback) {
        mUseCaseHandler.execute(mGetPIDTask, new GetPIDTask.RequestValues(activityManager), useCaseCallback);
    }

    @Override
    public void requireProcessMemoryInfo(List<ProcessInfo> pids) {
        mUseCaseHandler.execute(mMemoryUseCase, new RequireMemoryUseCase
                        .RequestValues((ActivityManager) mView.getContext().getSystemService(Context.ACTIVITY_SERVICE), pids),
                new SimpleUseCaseCallBack<RequireMemoryUseCase.ResponseValue>() {
                    @Override
                    public void onSuccess(RequireMemoryUseCase.ResponseValue response) {
                        Log.d(TAG, "requireProcessMemoryInfo Size:" + response.getMemoryBeans().size());
                    }
                });
    }

    @Override
    public void requireThreadNumByProcess(List<ProcessInfo> processInfos) {
        for (ProcessInfo info : processInfos) {
            File reader = new File("/proc/" + info.getPid() + "/task");
            if (reader.listFiles() != null) {
                Lg.d(TAG, String.format(Locale.getDefault(), "PID:%d Thread SIZE:%d", info.getPid(), reader.listFiles().length));
            }
        }
    }

    @Override
    public void requireFdNumByProcess(List<ProcessInfo> processInfos) {
        for (ProcessInfo info : processInfos) {
            File reader = new File("/proc/" + info.getPid() + "/fd");
            if (reader.listFiles() != null) {
                Lg.d(TAG, String.format(Locale.getDefault(), "PID:%d FD SIZE:%d", info.getPid(), reader.listFiles().length));
            }
        }
    }
}
