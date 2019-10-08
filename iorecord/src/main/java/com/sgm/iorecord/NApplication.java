package com.sgm.iorecord;

import android.app.Application;
import android.os.Environment;

import com.sgm.iorecord.databases.DbController;

import java.io.File;
import java.util.concurrent.Executors;

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
