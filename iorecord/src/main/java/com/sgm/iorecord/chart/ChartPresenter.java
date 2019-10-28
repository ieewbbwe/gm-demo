package com.sgm.iorecord.chart;

import android.util.Log;

import com.sgm.iorecord.base.BasePresenter;
import com.sgm.iorecord.chart.usecase.RawQueryTopTask;
import com.sgm.iorecord.chart.usecase.SumWrittenTopTask;
import com.sgm.iorecord.useCase.SimpleUseCaseCallBack;
import com.sgm.iorecord.useCase.UseCaseHandler;
import com.sgm.iorecord.useCase.main.QueryTask;

import java.util.Date;

public class ChartPresenter extends BasePresenter<ChartContract.View>
        implements ChartContract.Presenter {

    private final QueryTask mQueryTask;

    public ChartPresenter(ChartContract.View view) {
        super(view);
        mQueryTask = new QueryTask();
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

}
