package com.example.calendar;

public class Events {
    private String originalAllDay;
    private String account_type;
    private String exrule;
    private String mutators;
    private String originalInstanceTime;
    private String allDay;//是否是全天事件
    private String allowedReminders;
    private String rrule;//重复规则
    private String location_longitude;
    private String canOrganizerRespond;
    private String lastDate;
    private String visible;
    private String calendar_id;// 所属日历
    private String hasExtendedProperties;
    private String calendar_access_level;
    private String selfAttendeeStatus;
    private String version;
    private String allowedAvailability;
    private String eventColor_index;
    private String isOrganizer;
    private String _sync_id;
    private String event_image_type;
    private String calendar_color_index;
    private String _id;
    private String guestsCanInviteOthers;
    private String allowedAttendeeTypes;
    private String dtstart;//事件启动时间
    private String guestsCanSeeGuests;
    private String sync_data9;
    private String sync_data8;
    private String exdate;
    private String sync_data7;
    private String sync_data6;
    private String sync_data1;
    private String description;//事件描述
    private String eventTimezone;
    private String availability;
    private String title;// 事件标题
    private String ownerAccount;
    private String sync_data5;
    private String sync_data4;
    private String sync_data3;
    private String sync_data2;
    private String duration;//事件持续时间
    private String event_calendar_type;
    private String lastSynced;
    private String guestsCanModify;
    private String cal_sync3;
    private String rdate;//事件重复日期
    private String cal_sync2;
    private String maxReminders;
    private String cal_sync1;
    private String cal_sync10;
    private String account_name;
    private String cal_sync7;
    private String cal_sync6;
    private String cal_sync5;
    private String cal_sync4;
    private String calendar_color;
    private String cal_sync9;
    private String cal_sync8;
    private String dirty;
    private String calendar_timezone;
    private String accessLevel;
    private String eventLocation;//事件地址
    private String location_latitude;
    private String hasAlarm;
    private String uid2445;
    private String deleted;
    private String eventColor;
    private String organizer;//组织者
    private String eventStatus;
    private String customAppUri;
    private String canModifyTimeZone;
    private String eventEndTimezone;
    private String customAppPackage;
    private String original_sync_id;
    private String hasAttendeeData;
    private String displayColor;
    private String dtend;//事件结束时间
    private String original_id;
    private String sync_data10;
    private String calendar_displayNameprivate;

    public Events() {
    }

    public Events(String account_type, String exrule, String allDay, String rrule, String lastDate, String calendar_id, String eventColor_index, String dtstart, String sync_data1, String description, String eventTimezone, String title, String ownerAccount, String duration, String rdate, String maxReminders, String account_name, String dirty, String hasAlarm, String deleted, String dtend) {
        this.account_type = account_type;
        this.exrule = exrule;
        this.allDay = allDay;
        this.rrule = rrule;
        this.lastDate = lastDate;
        this.calendar_id = calendar_id;
        this.eventColor_index = eventColor_index;
        this.dtstart = dtstart;
        this.sync_data1 = sync_data1;
        this.description = description;
        this.eventTimezone = eventTimezone;
        this.title = title;
        this.ownerAccount = ownerAccount;
        this.duration = duration;
        this.rdate = rdate;
        this.maxReminders = maxReminders;
        this.account_name = account_name;
        this.dirty = dirty;
        this.hasAlarm = hasAlarm;
        this.deleted = deleted;
        this.dtend = dtend;
    }

