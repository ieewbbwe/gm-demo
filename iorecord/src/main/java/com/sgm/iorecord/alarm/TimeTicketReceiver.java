package com.sgm.iorecord.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by s2s8tb on 2019/10/22.
 */

public class TimeTicketReceiver extends BroadcastReceiver {

    private static final String TAG = "TimeTicketReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
//        Intent recordService = new Intent(context, IORecordService.class);
//        context.startService(recordService);
        Log.d(TAG, "onReceive:" + intent.getAction());
    }
}
