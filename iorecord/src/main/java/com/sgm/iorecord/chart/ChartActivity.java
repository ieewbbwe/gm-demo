package com.sgm.iorecord.chart;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Menu;
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
import com.sgm.iorecord.base.BaseActivity;
import com.sgm.iorecord.databases.DataEngine;
import com.sgm.iorecord.model.IOTopBean;
import com.sgm.iorecord.utils.TimerUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChartActivity extends BaseActivity implements ChartContract.View, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private Date startTime = TimerUtils.removeHHMMSS(new Date());
    private Date endTime = TimerUtils.removeHHMMSS(TimerUtils.getTomorrow());

    private ChartPresenter mPresenter;
    private PieChart mChart;
    private TextView mStartTimeTv;
    private TextView mEndTimeTv;
    private SwipeRefreshLayout mChartSrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Visual Chart");
        setContentView(R.layout.activity_chart);
        mPresenter = new ChartPresenter(this);
        //mPresenter.queryIOTopAllAsync();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initChartView();
        mStartTimeTv = findViewById(R.id.m_start_time_tv);
        mEndTimeTv = findViewById(R.id.m_end_time_bt);
        mChartSrl = findViewById(R.id.m_chart_srl);
        mStartTimeTv.setOnClickListener(this);
        mEndTimeTv.setOnClickListener(this);
        mChartSrl.setOnRefreshListener(this);

        mStartTimeTv.setText(TimerUtils.formatTime(startTime.getTime(), TimerUtils.FORMAT_YYYY_MM_DD));
        mEndTimeTv.setText(TimerUtils.formatTime(endTime.getTime(), TimerUtils.FORMAT_YYYY_MM_DD));

        mPresenter.queryIOTopByPackage(startTime, endTime);
    }

    private void initChartView() {
        mChart = findViewById(R.id.m_pie_chart);
        mChart.setUsePercentValues(true);
        mChart.getDescription().setEnabled(false);
        mChart.setExtraOffsets(5, 10, 5, 5);

        mChart.setDragDecelerationFrictionCoef(0.95f);

        //chart.setCenterTextTypeface(tfLight);
        mChart.setCenterText("History Written");

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

        // chart.setUnit(" €");
        // chart.setDrawUnitsInChart(true);

        // add a selection listener
        //mChart.setOnChartValueSelectedListener(this);

        mChart.animateY(1400, Easing.EaseInOutQuad);
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

        mChart.invalidate();
    }

    @Override
    public void showBarChart(List<IOTopBean> ioTopBeans) {

    }

    @Override
    public void showTimePicker(OnTimeSelectListener selectListener) {
        TimePickerView pvTime = new TimePickerBuilder(ChartActivity.this, selectListener).build();
        pvTime.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pie, menu);
        return true;
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
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.m_start_time_tv:
                showTimePicker(new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        startTime = TimerUtils.removeHHMMSS(date);
                        ((TextView) view).setText(TimerUtils.formatTime(date.getTime(), TimerUtils.FORMAT_YYYY_MM_DD));
                    }
                });
                break;
            case R.id.m_end_time_bt:
                showTimePicker(new OnTimeSelectListener() {
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
    public void onRefresh() {
        mPresenter.queryIOTopByPackage(startTime, endTime);
    }

    @Override
    public void showLoading() {
        //super.showLoading();
        mChartSrl.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        //super.hideLoading();
        mChartSrl.setRefreshing(false);
    }
}