    @Override
    public String toString() {
        return "Events{" +
                "originalAllDay='" + originalAllDay + '\'' +
                ", account_type='" + account_type + '\'' +
                ", exrule='" + exrule + '\'' +
                ", mutators='" + mutators + '\'' +
                ", originalInstanceTime='" + originalInstanceTime + '\'' +
                ", allDay='" + allDay + '\'' +
                ", allowedReminders='" + allowedReminders + '\'' +
                ", rrule='" + rrule + '\'' +
                ", location_longitude='" + location_longitude + '\'' +
                ", canOrganizerRespond='" + canOrganizerRespond + '\'' +
                ", lastDate='" + lastDate + '\'' +
                ", visible='" + visible + '\'' +
                ", calendar_id='" + calendar_id + '\'' +
                ", hasExtendedProperties='" + hasExtendedProperties + '\'' +
                ", calendar_access_level='" + calendar_access_level + '\'' +
                ", selfAttendeeStatus='" + selfAttendeeStatus + '\'' +
                ", version='" + version + '\'' +
                ", allowedAvailability='" + allowedAvailability + '\'' +
                ", eventColor_index='" + eventColor_index + '\'' +
                ", isOrganizer='" + isOrganizer + '\'' +
                ", _sync_id='" + _sync_id + '\'' +
                ", event_image_type='" + event_image_type + '\'' +
                ", calendar_color_index='" + calendar_color_index + '\'' +
                ", _id='" + _id + '\'' +
                ", guestsCanInviteOthers='" + guestsCanInviteOthers + '\'' +
                ", allowedAttendeeTypes='" + allowedAttendeeTypes + '\'' +
                ", dtstart='" + dtstart + '\'' +
                ", guestsCanSeeGuests='" + guestsCanSeeGuests + '\'' +
                ", exdate='" + exdate + '\'' +
                ", sync_data1='" + sync_data1 + '\'' +
                ", description='" + description + '\'' +
                ", eventTimezone='" + eventTimezone + '\'' +
                ", availability='" + availability + '\'' +
                ", title='" + title + '\'' +
                ", ownerAccount='" + ownerAccount + '\'' +
                ", duration='" + duration + '\'' +
                ", event_calendar_type='" + event_calendar_type + '\'' +
                ", lastSynced='" + lastSynced + '\'' +
                ", guestsCanModify='" + guestsCanModify + '\'' +
                ", rdate='" + rdate + '\'' +
                ", maxReminders='" + maxReminders + '\'' +
                ", cal_sync1='" + cal_sync1 + '\'' +
                ", account_name='" + account_name + '\'' +
                ", calendar_color='" + calendar_color + '\'' +
                ", dirty='" + dirty + '\'' +
                ", calendar_timezone='" + calendar_timezone + '\'' +
                ", accessLevel='" + accessLevel + '\'' +
                ", eventLocation='" + eventLocation + '\'' +
                ", location_latitude='" + location_latitude + '\'' +
                ", hasAlarm='" + hasAlarm + '\'' +
                ", deleted='" + deleted + '\'' +
                ", eventColor='" + eventColor + '\'' +
                ", organizer='" + organizer + '\'' +
                ", eventStatus='" + eventStatus + '\'' +
                ", canModifyTimeZone='" + canModifyTimeZone + '\'' +
                ", eventEndTimezone='" + eventEndTimezone + '\'' +
                ", customAppPackage='" + customAppPackage + '\'' +
                ", original_sync_id='" + original_sync_id + '\'' +
                ", hasAttendeeData='" + hasAttendeeData + '\'' +
                ", displayColor='" + displayColor + '\'' +
                ", dtend='" + dtend + '\'' +
                ", original_id='" + original_id + '\'' +
                ", calendar_displayNameprivate='" + calendar_displayNameprivate + '\'' +
                '}';
    }

    public String getOriginalAllDay() {
        return originalAllDay;
    }

    public void setOriginalAllDay(String originalAllDay) {
        this.originalAllDay = originalAllDay;
    }

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    public String getExrule() {
        return exrule;
    }

    public void setExrule(String exrule) {
        this.exrule = exrule;
    }

    public String getMutators() {
        return mutators;
    }

    public void setMutators(String mutators) {
        this.mutators = mutators;
    }

    public String getOriginalInstanceTime() {
        return originalInstanceTime;
    }

    public void setOriginalInstanceTime(String originalInstanceTime) {
        this.originalInstanceTime = originalInstanceTime;
    }

    public String getAllDay() {
        return allDay;
    }

    public void setAllDay(String allDay) {
        this.allDay = allDay;
    }

    public String getAllowedReminders() {
        return allowedReminders;
    }

    public void setAllowedReminders(String allowedReminders) {
        this.allowedReminders = allowedReminders;
    }

    public String getRrule() {
        return rrule;
    }

    public void setRrule(String rrule) {
        this.rrule = rrule;
    }

    public String getLocation_longitude() {
        return location_longitude;
    }

    public void setLocation_longitude(String location_longitude) {
        this.location_longitude = location_longitude;
    }

    public String getCanOrganizerRespond() {
        return canOrganizerRespond;
    }

    public void setCanOrganizerRespond(String canOrganizerRespond) {
        this.canOrganizerRespond = canOrganizerRespond;
    }

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    public String getCalendar_id() {
        return calendar_id;
    }

    public void setCalendar_id(String calendar_id) {
        this.calendar_id = calendar_id;
    }

    public String getHasExtendedProperties() {
        return hasExtendedProperties;
    }

    public void setHasExtendedProperties(String hasExtendedProperties) {
        this.hasExtendedProperties = hasExtendedProperties;
    }

    public String getCalendar_access_level() {
        return calendar_access_level;
    }

    public void setCalendar_access_level(String calendar_access_level) {
        this.calendar_access_level = calendar_access_level;
    }

    public String getSelfAttendeeStatus() {
        return selfAttendeeStatus;
    }

