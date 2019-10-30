package com.sgm.iorecord.useCase.main;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.sgm.iorecord.BuildConfig;
import com.sgm.iorecord.databases.DataEngine;
import com.sgm.iorecord.useCase.UseCase;
import com.sgm.iorecord.utils.CommandExecution;
import com.sgm.iorecord.utils.Lg;

/**
 * Created by s2s8tb on 2019/10/12.
 * Shell 命令任务
 */

public class TopShellTask extends UseCase<TopShellTask.RequestValues, TopShellTask.ResponseValue> {

    private static final java.lang.String TAG = "TopShellTask";

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        CommandExecution.CommandResult result;
        Lg.d(TAG, "Shell Script is:" + requestValues.getShellSpript());
        if (BuildConfig.DEV_MODE) {
            result = DataEngine.gerInstance().createCommandResult();
        } else {
            result = CommandExecution.execCommand(requestValues.getShellSpript(), requestValues.isRoot());
        }
        if (!TextUtils.isEmpty(result.successMsg)) {
            getUseCaseCallback().onSuccess(new ResponseValue(result));
        } else if (!TextUtils.isEmpty(result.errorMsg)) {
            getUseCaseCallback().onError();
        }
    }

    public static final class RequestValues implements UseCase.RequestValues {

        private final String shellSpript;
        private boolean isRoot = false;

        public RequestValues(@NonNull String shell,boolean isRoot) {
            shellSpript = shell;
            this.isRoot = isRoot;
        }

        public String getShellSpript() {
            return shellSpript;
        }

        public boolean isRoot() {
            return isRoot;
        }

        public void setRoot(boolean root) {
            isRoot = root;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {

        private CommandExecution.CommandResult result;

        public ResponseValue(CommandExecution.CommandResult result) {
            this.result = result;
        }

        public CommandExecution.CommandResult getResult() {
            return result;
        }
    }
}
