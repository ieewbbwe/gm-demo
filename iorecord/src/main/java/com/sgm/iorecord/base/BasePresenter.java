package com.sgm.iorecord.base;

import com.sgm.iorecord.useCase.UseCaseHandler;

public class BasePresenter<T> {
    protected T mView;
    protected final String TAG = this.getClass().getSimpleName();
    protected final UseCaseHandler mUseCaseHandler;

    public BasePresenter(T view) {
        this.mView = view;
        mUseCaseHandler = UseCaseHandler.getInstance();
    }
}
