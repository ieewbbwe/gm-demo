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
    private final UseCaseHandler mUseCaseHandler;
    private final RawQueryTopTask mByPackageTask;
    private final SumWrittenTopTask mSumWrittenTopTask;

    public ChartPresenter(ChartContract.View view) {
        super(view);
        mUseCaseHandler = UseCaseHandler.getInstance();
        mQueryTask = new QueryTask();
        mByPackageTask = new RawQueryTopTask();
        mSumWrittenTopTask = new SumWrittenTopTask();
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
     *
     * @param startTime
     * @param endTime
     */
    @Override
    public void queryIOTopByPackage(Date startTime, Date endTime) {
        mView.showLoading();
        if (endTime.getTime() <= startTime.getTime()) {
            mView.showToast("时间区间错误！");
            mView.hideLoading();
            return;
        }
        mUseCaseHandler.execute(mSumWrittenTopTask, new SumWrittenTopTask.RequestValues(startTime, endTime),
                new SimpleUseCaseCallBack<SumWrittenTopTask.ResponseValue>() {
                    @Override
                    public void onSuccess(SumWrittenTopTask.ResponseValue response) {
                        Log.d(TAG, "queryIOTopByPackage Succeed!" + response.getPieEntries().size());
                        mView.showPieChart(response.getPieEntries());
                        mView.hideLoading();
                    }
                });
    }
}
