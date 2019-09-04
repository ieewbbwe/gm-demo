package com.example.calendar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by s2s8tb on 2019/7/19.
 */

public class TestReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent in = new Intent(context,MainActivity.class);
        in.setFlags(FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(in);
    }
}
