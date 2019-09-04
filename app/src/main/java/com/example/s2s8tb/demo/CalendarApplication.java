package com.example.s2s8tb.demo;

import android.app.Application;
import android.util.Log;

/**
 * Created by s2s8tb on 2019/6/25.
 */

public class CalendarApplication extends Application implements Thread.UncaughtExceptionHandler{

    @Override
    public void onCreate() {
        super.onCreate();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        Log.d("picher","ThreadName:"+thread.getName()+"msg:"+throwable.getMessage());
    }

}
