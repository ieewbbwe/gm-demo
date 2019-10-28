package com.sgm.iorecord.model;

public class ProcessInfo {
    private int pid;
    private String processName;

    public ProcessInfo(int pid, String processName) {
        this.pid = pid;
        this.processName = processName;
    }

    public int getPid() {
        return pid;
    }

    public String getProcessName() {
        return processName;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProcessInfo that = (ProcessInfo) o;

        if (pid != that.pid) return false;
        return processName.equals(that.processName);
    }

    @Override
    public int hashCode() {
        int result = pid;
        result = 31 * result + processName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ProcessInfo{");
        sb.append("pid=").append(pid);
        sb.append(", processName='").append(processName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
