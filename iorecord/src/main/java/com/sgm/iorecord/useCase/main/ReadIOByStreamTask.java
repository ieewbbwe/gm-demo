package com.sgm.iorecord.useCase.main;

import android.support.annotation.NonNull;

import com.sgm.iorecord.model.IOBean;
import com.sgm.iorecord.model.IOTopBean;
import com.sgm.iorecord.model.ProcessInfo;
import com.sgm.iorecord.useCase.UseCase;
import com.sgm.iorecord.utils.CommandExecution;
import com.sgm.iorecord.utils.Lg;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ReadIOByStreamTask extends UseCase<ReadIOByStreamTask.RequestValues, ReadIOByStreamTask.ResponseValue> {

    private static final String TAG = "ReadIOByStreamTask";

    @Override
    protected void executeUseCase(RequestValues requestValues) {

        List<ProcessInfo> processInfos = requestValues.processInfos;
        // for suit topShell logic
        List<IOTopBean> ioTopBeans = new ArrayList<>();
        for (ProcessInfo info : processInfos) {
            try {
                RandomAccessFile accessFile = new RandomAccessFile("/proc/" + info.getPid() + "/io", "r");
                String line;
                Map<String, String> lineMap = new HashMap<>();
                while ((line = accessFile.readLine()) != null) {
                    String[] strs = line.split(":");
                    lineMap.put(strs[0], strs[1]);
                }
                Lg.d(TAG, String.format(Locale.getDefault(), "PID:%s,ProcessName:%s,Written:%s"
                        , info.getPid(), info.getProcessName(), lineMap.get("write_bytes")));
                ioTopBeans.add(new IOTopBean(info.getPid() + "", lineMap.get("read_bytes"), lineMap.get("write_bytes"),
                        "", "", info.getProcessName(), new Date()));
                accessFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        getUseCaseCallback().onSuccess(new ResponseValue(ioTopBeans));
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private List<ProcessInfo> processInfos;

        public RequestValues(List<ProcessInfo> infos) {
            this.processInfos = infos;
        }

        public List<ProcessInfo> getProcessInfos() {
            return processInfos;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {

        private List<IOTopBean> result;

        public ResponseValue(List<IOTopBean> result) {
            this.result = result;
        }

        public List<IOTopBean> getResult() {
            return result;
        }
    }
}
