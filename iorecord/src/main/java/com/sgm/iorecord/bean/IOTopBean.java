package com.sgm.iorecord.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;

/**
 * Created by s2s8tb on 2019/10/9.
 * 使用IOTOP工具查询到的数据实体
 */
@Entity
public class IOTopBean {
    @Id(autoincrement = true)//设置自增长
    private Long id;

    private String PID;

    @Override
    public String toString() {
        return "IOTopBean{" +
                "id=" + id +
                ", PID='" + PID + '\'' +
                ", READ='" + READ + '\'' +
                ", WRITTEN='" + WRITTEN + '\'' +
                ", READ_SPEED='" + READ_SPEED + '\'' +
                ", WRITE_SPEED='" + WRITE_SPEED + '\'' +
                ", PROCESS='" + PROCESS + '\'' +
                ", date=" + date +
                '}';
    }

    private String READ;
    private String WRITTEN;
    private String READ_SPEED;
    private String WRITE_SPEED;
    private String PROCESS;
    private Date date;

    public IOTopBean(String PID, String READ, String WRITTEN,
                     String READ_SPEED, String WRITE_SPEED, String PROCESS, Date date) {
        this.PID = PID;
        this.READ = READ;
        this.WRITTEN = WRITTEN;
        this.READ_SPEED = READ_SPEED;
        this.WRITE_SPEED = WRITE_SPEED;
        this.PROCESS = PROCESS;
        this.date = date;
    }

    @Generated(hash = 2057499034)
    public IOTopBean(Long id, String PID, String READ, String WRITTEN, String READ_SPEED,
                     String WRITE_SPEED, String PROCESS, Date date) {
        this.id = id;
        this.PID = PID;
        this.READ = READ;
        this.WRITTEN = WRITTEN;
        this.READ_SPEED = READ_SPEED;
        this.WRITE_SPEED = WRITE_SPEED;
        this.PROCESS = PROCESS;
        this.date = date;
    }

    @Generated(hash = 1785625366)
    public IOTopBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPID() {
        return this.PID;
    }

    public void setPID(String PID) {
        this.PID = PID;
    }

    public String getREAD() {
        return this.READ;
    }

    public void setREAD(String READ) {
        this.READ = READ;
    }

    public String getWRITTEN() {
        return this.WRITTEN;
    }

    public void setWRITTEN(String WRITTEN) {
        this.WRITTEN = WRITTEN;
    }

    public String getREAD_SPEED() {
        return this.READ_SPEED;
    }

    public void setREAD_SPEED(String READ_SPEED) {
        this.READ_SPEED = READ_SPEED;
    }

    public String getWRITE_SPEED() {
        return this.WRITE_SPEED;
    }

    public void setWRITE_SPEED(String WRITE_SPEED) {
        this.WRITE_SPEED = WRITE_SPEED;
    }

    public String getPROCESS() {
        return this.PROCESS;
    }

    public void setPROCESS(String PROCESS) {
        this.PROCESS = PROCESS;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


}
