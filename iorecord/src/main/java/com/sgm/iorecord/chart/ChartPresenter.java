package com.sgm.iorecord.chart;

import com.sgm.iorecord.base.BasePresenter;

public class ChartPresenter extends BasePresenter<ChartContract.View>
        implements ChartContract.Presenter {

    public ChartPresenter(ChartContract.View view) {
        super(view);
    }
}
