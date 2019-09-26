package com.sgm.iorecord;

import android.app.Application;

import com.sgm.iorecord.databases.DbController;

/**
 * Created by s2s8tb on 2019/9/26.
 */

public class NApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        DbController.init(getApplicationContext());
    }
}
