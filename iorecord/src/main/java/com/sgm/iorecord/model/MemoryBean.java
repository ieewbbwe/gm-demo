package com.sgm.iorecord.model;

/**
 * Created by s2s8tb on 2019/11/2.
 */

public class MemoryBean {

    private int pid;
    private String processName;
    private int totalPss;
    private int totalPrivateDirty;

    public MemoryBean(int pid, String processName, int totalPss, int totalPrivateDirty) {
        this.pid = pid;
        this.processName = processName;
        this.totalPss = totalPss;
        this.totalPrivateDirty = totalPrivateDirty;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public int getTotalPss() {
        return totalPss;
    }

    public void setTotalPss(int totalPss) {
        this.totalPss = totalPss;
    }

    public int getTotalPrivateDirty() {
        return totalPrivateDirty;
    }

    public void setTotalPrivateDirty(int totalPrivateDirty) {
        this.totalPrivateDirty = totalPrivateDirty;
    }
}
