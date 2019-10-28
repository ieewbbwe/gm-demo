package com.sgm.iorecord.chart.fragment;

import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.github.mikephil.charting.data.PieEntry;
import com.sgm.iorecord.base.BaseView;

import java.util.Date;
import java.util.List;

/**
 * Created by s2s8tb on 2019/10/18.
 */

public class PieChartContract {

    public interface View extends BaseView {
        void showPieChart(List<PieEntry> entries);

        void showStartTimePicker(OnTimeSelectListener selectListener);

        void showEndTimePicker(OnTimeSelectListener selectListener);
    }

    public interface Presenter {
        void queryIOTopByPackage(Date startTime, Date endTime);
    }
}
