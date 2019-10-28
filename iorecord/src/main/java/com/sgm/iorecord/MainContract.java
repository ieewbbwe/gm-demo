package com.sgm.iorecord;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;

import com.sgm.iorecord.base.BaseView;
import com.sgm.iorecord.listener.IShellCallBack;
import com.sgm.iorecord.model.IOTopBean;
import com.sgm.iorecord.model.ProcessInfo;

import java.util.List;

/**
 * Created by s2s8tb on 2019/9/26.
 */

public class MainContract {

    public interface View extends BaseView {
        void registerMyReceiver(BroadcastReceiver receiver, IntentFilter filter);

        void startMyService(Intent service);
    }

    public interface Presenter {
        void registerTimeRefreshService();

        void registerProcessListener();

        long insertIoTopBean(IOTopBean ioTopBean);

        void insertIOList(List<IOTopBean> ioBeans);

        IOTopBean queryById(String id);

        void queryAll();

        void executeShell(String shell, boolean isRoot, IShellCallBack shellCallBack);

        void executeShellAndDBAsync(final String shell, final boolean isRoot);

        void registerService();

        void registerTimeTicketReceiver();

        void startRecordService();

        void getProcessMemoryInfo(List<ProcessInfo> pids);
    }
}
