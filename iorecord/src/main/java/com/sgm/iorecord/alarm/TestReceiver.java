package com.sgm.iorecord.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by s2s8tb on 2019/10/25.
 */

public class TestReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("TimeTicketReceiver", "TestReceiver onReceive:" + intent.getAction());

    }
}
