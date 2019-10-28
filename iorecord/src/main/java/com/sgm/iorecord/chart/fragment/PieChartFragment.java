package com.sgm.iorecord.chart.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.sgm.iorecord.R;
import com.sgm.iorecord.base.BaseChartFragment;
import com.sgm.iorecord.databases.DataEngine;
import com.sgm.iorecord.utils.TimerUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by s2s8tb on 2019/10/18.
 * 饼图
 */

public class PieChartFragment extends BaseChartFragment implements PieChartContract.View, View.OnClickListener {

    private static final int ANIMATE_DURATION = 800;
    private PieChart mChart;
    private TextView mEndTimeTv;
    private TextView mStartTimeTv;
    private Date startTime;
    private Date endTime;
    private TimePickerView mStartTimePk;
    private TimePickerView mEndTimePk;
    private PieChartPresenter mPresenter;

    @Override
    protected int create() {
        return R.layout.fragment_chart_pie_layout;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = new PieChartPresenter(this);
        initChartView();
        setHasOptionsMenu(true);
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

    private void initChartView() {
        mChart = mView.findViewById(R.id.m_pie_chart);
        mChart.setUsePercentValues(true);
        mChart.getDescription().setEnabled(false);
        mChart.setExtraOffsets(5, 10, 5, 5);
        mChart.setDragDecelerationFrictionCoef(0.95f);
        //chart.setCenterTextTypeface(tfLight);
        mChart.setCenterText("History Written\n(bytes)");
        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(Color.WHITE);
        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);
        mChart.setHoleRadius(58f);
        mChart.setTransparentCircleRadius(61f);
        mChart.setDrawCenterText(true);
        mChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(true);
        mChart.setHighlightPerTapEnabled(true);
        mChart.animateY(ANIMATE_DURATION, Easing.EaseInOutQuad);
        // chart.spin(2000, 0, 360);

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // entry label styling
        mChart.setEntryLabelColor(Color.RED);
        //chart.setEntryLabelTypeface(tfRegular);
        mChart.setEntryLabelTextSize(12f);
    }


    @Override
    public void showPieChart(List<PieEntry> entries) {
        PieDataSet dataSet = DataEngine.gerInstance().convertToPieDataFromTopList(entries);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter(mChart));
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.RED);

        mChart.setData(data);
        // undo all highlights
        mChart.highlightValues(null);
        mChart.animateY(ANIMATE_DURATION);
        mChart.invalidate();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.piePresentLine:
                showPiePresentLine((PieDataSet) mChart.getData().getDataSet());
                break;
            case R.id.pieDataPresentChange:
                mChart.setUsePercentValues(!mChart.isUsePercentValuesEnabled());
                mChart.invalidate();
                break;
        }
        return true;
    }

    /**
     * 开启/关闭 百分比标记线
     *
     * @param dataSet
     */
    private void showPiePresentLine(PieDataSet dataSet) {
        dataSet.setValueLinePart1OffsetPercentage(80.f);
        dataSet.setValueLinePart1Length(0.2f);
        dataSet.setValueLinePart2Length(0.4f);
        //dataSet.setUsingSliceColorAsValueLineColor(true);
        //dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        if (dataSet.getYValuePosition() == PieDataSet.ValuePosition.OUTSIDE_SLICE) {
            dataSet.setYValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);
        } else {
            dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        }
        mChart.invalidate();
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

    @Override
    public void refresh() {
        mPresenter.queryIOTopByPackage(startTime, endTime);
    }

}
