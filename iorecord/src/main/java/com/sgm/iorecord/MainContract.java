package com.sgm.iorecord;

import com.sgm.iorecord.model.IOTopBean;
import com.sgm.iorecord.listener.IShellCallBack;
import com.sgm.iorecord.utils.CommandExecution;

import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created by s2s8tb on 2019/9/26.
 */

public class MainContract {

    public interface View {
        void showToast(String str);

        void showLoadding();

        void hideLoadding();
    }

    public interface Presenter {
        void registerTimeRefreshService();

        void registerProcessListener();

        void insertIOList(List<IOTopBean> ioBeans);

        void insertIOData(IOTopBean ioBean);

        IOTopBean queryById(String id);

        void queryAll();

        void executeShell(String shell, boolean isRoot, IShellCallBack shellCallBack);

        void executeShellAndDBAsync(final String shell, final boolean isRoot);

        void registerService();

        List<IOTopBean> convertToIOBeanListFromResult(@Nullable CommandExecution.CommandResult result);
    }
}
