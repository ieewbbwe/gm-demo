package com.sgm.iorecord.model;

public class RecordPattern {
    /**
     * 数据名称
     */
    private String name;

    /**
     * 数据单位
     */
    private String unit;

    /**
     * 数据来源
     */
    private String source;

    /**
     * 启示录制时间
     */
    private Long startTime;

    /**
     * 结束录制时间
     */
    private Long endTime;

    public RecordPattern(String name, String unit, String source) {
        this.name = name;
        this.unit = unit;
        this.source = source;
    }

    public String getName() {
        return name;
    }

    public String getUnit() {
        return unit;
    }

    public String getSource() {
        return source;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RecordPattern that = (RecordPattern) o;

        if (!name.equals(that.name)) return false;
        if (!unit.equals(that.unit)) return false;
        if (!source.equals(that.source)) return false;
        if (startTime != null ? !startTime.equals(that.startTime) : that.startTime != null)
            return false;
        return endTime != null ? endTime.equals(that.endTime) : that.endTime == null;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + unit.hashCode();
        result = 31 * result + source.hashCode();
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        return result;
    }

    /**
     * 单行录制数据
     */
    public static class RecordItem{
        /**
         * 记录时间
         */
        public Long time;

        /**
         * 记录值
         */
        public Float value;

        /**
         * 额外信息
         */
        public String extra;

        public RecordItem(Long time, Float value, String extra) {
            this.time = time;
            this.value = value;
            this.extra = extra;
        }

        @Override
        public String toString() {
            return "RecordItem{" +
                    "time=" + time +
                    ", value=" + value +
                    ", extra='" + extra + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            RecordItem that = (RecordItem) o;

            if (time != null ? !time.equals(that.time) : that.time != null) return false;
            if (value != null ? !value.equals(that.value) : that.value != null) return false;
            return extra != null ? extra.equals(that.extra) : that.extra == null;

        }

        @Override
        public int hashCode() {
            int result = time != null ? time.hashCode() : 0;
            result = 31 * result + (value != null ? value.hashCode() : 0);
            result = 31 * result + (extra != null ? extra.hashCode() : 0);
            return result;
        }
    }
}
