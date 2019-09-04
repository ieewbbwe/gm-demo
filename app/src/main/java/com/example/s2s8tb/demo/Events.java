package com.example.s2s8tb.demo;

public class Events {
    public static final String EVENT_R_DURATION = "P3600S";

    private Integer mId;
    private Integer mAccountId;
    private String mGuid;
    private String mContentString;
    private String mLocationString;
    private Long mBeginTime;
    private Long mEndTime;
    private String mEventTimezoneString;
    private String mEventEndTimezoneString;
    private String mRruleString;
    private String mRdateString;
    private Boolean mHasAlarm;
    private String mVrVoiceAddressString;
    private Integer mReminderType;
    private Integer mVehicleType;
    private Boolean mIsVoice;
    private Boolean mIsExpire;
    private int mRepeat;
    private String mLon;
    private String mLat;
    private String mCreateDate;
    private String mUpdateDate;
    private long mRecordId;
    private String mDuration;
    private int mCalendarId;
    private String mSyncId;
    private Long mLastDate;
    private int mTimeType;
    private Long mInstancesTime;
    private int mStartDay;
    private boolean mIsTriggered;

    public boolean isTriggered() {
        return mIsTriggered;
    }

    public void setTriggered(boolean triggered) {
        this.mIsTriggered = triggered;
    }

    public int getStartDay() {
        return mStartDay;
    }

    public void setStartDay(int startDay) {
        mStartDay = startDay;
    }

    public Long getInstancesTime() {
        return mInstancesTime;
    }

    public void setInstancesTime(Long instancesTime) {
        mInstancesTime = instancesTime;
    }

    public int getTimeType() {
        return mTimeType;
    }

    public void setTimeType(int timeType) {
        mTimeType = timeType;
    }

    public Long getLastDate() {
        return mLastDate;
    }

    public void setLastDate(Long lastDate) {
        mLastDate = lastDate;
    }

    public int getCalendarId() {
        return mCalendarId;
    }

    public void setCalendarId(int calendarId) {
        mCalendarId = calendarId;
    }

    public String getSyncId() {
        return mSyncId;
    }

    public void setSyncId(String syncId) {
        mSyncId = syncId;
    }

    public String getDuration() {
        return mDuration;
    }

