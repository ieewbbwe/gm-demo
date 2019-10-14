package com.sgm.iorecord.chart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.sgm.iorecord.R;
import com.sgm.iorecord.model.IOTopBean;

import java.util.List;

public class ChartActivity extends AppCompatActivity implements ChartContract.View {

    private ChartPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        mPresenter = new ChartPresenter(this);
        mPresenter.queryIOTopAllAsync();
    }

    @Override
    public void showToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showPieChart(List<IOTopBean> ioTopBeans) {
        Log.d("picher", "数据个数：" + ioTopBeans.size());

    }

    @Override
    public void showBarChart(List<IOTopBean> ioTopBeans) {

    }
}
