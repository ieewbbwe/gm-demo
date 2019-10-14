package com.sgm.iorecord.base;

public class BasePresenter<T> {
    protected T mView;
    protected final String TAG = this.getClass().getSimpleName();

    public BasePresenter(T view) {
        this.mView = view;
    }
}
