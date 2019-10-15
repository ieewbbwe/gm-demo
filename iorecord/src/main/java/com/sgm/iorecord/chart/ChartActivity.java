package com.sgm.iorecord.chart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.sgm.iorecord.R;
import com.sgm.iorecord.base.BaseActivity;
import com.sgm.iorecord.model.IOTopBean;

import java.util.List;

public class ChartActivity extends BaseActivity implements ChartContract.View {

    private ChartPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        mPresenter = new ChartPresenter(this);
        //mPresenter.queryIOTopAllAsync();
        mPresenter.queryIOTopByPackage();
    }

    @Override
    public void showPieChart(List<IOTopBean> ioTopBeans) {
        Log.d("picher", "数据个数：" + ioTopBeans.size());

    }

    @Override
    public void showBarChart(List<IOTopBean> ioTopBeans) {

    }
}
