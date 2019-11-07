package com.sgm.iorecord;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;

import com.sgm.iorecord.base.BaseView;
import com.sgm.iorecord.listener.IShellCallBack;
import com.sgm.iorecord.model.IOTopBean;
import com.sgm.iorecord.model.ProcessInfo;
import com.sgm.iorecord.useCase.UseCase;
import com.sgm.iorecord.useCase.main.GetPIDTask;

import java.util.Date;
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

        void registerService();

        void registerTimeTicketReceiver();

        void startRecordService();

        /**
         * 获取应用所有进程信息
         */
        void getProcessInfo(ActivityManager activityManager, UseCase.UseCaseCallback<GetPIDTask.ResponseValue> useCaseCallback);

        /**
         * 记录系统IO历史信息
         *
         * @param shell  iotop脚本
         * @param isRoot is devices root
         */
        @Deprecated//can't get all pids by shell script "ps"
        void requireIOAndDBAsync(final String shell, final boolean isRoot);

        /**
         * 获取系统IO历史数据，按照包名分类求和
         *
         * @param startTime 记录得开始时间
         * @param endTime   记录得结束时间
         */
        void queryIOTopByPackage(Date startTime, Date endTime);

        /**
         * Require by file stream
         */
        void requireIOAndDbAsync(List<ProcessInfo> infos);

        /**
         * 获取进程内存信息
         *
         * @param processInfos 进程信息
         */
        void requireProcessMemoryInfo(List<ProcessInfo> processInfos);

        /**
         * 获取进程得线程数
         *
         * @param processInfos 进程信息
         */
        void requireThreadNumByProcess(List<ProcessInfo> processInfos);

        /**
         * 获取fd句柄数
         *
         * @param processInfos 进程信息
         */
        void requireFdNumByProcess(List<ProcessInfo> processInfos);

    }
}
