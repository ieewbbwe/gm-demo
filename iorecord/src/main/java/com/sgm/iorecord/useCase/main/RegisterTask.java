package com.sgm.iorecord.useCase.main;

import android.app.IProcessObserver;
import android.os.RemoteException;
import android.util.Log;

import com.sgm.iorecord.useCase.UseCase;

import java.lang.reflect.Method;

/**
 * Created by s2s8tb on 2019/10/14.
 */

public class RegisterTask extends UseCase<RegisterTask.RequestValues, RegisterTask.ResponseValue> {

    private IProcessObserver mProcessObserver = new IProcessObserver.Stub() {
        @Override
        public void onForegroundActivitiesChanged(int pid, int uid, boolean foregroundActivities) throws RemoteException {
            Log.d("picher", String.format("onForegroundActivitiesChanged ->> pid:%s，uid：%s", pid, uid));
        }

        @Override
        public void onProcessDied(int pid, int uid) throws RemoteException {
            Log.d("picher", String.format("onProcessDied ->> pid:%s，uid：%s", pid, uid));

        }
    };

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        try {
            Class activityManager = Class.forName("android.app.ActivityManager");
            Method getDefaultMethod = activityManager.getMethod("getService");
            Object iActivityManager = getDefaultMethod.invoke(null);
            if (iActivityManager != null) {
                Method registerMethod = iActivityManager.getClass().getMethod("registerProcessObserver", IProcessObserver.class);
                registerMethod.invoke(iActivityManager, mProcessObserver);
            }
            getUseCaseCallback().onSuccess(new ResponseValue());
        } catch (Exception e) {
            e.printStackTrace();
            getUseCaseCallback().onError();
        }
    }

    public static final class RequestValues implements UseCase.RequestValues {

    }

    public static final class ResponseValue implements UseCase.ResponseValue {

    }
}