    public void setDuration(String duration) {
        mDuration = duration;
    }

    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        mId = id;
    }

    public Integer getAccountId() {
        return mAccountId;
    }

    public void setAccountId(Integer accountId) {
        mAccountId = accountId;
    }

    public String getGuid() {
        return mGuid;
    }

    public void setGuid(String guid) {
        mGuid = guid;
    }

    public String getContent() {
        return mContentString;
    }

    public void setContent(String contentString) {
        mContentString = contentString;
    }

    public String getLocation() {
        return mLocationString;
    }

    public void setLocation(String locationString) {
        mLocationString = locationString;
    }

    public Long getBeginTime() {
        return mBeginTime;
    }

    public void setBeginTime(Long beginTime) {
        mBeginTime = beginTime;
    }

    /**
     * If repeat events, return null, ref: getLastDate()
     */
    public Long getEndTime() {
        return mEndTime;
    }

    public void setEndTime(Long endTime) {
        mEndTime = endTime;
    }

    public String getEventTimezone() {
        return mEventTimezoneString;
    }

    public void setEventTimezone(String eventTimezoneString) {
        mEventTimezoneString = eventTimezoneString;
    }

    public String getEventEndTimezone() {
        return mEventEndTimezoneString;
    }

    public void setEventEndTimezone(String eventEndTimezoneString) {
        mEventEndTimezoneString = eventEndTimezoneString;
    }

    public String getRrule() {
        return mRruleString;
    }

    public void setRrule(String rruleString) {
        mRruleString = rruleString;
    }

    public String getRdate() {
        return mRdateString;
    }

    public void setRdate(String rdateString) {
        mRdateString = rdateString;
    }

    public Boolean getHasAlarm() {
        return mHasAlarm;
    }

    public void setHasAlarm(Boolean hasAlarm) {
        mHasAlarm = hasAlarm;
    }

    public String getVrVoiceAddress() {
        return mVrVoiceAddressString;
    }

    public void setVrVoiceAddress(String vrVoiceAddressString) {
        mVrVoiceAddressString = vrVoiceAddressString;
    }

    public Integer getReminderType() {
        return mReminderType;
    }

    public void setReminderType(Integer eventType) {
        mReminderType = eventType;
    }

    public Integer getVehicleType() {
        return mVehicleType;
    }

    public void setVehicleType(Integer type) {
        mVehicleType = type;
    }

    public Boolean getIsVoice() {
        return mIsVoice;
    }

    public void setIsVoice(Boolean voice) {
        mIsVoice = voice;
    }

    public Boolean getIsExpire() {
        return mIsExpire;
    }

    public void setIsExpire(Boolean expire) {
        mIsExpire = expire;
    }

    public int getRepeat() {
        return mRepeat;
    }

    public void setRepeat(int repeat) {
        mRepeat = repeat;
    }

    public String getLon() {
        return mLon;
    }

    public void setLon(String lon) {
        mLon = lon;
    }

    public String getLat() {
        return mLat;
    }

    public void setLat(String lat) {
        mLat = lat;
    }

    public String getCreateDate() {
        return mCreateDate;
    }

    public void setCreateDate(String createDate) {
        mCreateDate = createDate;
    }

    public String getUpdateDate() {
        return mUpdateDate;
    }

    public void setUpdateDate(String updateDate) {
        mUpdateDate = updateDate;
    }

    public long getRecordId() {
        return mRecordId;
    }

    public void setRecordId(long recordId) {
        mRecordId = recordId;
    }


    /**
     * copy events entry to others
     *
     * @param events
     */
    public Events copyTo(Events events) {
        events.setReminderType(getReminderType());
        events.setVehicleType(getVehicleType());
        events.setTimeType(getTimeType());
        events.setEndTime(getEndTime());
        events.setLastDate(getLastDate());
        events.setSyncId(getSyncId());
        events.setCalendarId(getCalendarId());
        events.setRrule(getRrule());
        events.setAccountId(getAccountId());
        events.setBeginTime(getBeginTime());
        events.setContent(getContent());
        events.setCreateDate(getCreateDate());
        events.setDuration(getDuration());
        events.setEventEndTimezone(getEventEndTimezone());
        events.setEventTimezone(getEventTimezone());
        events.setGuid(getGuid());
        events.setHasAlarm(getHasAlarm());
        events.setId(getId());
        events.setIsExpire(getIsExpire());
        events.setIsVoice(getIsVoice());
        events.setLat(getLat());
        events.setLon(getLon());
        events.setLocation(getLocation());
        events.setRdate(getRdate());
        events.setRecordId(getRecordId());
        events.setRepeat(getRepeat());
        events.setUpdateDate(getUpdateDate());
        events.setVrVoiceAddress(getVrVoiceAddress());
        events.setInstancesTime(getInstancesTime());
        events.setStartDay(getStartDay());
        return events;
    }

    @Override
    public String toString() {
        return "Events{" +
                "mId=" + mId +
                ", mAccountId=" + mAccountId +
                ", mGuid='" + mGuid + '\'' +
                ", mContentString='" + mContentString + '\'' +
                ", mLocationString='" + mLocationString + '\'' +
                ", mBeginTime=" + mBeginTime +
                ", mEndTime=" + mEndTime +
                ", mEventTimezoneString='" + mEventTimezoneString + '\'' +
                ", mEventEndTimezoneString='" + mEventEndTimezoneString + '\'' +
                ", mRruleString='" + mRruleString + '\'' +
                ", mRdateString='" + mRdateString + '\'' +
                ", mHasAlarm=" + mHasAlarm +
                ", mVrVoiceAddressString='" + mVrVoiceAddressString + '\'' +
                ", mReminderType=" + mReminderType +
                ", mVehicleType=" + mVehicleType +
                ", mIsVoice=" + mIsVoice +
                ", mIsExpire=" + mIsExpire +
                ", mRepeat=" + mRepeat +
                ", mLon='" + mLon + '\'' +
                ", mLat='" + mLat + '\'' +
                ", mCreateDate='" + mCreateDate + '\'' +
                ", mUpdateDate='" + mUpdateDate + '\'' +
                ", mRecordId=" + mRecordId +
                ", mDuration='" + mDuration + '\'' +
                ", mCalendarId=" + mCalendarId +
                ", mSyncId='" + mSyncId + '\'' +
                ", mLastDate=" + mLastDate +
                ", mTimeType=" + mTimeType +
                ", mInstancesTime=" + mInstancesTime +
                ", mStartDay=" + mStartDay +
                ", mIsTriggered=" + mIsTriggered +
                '}';
    }
}
