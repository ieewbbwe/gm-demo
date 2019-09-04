package com.example.calendar;

/**
 * Created by s2s8tb on 2019/6/25.
 */

public class ListItemBean {

    private String id;
    private String name;
    private boolean isDelete;

    public ListItemBean(String id, String name,boolean isDel) {
        this.id = id;
        this.name = name;
        this.isDelete = isDel;
    }

    public ListItemBean() {

    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
