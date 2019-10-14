package com.sgm.iorecord;

import android.text.TextUtils;
import android.util.Log;

import com.sgm.iorecord.base.BasePresenter;
import com.sgm.iorecord.databases.DbController;
import com.sgm.iorecord.event.RXLoadIoTopAllEvent;
import com.sgm.iorecord.event.rx.RxBus;
import com.sgm.iorecord.listener.IShellCallBack;
import com.sgm.iorecord.model.IOTopBean;
import com.sgm.iorecord.useCase.SimpleUseCaseCallBack;
import com.sgm.iorecord.useCase.UseCase;
import com.sgm.iorecord.useCase.UseCaseHandler;
import com.sgm.iorecord.useCase.main.InsertIOTopTask;
import com.sgm.iorecord.useCase.main.QueryTask;
import com.sgm.iorecord.useCase.main.RegisterTask;
import com.sgm.iorecord.useCase.main.ShellTask;
import com.sgm.iorecord.utils.CommandExecution;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by s2s8tb on 2019/9/26.
 */

public class MainPresenter extends BasePresenter<MainContract.View>
        implements MainContract.Presenter {

    private final ShellTask mShellTask;
    private final UseCaseHandler mUseCaseHandler;
    private final InsertIOTopTask mInsertTask;
    private final QueryTask mQueryTask;
    private final RegisterTask mRegisterTask;

    public MainPresenter(MainContract.View view) {
        super(view);
        mShellTask = new ShellTask();
        mUseCaseHandler = UseCaseHandler.getInstance();
        mInsertTask = new InsertIOTopTask();
        mQueryTask = new QueryTask();
        mRegisterTask = new RegisterTask();
    }


    @Override
    public void registerTimeRefreshService() {

    }

    @Override
    public void registerProcessListener() {

    }

    @Override
    public void insertIOData(IOTopBean ioBean) {
        long l = DbController.getInstance().getSession().getIOTopBeanDao().insert(ioBean);
        mView.showToast("插入成功Id：" + l);
    }

    @Override
    public IOTopBean queryById(String id) {
        return null;
    }

    @Override
    public void insertIOList(List<IOTopBean> ioBeans) {
        mUseCaseHandler.execute(mInsertTask, new InsertIOTopTask.RequestValues(ioBeans), new SimpleUseCaseCallBack<InsertIOTopTask.ResponseValue>() {
            @Override
            public void onSuccess(InsertIOTopTask.ResponseValue response) {
                mView.hideLoadding();
            }
        });

    }

    @Override
    public void queryAll() {
        mUseCaseHandler.execute(mQueryTask, new QueryTask.RequestValues(), new SimpleUseCaseCallBack<QueryTask.ResponseValue>() {
            @Override
            public void onSuccess(QueryTask.ResponseValue response) {
                RxBus.get().post(new RXLoadIoTopAllEvent(response.getIoTopBeans()));
            }
        });
    }

    @Override
    public void executeShell(final String shell, final boolean isRoot, final IShellCallBack shellCallBack) {
        shellCallBack.onShellStart();
        mUseCaseHandler.execute(mShellTask, new ShellTask.RequestValues(shell), new UseCase.UseCaseCallback<ShellTask.ResponseValue>() {
            @Override
            public void onSuccess(ShellTask.ResponseValue response) {
                CommandExecution.CommandResult result = response.getResult();
                Log.d(TAG, String.format("errorMsg:%s\nsuccessMsg:%s\nresult:%s\n",
                        result.errorMsg, result.successMsg, result.result));
                shellCallBack.onShellExecuted(result);
            }

            @Override
            public void onError() {
                Log.d(TAG, "executeShell Error!");
            }
        });
    }

    @Override
    public void executeShellAndDBAsync(final String shell, final boolean isRoot) {
        mView.showLoadding();
        mUseCaseHandler.execute(mShellTask, new ShellTask.RequestValues(shell), new SimpleUseCaseCallBack<ShellTask.ResponseValue>() {
            @Override
            public void onSuccess(ShellTask.ResponseValue response) {
                CommandExecution.CommandResult result = response.getResult();
                Log.d(TAG, String.format("errorMsg:%s\nsuccessMsg:%s\nresult:%s\n",
                        result.errorMsg, result.successMsg, result.result));
                List<IOTopBean> ioTopBeans = convertToIOBeanListFromResult(response.getResult());
                if (!ioTopBeans.isEmpty()) {
                    insertIOList(ioTopBeans);
                }
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

}
