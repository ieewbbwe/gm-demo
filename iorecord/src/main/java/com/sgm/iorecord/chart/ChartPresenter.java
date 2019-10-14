package com.sgm.iorecord.chart;

import com.sgm.iorecord.base.BasePresenter;
import com.sgm.iorecord.databases.DbController;
import com.sgm.iorecord.model.IOTopBean;
import com.sgm.iorecord.useCase.SimpleUseCaseCallBack;
import com.sgm.iorecord.useCase.UseCaseHandler;
import com.sgm.iorecord.useCase.main.QueryTask;

import java.util.List;

public class ChartPresenter extends BasePresenter<ChartContract.View>
        implements ChartContract.Presenter {

    private final QueryTask mQueryTask;
    private final UseCaseHandler mUseCaseHandler;

    public ChartPresenter(ChartContract.View view) {
        super(view);
        mQueryTask = new QueryTask();
        mUseCaseHandler = UseCaseHandler.getInstance();
    }

    @Override
    public List<IOTopBean> queryIOTopAll() {
        return DbController.getInstance().getSession().getIOTopBeanDao().loadAll();
    }

    @Override
    public void queryIOTopAllAsync() {
        mView.showToast("Loading...");
        mUseCaseHandler.execute(mQueryTask, new QueryTask.RequestValues(), new SimpleUseCaseCallBack<QueryTask.ResponseValue>() {
            @Override
            public void onSuccess(QueryTask.ResponseValue response) {
                mView.showPieChart(response.getIoTopBeans());
                mView.showToast("load succeed!");
            }
        });
    }
}
