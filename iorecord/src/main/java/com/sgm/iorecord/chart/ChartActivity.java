package com.sgm.iorecord.chart;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.sgm.iorecord.R;
import com.sgm.iorecord.event.RXLoadIoTopAllEvent;
import com.sgm.iorecord.event.rx.RxBus;

import io.reactivex.functions.Consumer;

public class ChartActivity extends AppCompatActivity implements ChartContract.View {


    private ChartPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        mPresenter = new ChartPresenter(this);
        mPresenter.queryIOTopAllAsync();
        RxBus.get().doSubscribe(RXLoadIoTopAllEvent.class, new Consumer<RXLoadIoTopAllEvent>() {
            @Override
            public void accept(RXLoadIoTopAllEvent rxLoadIoTopAllEvent) throws Exception {
                Log.d("picher", "数据个数：" + rxLoadIoTopAllEvent.getIoTopBeans().size());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        });
    }

    @Override
    public void showPieChart() {

    }
}
