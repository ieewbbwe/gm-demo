package com.example.calendar.gsonDemo;

/**
 * Created by s2s8tb on 2019/7/2.
 */

public class GsonBean<T> {
    private String name;
    private T t;

    public GsonBean() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
}
