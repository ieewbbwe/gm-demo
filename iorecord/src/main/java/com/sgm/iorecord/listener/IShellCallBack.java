package com.sgm.iorecord.listener;

import com.sgm.iorecord.CommandExecution;

public interface IShellCallBack {
    void onShellStart();

    void onShellExecuted(CommandExecution.CommandResult result);
}