    public void setSelfAttendeeStatus(String selfAttendeeStatus) {
        this.selfAttendeeStatus = selfAttendeeStatus;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAllowedAvailability() {
        return allowedAvailability;
    }

    public void setAllowedAvailability(String allowedAvailability) {
        this.allowedAvailability = allowedAvailability;
    }

    public String getEventColor_index() {
        return eventColor_index;
    }

    public void setEventColor_index(String eventColor_index) {
        this.eventColor_index = eventColor_index;
    }

    public String getIsOrganizer() {
        return isOrganizer;
    }

    public void setIsOrganizer(String isOrganizer) {
        this.isOrganizer = isOrganizer;
    }

    public String get_sync_id() {
        return _sync_id;
    }

    public void set_sync_id(String _sync_id) {
        this._sync_id = _sync_id;
    }

    public String getEvent_image_type() {
        return event_image_type;
    }

    public void setEvent_image_type(String event_image_type) {
        this.event_image_type = event_image_type;
    }

    public String getCalendar_color_index() {
        return calendar_color_index;
    }

    public void setCalendar_color_index(String calendar_color_index) {
        this.calendar_color_index = calendar_color_index;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getGuestsCanInviteOthers() {
        return guestsCanInviteOthers;
    }

    public void setGuestsCanInviteOthers(String guestsCanInviteOthers) {
        this.guestsCanInviteOthers = guestsCanInviteOthers;
    }

    public String getAllowedAttendeeTypes() {
        return allowedAttendeeTypes;
    }

    public void setAllowedAttendeeTypes(String allowedAttendeeTypes) {
        this.allowedAttendeeTypes = allowedAttendeeTypes;
    }

    public String getDtstart() {
        return dtstart;
    }

    public void setDtstart(String dtstart) {
        this.dtstart = dtstart;
    }

    public String getGuestsCanSeeGuests() {
        return guestsCanSeeGuests;
    }

    public void setGuestsCanSeeGuests(String guestsCanSeeGuests) {
        this.guestsCanSeeGuests = guestsCanSeeGuests;
    }

    public String getSync_data9() {
        return sync_data9;
    }

    public void setSync_data9(String sync_data9) {
        this.sync_data9 = sync_data9;
    }

    public String getSync_data8() {
        return sync_data8;
    }

    public void setSync_data8(String sync_data8) {
        this.sync_data8 = sync_data8;
    }

    public String getExdate() {
        return exdate;
    }

    public void setExdate(String exdate) {
        this.exdate = exdate;
    }

    public String getSync_data7() {
        return sync_data7;
    }

    public void setSync_data7(String sync_data7) {
        this.sync_data7 = sync_data7;
    }

    public String getSync_data6() {
        return sync_data6;
    }

    public void setSync_data6(String sync_data6) {
        this.sync_data6 = sync_data6;
    }

    public String getSync_data1() {
        return sync_data1;
    }

    public void setSync_data1(String sync_data1) {
        this.sync_data1 = sync_data1;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEventTimezone() {
        return eventTimezone;
    }

    public void setEventTimezone(String eventTimezone) {
        this.eventTimezone = eventTimezone;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOwnerAccount() {
        return ownerAccount;
    }

    public void setOwnerAccount(String ownerAccount) {
        this.ownerAccount = ownerAccount;
    }

    public String getSync_data5() {
        return sync_data5;
    }

    public void setSync_data5(String sync_data5) {
        this.sync_data5 = sync_data5;
    }

    public String getSync_data4() {
        return sync_data4;
    }

    public void setSync_data4(String sync_data4) {
        this.sync_data4 = sync_data4;
    }

    public String getSync_data3() {
        return sync_data3;
    }

    public void setSync_data3(String sync_data3) {
        this.sync_data3 = sync_data3;
    }

    public String getSync_data2() {
        return sync_data2;
    }

    public void setSync_data2(String sync_data2) {
        this.sync_data2 = sync_data2;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getEvent_calendar_type() {
        return event_calendar_type;
    }

    public void setEvent_calendar_type(String event_calendar_type) {
        this.event_calendar_type = event_calendar_type;
    }

    public String getLastSynced() {
        return lastSynced;
    }

    public void setLastSynced(String lastSynced) {
        this.lastSynced = lastSynced;
    }

    public String getGuestsCanModify() {
        return guestsCanModify;
    }

    public void setGuestsCanModify(String guestsCanModify) {
        this.guestsCanModify = guestsCanModify;
    }

    public String getCal_sync3() {
        return cal_sync3;
    }

    public void setCal_sync3(String cal_sync3) {
        this.cal_sync3 = cal_sync3;
    }

    public String getRdate() {
        return rdate;
    }

    public void setRdate(String rdate) {
        this.rdate = rdate;
    }

    public String getCal_sync2() {
        return cal_sync2;
    }

    public void setCal_sync2(String cal_sync2) {
        this.cal_sync2 = cal_sync2;
    }

    public String getMaxReminders() {
        return maxReminders;
    }

    public void setMaxReminders(String maxReminders) {
        this.maxReminders = maxReminders;
    }

    public String getCal_sync1() {
        return cal_sync1;
    }

    public void setCal_sync1(String cal_sync1) {
        this.cal_sync1 = cal_sync1;
    }

    public String getCal_sync10() {
        return cal_sync10;
    }

    public void setCal_sync10(String cal_sync10) {
        this.cal_sync10 = cal_sync10;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getCal_sync7() {
        return cal_sync7;
    }

    public void setCal_sync7(String cal_sync7) {
        this.cal_sync7 = cal_sync7;
    }

    public String getCal_sync6() {
        return cal_sync6;
    }

    public void setCal_sync6(String cal_sync6) {
        this.cal_sync6 = cal_sync6;
    }

    public String getCal_sync5() {
        return cal_sync5;
    }

    public void setCal_sync5(String cal_sync5) {
        this.cal_sync5 = cal_sync5;
    }

    public String getCal_sync4() {
        return cal_sync4;
    }

    public void setCal_sync4(String cal_sync4) {
        this.cal_sync4 = cal_sync4;
    }

    public String getCalendar_color() {
        return calendar_color;
    }

    public void setCalendar_color(String calendar_color) {
        this.calendar_color = calendar_color;
    }

    public String getCal_sync9() {
        return cal_sync9;
    }

    public void setCal_sync9(String cal_sync9) {
        this.cal_sync9 = cal_sync9;
    }

    public String getCal_sync8() {
        return cal_sync8;
    }

    public void setCal_sync8(String cal_sync8) {
        this.cal_sync8 = cal_sync8;
    }

    public String getDirty() {
        return dirty;
    }

    public void setDirty(String dirty) {
        this.dirty = dirty;
    }

    public String getCalendar_timezone() {
        return calendar_timezone;
    }

    public void setCalendar_timezone(String calendar_timezone) {
        this.calendar_timezone = calendar_timezone;
    }

    public String getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getLocation_latitude() {
        return location_latitude;
    }

    public void setLocation_latitude(String location_latitude) {
        this.location_latitude = location_latitude;
    }

    public String getHasAlarm() {
        return hasAlarm;
    }

    public void setHasAlarm(String hasAlarm) {
        this.hasAlarm = hasAlarm;
    }

    public String getUid2445() {
        return uid2445;
    }

    public void setUid2445(String uid2445) {
        this.uid2445 = uid2445;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public String getEventColor() {
        return eventColor;
    }

    public void setEventColor(String eventColor) {
        this.eventColor = eventColor;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public String getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(String eventStatus) {
        this.eventStatus = eventStatus;
    }

    public String getCustomAppUri() {
        return customAppUri;
    }

    public void setCustomAppUri(String customAppUri) {
        this.customAppUri = customAppUri;
    }

    public String getCanModifyTimeZone() {
        return canModifyTimeZone;
    }

    public void setCanModifyTimeZone(String canModifyTimeZone) {
        this.canModifyTimeZone = canModifyTimeZone;
    }

    public String getEventEndTimezone() {
        return eventEndTimezone;
    }

    public void setEventEndTimezone(String eventEndTimezone) {
        this.eventEndTimezone = eventEndTimezone;
    }

    public String getCustomAppPackage() {
        return customAppPackage;
    }

    public void setCustomAppPackage(String customAppPackage) {
        this.customAppPackage = customAppPackage;
    }

    public String getOriginal_sync_id() {
        return original_sync_id;
    }

    public void setOriginal_sync_id(String original_sync_id) {
        this.original_sync_id = original_sync_id;
    }

    public String getHasAttendeeData() {
        return hasAttendeeData;
    }

    public void setHasAttendeeData(String hasAttendeeData) {
        this.hasAttendeeData = hasAttendeeData;
    }

    public String getDisplayColor() {
        return displayColor;
    }

    public void setDisplayColor(String displayColor) {
        this.displayColor = displayColor;
    }

    public String getDtend() {
        return dtend;
    }

    public void setDtend(String dtend) {
        this.dtend = dtend;
    }

    public String getOriginal_id() {
        return original_id;
    }

    public void setOriginal_id(String original_id) {
        this.original_id = original_id;
    }

    public String getSync_data10() {
        return sync_data10;
    }

    public void setSync_data10(String sync_data10) {
        this.sync_data10 = sync_data10;
    }

    public String getCalendar_displayNameprivate() {
        return calendar_displayNameprivate;
    }

    public void setCalendar_displayNameprivate(String calendar_displayNameprivate) {
        this.calendar_displayNameprivate = calendar_displayNameprivate;
    }
}
