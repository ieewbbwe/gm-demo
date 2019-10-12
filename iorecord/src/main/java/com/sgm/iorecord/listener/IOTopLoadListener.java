package com.sgm.iorecord.listener;

import com.sgm.iorecord.model.IOTopBean;

import java.util.List;

public interface IOTopLoadListener {

    void onTaskStart();

    void onTaskFinished(List<IOTopBean> ioTopBeans);
}
