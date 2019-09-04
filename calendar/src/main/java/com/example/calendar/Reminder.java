package com.example.calendar;

/**
 * Created by s2s8tb on 2019/6/27.
 */

public class Reminder {

    private String _id;
    private String event_id;
    private String minutes;
    private String method;

    public Reminder() {
    }

    public Reminder(String _id, String event_id, String minutes, String method) {
        this._id = _id;
        this.event_id = event_id;
        this.minutes = minutes;
        this.method = method;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    public String getMinutes() {
        return minutes;
    }

    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
