package com.sgm.iorecord.chart.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieEntry;
import com.sgm.iorecord.R;
import com.sgm.iorecord.base.BaseChartFragment;
import com.sgm.iorecord.utils.TimerUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by s2s8tb on 2019/10/18.
 */

public class BarChartFragment extends BaseChartFragment implements BarChartContract.View, View.OnClickListener {

    private BarChartPresenter mPresenter;
    private static final int ANIMATE_DURATION = 800;
    private PieChart mChart;
    private TextView mEndTimeTv;
    private TextView mStartTimeTv;
    private Date startTime;
    private Date endTime;
    private TimePickerView mStartTimePk;
    private TimePickerView mEndTimePk;
    private BarChart mBarChart;

    @Override
    protected int create() {
        return R.layout.fragment_chart_bar_layout;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = new BarChartPresenter(this);

        initBarView();
        mStartTimeTv = mView.findViewById(R.id.m_start_time_tv);
        mEndTimeTv = mView.findViewById(R.id.m_end_time_bt);
        mStartTimeTv.setOnClickListener(this);
        mEndTimeTv.setOnClickListener(this);

        startTime = TimerUtils.removeHHMMSS(new Date());
        endTime = TimerUtils.removeHHMMSS(TimerUtils.getTomorrow());
        mStartTimeTv.setText(TimerUtils.formatTime(startTime.getTime(), TimerUtils.FORMAT_YYYY_MM_DD));
        mEndTimeTv.setText(TimerUtils.formatTime(endTime.getTime(), TimerUtils.FORMAT_YYYY_MM_DD));

        refresh();
    }

    private void initBarView() {
        mBarChart = mView.findViewById(R.id.m_bar_chart);

    }

    @Override
    public void refresh() {
        mPresenter.queryIOTopByPackage(startTime, endTime);
    }

    @Override
    public void showBarChart(List<PieEntry> entries) {

    }

    @Override
    public void showStartTimePicker(OnTimeSelectListener selectListener) {
        if (mStartTimePk == null) {
            mStartTimePk = new TimePickerBuilder(activity, selectListener).build();
        }
        mStartTimePk.show();
    }

    @Override
    public void showEndTimePicker(OnTimeSelectListener selectListener) {
        if (mEndTimePk == null) {
            mEndTimePk = new TimePickerBuilder(activity, selectListener).build();
        }
        mEndTimePk.show();
    }

    @Override
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.m_start_time_tv:
                showStartTimePicker(new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        startTime = TimerUtils.removeHHMMSS(date);
                        ((TextView) view).setText(TimerUtils.formatTime(date.getTime(), TimerUtils.FORMAT_YYYY_MM_DD));
                    }
                });
                break;
            case R.id.m_end_time_bt:
                showEndTimePicker(new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        endTime = TimerUtils.removeHHMMSS(date);
                        ((TextView) view).setText(TimerUtils.formatTime(date.getTime(), TimerUtils.FORMAT_YYYY_MM_DD));
                    }
                });
                break;
        }
    }
}
