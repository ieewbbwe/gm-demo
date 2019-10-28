package com.sgm.iorecord.chart;

import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.github.mikephil.charting.data.PieEntry;
import com.sgm.iorecord.base.BaseView;
import com.sgm.iorecord.model.IOTopBean;

import java.util.List;

public class ChartContract {

    public interface View extends BaseView {

        void showBarChart(List<IOTopBean> ioTopBeans);

    }

    public interface Presenter {
        void queryIOTopAllAsync();

    }
}
