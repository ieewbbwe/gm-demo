package com.sgm.iorecord.useCase.main;

import android.app.ActivityManager;
import android.os.Build;

import com.sgm.iorecord.model.ProcessInfo;
import com.sgm.iorecord.useCase.UseCase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by s2s8tb on 2019/10/28.
 */

public class GetPIDTask extends UseCase<GetPIDTask.RequestValues, GetPIDTask.ResponseValue> {

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        ActivityManager activityManager = requestValues.getActivityManager();
        List<ProcessInfo> processInfos;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            processInfos = requireAllProcess(activityManager);
        } else {
            processInfos = requireAllProcessAPI23();
        }
        getUseCaseCallback().onSuccess(new ResponseValue(processInfos));
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

    public List<ProcessInfo> requireAllProcessAPI23() {


        return null;
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private ActivityManager activityManager;

        public RequestValues(ActivityManager activityManager) {
            this.activityManager = activityManager;
        }

        public ActivityManager getActivityManager() {
            return activityManager;
        }
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
