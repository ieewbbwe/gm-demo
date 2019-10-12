package com.sgm.iorecord.chart;

import com.sgm.iorecord.model.IOTopBean;

import java.util.List;

public class ChartContract {

    public interface View {
        void showPieChart();
    }

    public interface Presenter {
        List<IOTopBean> queryIOTopAll();

        void queryIOTopAllAsync();
    }
}
