package com.sgm.iorecord.chart;

import com.sgm.iorecord.base.BaseView;
import com.sgm.iorecord.model.IOTopBean;

import java.util.List;

public class ChartContract {

    public interface View extends BaseView{

        void showPieChart(List<IOTopBean> ioTopBeans);

        void showBarChart(List<IOTopBean> ioTopBeans);
    }

    public interface Presenter {
        void queryIOTopAllAsync();

        void queryIOTopByPackage();
    }
}
