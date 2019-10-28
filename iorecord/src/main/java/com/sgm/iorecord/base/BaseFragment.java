package com.sgm.iorecord.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by s2s8tb on 2019/10/18.
 */

public abstract class BaseFragment extends Fragment implements BaseView {
    protected View mView;
    protected BaseActivity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(create(), container, false);
        activity = (BaseActivity) getActivity();
        return mView;
    }

    protected abstract int create();

    @Override
    public void showToast(String str) {
        activity.showToast(str);
    }

    @Override
    public void showLoading() {
        activity.showLoading();
    }

    @Override
    public void hideLoading() {
        activity.hideLoading();
    }
}
