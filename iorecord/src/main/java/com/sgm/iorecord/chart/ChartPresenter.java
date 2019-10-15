package com.sgm.iorecord.chart;

import android.util.Log;

import com.sgm.iorecord.base.BasePresenter;
import com.sgm.iorecord.chart.usecase.RawQueryTopTask;
import com.sgm.iorecord.databases.DbController;
import com.sgm.iorecord.databases.SqlScript;
import com.sgm.iorecord.model.IOTopBean;
import com.sgm.iorecord.useCase.SimpleUseCaseCallBack;
import com.sgm.iorecord.useCase.UseCaseHandler;
import com.sgm.iorecord.useCase.main.QueryTask;

import java.util.List;

public class ChartPresenter extends BasePresenter<ChartContract.View>
        implements ChartContract.Presenter {

    private final QueryTask mQueryTask;
    private final UseCaseHandler mUseCaseHandler;
    private final RawQueryTopTask mByPackageTask;

    public ChartPresenter(ChartContract.View view) {
        super(view);
        mUseCaseHandler = UseCaseHandler.getInstance();
        mQueryTask = new QueryTask();
        mByPackageTask = new RawQueryTopTask();
    }

    @Override
    public List<IOTopBean> queryIOTopAll() {
        return DbController.getInstance().getSession().getIOTopBeanDao().loadAll();
    }

    @Override
    public void queryIOTopAllAsync() {
        mView.showToast("Loading...");
        mUseCaseHandler.execute(mQueryTask, new QueryTask.RequestValues(), new SimpleUseCaseCallBack<QueryTask.ResponseValue>() {
            @Override
            public void onSuccess(QueryTask.ResponseValue response) {
                mView.showPieChart(response.getIoTopBeans());
                mView.showToast("load succeed!");
            }
        });
    }

    @Override
    public void queryIOTopByPackage() {
        mView.showToast("Loading...");
        mUseCaseHandler.execute(mByPackageTask, new RawQueryTopTask.RequestValues(SqlScript.SQL_LATEST_BY_PACKAGE),
                new SimpleUseCaseCallBack<RawQueryTopTask.ResponseValue>() {
                    @Override
                    public void onSuccess(RawQueryTopTask.ResponseValue response) {
                        Log.d(TAG, "数据集：" + response.getIoTopBeans().size());
                        mView.showToast("load succeed!");
                    }
                });
    }
}
