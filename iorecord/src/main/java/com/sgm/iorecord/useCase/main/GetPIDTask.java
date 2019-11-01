package com.sgm.iorecord.useCase.main;

import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import com.sgm.iorecord.model.ProcessInfo;
import com.sgm.iorecord.useCase.UseCase;
import com.sgm.iorecord.utils.Lg;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by s2s8tb on 2019/10/28.
 */

public class GetPIDTask extends UseCase<GetPIDTask.RequestValues, GetPIDTask.ResponseValue> {

    private static final String TAG = "GetPIDTask";

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        List<ProcessInfo> processInfos = requireAllPid();
//        ActivityManager activityManager = requestValues.getActivityManager();
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
//            processInfos = requireAllProcess(activityManager);
//        } else {
//            processInfos = requireAllProcessAPI23();
//        }
        getUseCaseCallback().onSuccess(new ResponseValue(processInfos));

    }

    public List<ProcessInfo> requireAllPid() {
        List<ProcessInfo> infos = new ArrayList<>();
        File[] files = new File("/proc").listFiles();
        RandomAccessFile accessFile;
        for (File f : files) {
            if (f.isDirectory()) {
                int pid = 0;
                String processName = "";
                String cmdLine;
                try {
                    pid = Integer.parseInt(f.getName());
                    accessFile = new RandomAccessFile("/proc/" + pid + "/cmdline", "r");
                    cmdLine = accessFile.readLine();
                    // Lg.d(TAG, String.format("PID:%S PROCESS:%S", pid, cmdLine));
                    if (cmdLine != null) {
                        processName = cmdLine.trim();
                    }
                    accessFile.close();
                    if (isSuitPidFilter(processName)) {
                        infos.add(new ProcessInfo(pid, processName));
                    }
                } catch (NumberFormatException | IOException e) {
                    Lg.d(TAG, "PID NumberFormat Error:" + pid);
                    //e.printStackTrace();
                }
            }
        }
        return infos;
    }

    private boolean isSuitPidFilter(String processName) {
        return !TextUtils.isEmpty(processName) && processName.startsWith("com.");
    }

    private boolean hasPermission(Context context) {
        AppOpsManager appOps = (AppOpsManager)
                context.getSystemService(Context.APP_OPS_SERVICE);
        int mode = 0;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                    android.os.Process.myUid(), context.getPackageName());
        }
        return mode == AppOpsManager.MODE_ALLOWED;
    }

    public List<ProcessInfo> requireAllProcessAPI23() {
        return null;
    }

    public List<ProcessInfo> requireAllProcess(ActivityManager activityManager) {
        List<ProcessInfo> infos = new ArrayList<>();
        if (activityManager != null) {
            ProcessInfo processInfo;
            List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo info : runningAppProcesses) {
                processInfo = new ProcessInfo(info.pid, info.processName);
                infos.add(processInfo);
            }
        }
        return infos;
    }

    public static final class RequestValues implements UseCase.RequestValues {
//        private ActivityManager activityManager;
//
//        public RequestValues(ActivityManager activityManager) {
//            this.activityManager = activityManager;
//        }
//
//        public ActivityManager getActivityManager() {
//            return activityManager;
//        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {

        private final List<ProcessInfo> processInfos;

        public ResponseValue(List<ProcessInfo> infos) {
            processInfos = infos;
        }

        public List<ProcessInfo> getIoTopBeans() {
            return processInfos;
        }
    }
}
