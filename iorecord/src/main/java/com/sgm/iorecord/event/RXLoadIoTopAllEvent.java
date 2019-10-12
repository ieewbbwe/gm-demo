package com.sgm.iorecord.event;

import com.sgm.iorecord.model.IOTopBean;
import com.sgm.iorecord.event.rx.BaseRxBusEvent;

import java.util.List;

public class RXLoadIoTopAllEvent extends BaseRxBusEvent {
    private List<IOTopBean> ioTopBeans;

    public RXLoadIoTopAllEvent(List<IOTopBean> ioTopBeans) {
        this.ioTopBeans = ioTopBeans;
    }

    public List<IOTopBean> getIoTopBeans() {
        return ioTopBeans;
    }

    public void setIoTopBeans(List<IOTopBean> ioTopBeans) {
        this.ioTopBeans = ioTopBeans;
    }
}
