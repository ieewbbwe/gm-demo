package com.sgm.iorecord.base;

import android.content.Context;

/**
 * Created by s2s8tb on 2019/10/15.
 */

public interface BaseView {

    void showToast(String str);

    void showLoading();

    void hideLoading();

    Context getContext();
}
