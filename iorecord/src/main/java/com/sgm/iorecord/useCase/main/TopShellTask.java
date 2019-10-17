package com.sgm.iorecord.useCase.main;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.sgm.iorecord.BuildConfig;
import com.sgm.iorecord.databases.DataEngine;
import com.sgm.iorecord.useCase.UseCase;
import com.sgm.iorecord.utils.CommandExecution;

/**
 * Created by s2s8tb on 2019/10/12.
 * Shell 命令任务
 */

public class TopShellTask extends UseCase<TopShellTask.RequestValues, TopShellTask.ResponseValue> {

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        CommandExecution.CommandResult result;
        if (BuildConfig.DEV_MODE) {
            result = DataEngine.gerInstance().createCommandResult();
        } else {
            result = CommandExecution.execCommand(requestValues.getShellSpript(), requestValues.isRoot());
        }
        Log.d("picher","任务执行完成！");
        if (!TextUtils.isEmpty(result.successMsg)) {
            getUseCaseCallback().onSuccess(new ResponseValue(result));
        } else if (!TextUtils.isEmpty(result.errorMsg)) {
            getUseCaseCallback().onError();
        }
    }

    public static final class RequestValues implements UseCase.RequestValues {

        private final String shellSpript;
        private boolean isRoot = false;

        public RequestValues(@NonNull String shell) {
            shellSpript = shell;
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