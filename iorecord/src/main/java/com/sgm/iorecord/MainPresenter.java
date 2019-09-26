package com.sgm.iorecord;

import com.sgm.iorecord.bean.IOBean;
import com.sgm.iorecord.databases.DbController;

import java.util.List;

/**
 * Created by s2s8tb on 2019/9/26.
 */

public class MainPresenter implements MainContract.Persenter {

    private final MainContract.View mView;

    public MainPresenter(MainContract.View v) {
        mView = v;
    }

    @Override
    public void registerTimeRefreshService() {

    }

    @Override
    public void registerProcessListener() {

    }

    @Override
    public void insertIOList(List<IOBean> ioBeans) {
        DbController.getInstance().getSession().startAsyncSession().insertInTx(IOBean.class, ioBeans);
    }

    @Override
    public void insertIOData(IOBean ioBean) {
        DbController.getInstance().getSession().getIOBeanDao().insert(ioBean);
    }

    @Override
    public IOBean queryById(String id) {
        return null;
    }

    @Override
    public List<IOBean> queryAll() {
        return DbController.getInstance().getSession().getIOBeanDao().loadAll();
    }

}
