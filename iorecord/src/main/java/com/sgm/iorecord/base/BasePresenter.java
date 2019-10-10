package com.sgm.iorecord.base;

import com.sgm.iorecord.chart.ChartContract;

public class BasePresenter<T> {
    protected T mView;
    protected final String TAG = this.getClass().getSimpleName();

    public BasePresenter(T view) {
        this.mView = view;
    }
}
