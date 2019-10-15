package com.sgm.iorecord.chart;

import android.util.Log;

import com.sgm.iorecord.base.BasePresenter;
import com.sgm.iorecord.chart.usecase.RawQueryTopTask;
import com.sgm.iorecord.databases.SqlScript;
import com.sgm.iorecord.useCase.SimpleUseCaseCallBack;
import com.sgm.iorecord.useCase.UseCaseHandler;
import com.sgm.iorecord.useCase.main.QueryTask;

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

    /**
     * 查询数据库中所有数据
     */
    @Override
    public void queryIOTopAllAsync() {
        mView.showLoading();
        mUseCaseHandler.execute(mQueryTask, new QueryTask.RequestValues(), new SimpleUseCaseCallBack<QueryTask.ResponseValue>() {
            @Override
            public void onSuccess(QueryTask.ResponseValue response) {
                Log.d(TAG, "queryIOTopAllAsync Succeed");
                //mView.showPieChart(response.getIoTopBeans());
                mView.hideLoading();
            }
        });
    }

    /**
     * 按照包名区分，查询最新的数据
     */
    @Override
    public void queryIOTopByPackage() {
        mView.showLoading();
        mUseCaseHandler.execute(mByPackageTask, new RawQueryTopTask.RequestValues(SqlScript.SQL_LATEST_BY_PACKAGE),
                new SimpleUseCaseCallBack<RawQueryTopTask.ResponseValue>() {
                    @Override
                    public void onSuccess(RawQueryTopTask.ResponseValue response) {
                        Log.d(TAG, "queryIOTopByPackage Succeed");
                        mView.showPieChart(response.getIoTopBeans());
                        mView.hideLoading();
                    }
                });
    }
}
