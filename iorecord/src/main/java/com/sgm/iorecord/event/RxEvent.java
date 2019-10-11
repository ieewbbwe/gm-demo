package com.sgm.iorecord.event;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by mxh on 2017/6/7.
 * Describe：
 */

public class RxEvent<T> {

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({RxEventId.RX_LOGIN_SUCCEED, RxEventId.RX_LOAD_IOTOP_SUCCEED})
    public @interface RxEventId {
        int RX_LOGIN_SUCCEED = 0;
        int RX_LOAD_IOTOP_SUCCEED = 1;
    }


    @RxEvent.RxEventId
    private int eventId;
    private T obj;

    public RxEvent() {
    }

    private RxEvent(int eventId) {
        this.eventId = eventId;
    }

    private RxEvent(int eventId, T obj) {
        this.eventId = eventId;
        this.obj = obj;
    }

    public int getEventId() {
        return eventId;
    }

    public Object getData() {
        return obj;
    }

    public RxEvent create(@RxEventId int eventId) {
        return new RxEvent(eventId);
    }

    public RxEvent create(@RxEventId int eventId, T data) {
        return new RxEvent(eventId, data);
    }
}
