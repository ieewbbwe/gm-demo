package com.sgm.iorecord.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;

/**
 * Created by s2s8tb on 2019/9/26.
 * proc/{pid}/io 实体类
 */

@Entity
public class IOBean {
    @Id(autoincrement = true)//设置自增长
    private Long id;

    private String rchar;
    private String wchar;
    private String syscr;
    private String syscw;
    private String read_bytes;
    private String write_bytes;
    private String cancelled_write_bytes;
    private Date date;

    @Generated(hash = 1457369975)
    public IOBean(Long id, String rchar, String wchar, String syscr, String syscw,
                  String read_bytes, String write_bytes, String cancelled_write_bytes,
                  Date date) {
        this.id = id;
        this.rchar = rchar;
        this.wchar = wchar;
        this.syscr = syscr;
        this.syscw = syscw;
        this.read_bytes = read_bytes;
        this.write_bytes = write_bytes;
        this.cancelled_write_bytes = cancelled_write_bytes;
        this.date = date;
    }

    @Generated(hash = 872658843)
    public IOBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRchar() {
        return this.rchar;
    }

    public void setRchar(String rchar) {
        this.rchar = rchar;
    }

    public String getWchar() {
        return this.wchar;
    }

    public void setWchar(String wchar) {
        this.wchar = wchar;
    }

    public String getSyscr() {
        return this.syscr;
    }

    public void setSyscr(String syscr) {
        this.syscr = syscr;
    }

    public String getSyscw() {
        return this.syscw;
    }

    public void setSyscw(String syscw) {
        this.syscw = syscw;
    }

    public String getRead_bytes() {
        return this.read_bytes;
    }

    public void setRead_bytes(String read_bytes) {
        this.read_bytes = read_bytes;
    }

    public String getWrite_bytes() {
        return this.write_bytes;
    }

    public void setWrite_bytes(String write_bytes) {
        this.write_bytes = write_bytes;
    }

    public String getCancelled_write_bytes() {
        return this.cancelled_write_bytes;
    }

    public void setCancelled_write_bytes(String cancelled_write_bytes) {
        this.cancelled_write_bytes = cancelled_write_bytes;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    @Override
    public String toString() {
        return "IOBean{" +
                "id=" + id +
                ", rchar='" + rchar + '\'' +
                ", wchar='" + wchar + '\'' +
                ", syscr='" + syscr + '\'' +
                ", syscw='" + syscw + '\'' +
                ", read_bytes='" + read_bytes + '\'' +
                ", write_bytes='" + write_bytes + '\'' +
                ", cancelled_write_bytes='" + cancelled_write_bytes + '\'' +
                ", date=" + date +
                '}';
    }
}
