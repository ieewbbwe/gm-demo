package com.sgm.iorecord.chart;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.sgm.iorecord.R;
import com.sgm.iorecord.base.BaseActivity;
import com.sgm.iorecord.databases.DataEngine;
import com.sgm.iorecord.model.IOTopBean;

import java.util.List;

//TODO 按照所选时间段插数据
public class ChartActivity extends BaseActivity implements ChartContract.View {

    private ChartPresenter mPresenter;
    private PieChart mChart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Visual Chart");
        setContentView(R.layout.activity_chart);
        mPresenter = new ChartPresenter(this);
        //mPresenter.queryIOTopAllAsync();
        mPresenter.queryIOTopByPackage();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initChartView();
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
    public void showPieChart(List<IOTopBean> ioTopBeans) {
        PieDataSet dataSet = DataEngine.gerInstance().convertToPieDataFromTopList(ioTopBeans);

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
}
