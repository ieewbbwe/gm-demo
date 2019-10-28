package com.sgm.iorecord.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by s2s8tb on 2019/10/15.
 * 基类，存放一些公共方法复用代码
 */

public class BaseActivity extends AppCompatActivity implements BaseView {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void showToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        showToast("showLoading...");
    }

    @Override
    public void hideLoading() {
        showToast("hideLoading...");
    }

    @Override
    public Context getContext() {
        return this;
    }
}
