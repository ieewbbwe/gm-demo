package com.example.espresso;

import android.app.Application;
import android.util.Log;

/**
 * Created by s2s8tb on 2019/8/13.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("picher","application onCreate");
    }
}
