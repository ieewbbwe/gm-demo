package com.sgm.iorecord.listener;

import com.sgm.iorecord.utils.CommandExecution;

public interface IShellCallBack {
    void onShellStart();

    void onShellExecuted(CommandExecution.CommandResult result);
}
