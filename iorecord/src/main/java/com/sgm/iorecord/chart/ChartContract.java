package com.sgm.iorecord.chart;

import com.sgm.iorecord.model.IOTopBean;

import java.util.List;

public class ChartContract {

    public interface View {

        void showToast(String str);

        void showPieChart(List<IOTopBean> ioTopBeans);

        void showBarChart(List<IOTopBean> ioTopBeans);
    }

    public interface Presenter {
        List<IOTopBean> queryIOTopAll();

        void queryIOTopAllAsync();
    }
}
