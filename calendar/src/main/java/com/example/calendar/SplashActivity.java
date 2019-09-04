package com.example.calendar;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import java.util.Timer;

/**
 * Created by s2s8tb on 2019/7/12.
 */

public class SplashActivity extends Activity {

    private GestureDetector mGestureDetector;
    private Timer timer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("picher", "onResume");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        sendBroadcast(new Intent("com.example.picher.test"));
        //startActivity(new Intent(this, MainActivity.class));
        finish();

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("picher", "onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("picher", "onDestory");
    }

//    public void thread(){
//        Log.d("picher","splashThread:"+Thread.currentThread().getName());
//        Handler handler =new Handler();
//        mGestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
//            @Override
//            public boolean onDown(MotionEvent e) {
//                return true;
//            }
//
//            @Override
//            public boolean onSingleTapUp(MotionEvent e) {
//                //doClickAction((int) e.getX(), (int) e.getY());
//                return true;
//            }
//        });
//    }
}
