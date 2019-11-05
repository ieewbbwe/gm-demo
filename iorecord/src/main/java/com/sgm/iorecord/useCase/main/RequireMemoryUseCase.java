package com.sgm.iorecord.useCase.main;

import android.app.ActivityManager;
import android.os.Debug;

import com.sgm.iorecord.model.MemoryBean;
import com.sgm.iorecord.model.ProcessInfo;
import com.sgm.iorecord.useCase.UseCase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by s2s8tb on 2019/11/2.
 */

public class RequireMemoryUseCase extends UseCase<RequireMemoryUseCase.RequestValues, RequireMemoryUseCase.ResponseValue> {

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        ActivityManager activityManager = requestValues.mActivityManager;
        List<ProcessInfo> pids = requestValues.infos;
        List<MemoryBean> memoryBeans = new ArrayList<>();
        if (pids != null && pids.size() > 0) {
            int[] realPids = new int[pids.size()];
            String[] processNames = new String[pids.size()];
            for (int i = 0; i < pids.size(); i++) {
                realPids[i] = pids.get(i).getPid();
                processNames[i] = pids.get(i).getProcessName() + "-" + realPids[i];
            }

            if (activityManager != null) {
                Debug.MemoryInfo[] memInfos = activityManager.getProcessMemoryInfo(realPids);
                if (memInfos != null && memInfos.length == pids.size()) {
                    for (int i = 0; i < realPids.length; i++) {
                        int pid = realPids[i];
                        Debug.MemoryInfo pidMemoryInfo = memInfos[i];

                        memoryBeans.add(new MemoryBean(pid, processNames[i], pidMemoryInfo.getTotalPss()
                                , pidMemoryInfo.getTotalPrivateDirty()));
                    }
                    getUseCaseCallback().onSuccess(new ResponseValue(memoryBeans));
                }
            }
        }
    }

    public static final class RequestValues implements UseCase.RequestValues {

        private ActivityManager mActivityManager;
        private List<ProcessInfo> infos;

        public RequestValues(ActivityManager activityManager, List<ProcessInfo> processInfos) {
            this.mActivityManager = activityManager;
            this.infos = processInfos;
        }

    }

    public static final class ResponseValue implements UseCase.ResponseValue {

        private final List<MemoryBean> memoryBeans;

        public ResponseValue(List<MemoryBean> memoryBeans) {
            this.memoryBeans = memoryBeans;
        }

        public List<MemoryBean> getMemoryBeans() {
            return memoryBeans;
        }
    }
}
