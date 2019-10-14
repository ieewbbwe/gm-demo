package com.sgm.iorecord.useCase.main;

import android.support.annotation.NonNull;
import android.util.Log;

import com.sgm.iorecord.BuildConfig;
import com.sgm.iorecord.databases.DataEngine;
import com.sgm.iorecord.useCase.UseCase;
import com.sgm.iorecord.utils.CommandExecution;

/**
 * Created by s2s8tb on 2019/10/12.
 * Shell 命令任务
 */

public class ShellTask extends UseCase<ShellTask.RequestValues, ShellTask.ResponseValue> {

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        CommandExecution.CommandResult result;
        if (BuildConfig.DEV_MODE) {
            result = DataEngine.gerInstance().createCommandResult();
        } else {
            result = CommandExecution.execCommand(requestValues.getShellSpript(), requestValues.isRoot());
        }
        getUseCaseCallback().onSuccess(new ResponseValue(result));
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
