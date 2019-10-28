package com.sgm.iorecord.chart.fragment;

import android.util.Log;

import com.sgm.iorecord.base.BasePresenter;
import com.sgm.iorecord.chart.usecase.SumWrittenTopTask;
import com.sgm.iorecord.useCase.SimpleUseCaseCallBack;

import java.util.Date;

/**
 * Created by s2s8tb on 2019/10/18.
 */

public class PieChartPresenter extends BasePresenter<PieChartContract.View>
        implements PieChartContract.Presenter {

    private final SumWrittenTopTask mSumWrittenTopTask;

    public PieChartPresenter(PieChartContract.View view) {
        super(view);
        mSumWrittenTopTask = new SumWrittenTopTask();
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
                        Log.d(TAG, "queryIOTopByPackage Succeed! PackageSize:" + response.getPieEntries().size());
                        mView.showPieChart(response.getPieEntries());
                        mView.hideLoading();
                    }
                });
    }
}
