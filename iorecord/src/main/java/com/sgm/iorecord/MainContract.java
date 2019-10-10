package com.sgm.iorecord;

import com.sgm.iorecord.bean.IOBean;

import java.util.List;

/**
 * Created by s2s8tb on 2019/9/26.
 */

public class MainContract {

    public interface View {
        void showToast(String str);
    }

    public interface Persenter {
        void registerTimeRefreshService();

        void registerProcessListener();

        void insertIOList(List<IOBean> ioBeans);

        void insertIOData(IOBean ioBean);

        IOBean queryById(String id);

        List<IOBean> queryAll();

        @Deprecated
        String executeShell(String shell);

        CommandExecution.CommandResult executeShell(String shell, boolean isRoot);

        void registerService();
    }
}
