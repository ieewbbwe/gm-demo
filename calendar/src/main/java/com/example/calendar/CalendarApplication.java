package com.example.calendar;

import android.app.Application;

/**
 * Created by s2s8tb on 2019/7/12.
 */

public class CalendarApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
