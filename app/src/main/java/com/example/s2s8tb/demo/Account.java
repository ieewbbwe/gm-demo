package com.example.s2s8tb.demo;

/**
 * Created by s2s8tb on 2019/6/19.
 */

public class Account {
    private String _id;
    private String account_name;
    private String account_type;
    private String _sync_id;
    private String dirty;
    private String mutators;
    private String calendar_display_name;
    private String calendar_color;
    private String calendar_color_index;
    private String calendar_access_level;
    private String visible;
    private String sync_events;
    private String calendar_location;
    private String calendar_timezone;
    private String ownerAccount;
    private String isPrimary;
    private String maxReminders;
    private String allowedReminders;
    private String deleted;
    private String cal_sync1;

    public Account() {
    }

    public Account(String _id, String account_name, String account_type, String _sync_id, String dirty, String mutators, String calendar_display_name, String calendar_color, String calendar_color_index, String calendar_access_level, String visible, String sync_events, String calendar_location, String calendar_timezone, String ownerAccount, String isPrimary, String maxReminders, String allowedReminders, String deleted, String cal_sync1) {
        this._id = _id;
        this.account_name = account_name;
        this.account_type = account_type;
        this._sync_id = _sync_id;
        this.dirty = dirty;
        this.mutators = mutators;
        this.calendar_display_name = calendar_display_name;
        this.calendar_color = calendar_color;
        this.calendar_color_index = calendar_color_index;
        this.calendar_access_level = calendar_access_level;
        this.visible = visible;
        this.sync_events = sync_events;
        this.calendar_location = calendar_location;
        this.calendar_timezone = calendar_timezone;
        this.ownerAccount = ownerAccount;
        this.isPrimary = isPrimary;
        this.maxReminders = maxReminders;
        this.allowedReminders = allowedReminders;
        this.deleted = deleted;
        this.cal_sync1 = cal_sync1;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    public String get_sync_id() {
        return _sync_id;
    }

    public void set_sync_id(String _sync_id) {
        this._sync_id = _sync_id;
    }

    public String getDirty() {
        return dirty;
    }

    public void setDirty(String dirty) {
        this.dirty = dirty;
    }

    public String getMutators() {
        return mutators;
    }

    public void setMutators(String mutators) {
        this.mutators = mutators;
    }

    public String getCalendar_display_name() {
        return calendar_display_name;
    }

    public void setCalendar_display_name(String calendar_display_name) {
        this.calendar_display_name = calendar_display_name;
    }

    public String getCalendar_color() {
        return calendar_color;
    }

    public void setCalendar_color(String calendar_color) {
        this.calendar_color = calendar_color;
    }

    public String getCalendar_color_index() {
        return calendar_color_index;
    }

    public void setCalendar_color_index(String calendar_color_index) {
        this.calendar_color_index = calendar_color_index;
    }

    public String getCalendar_access_level() {
        return calendar_access_level;
    }

    public void setCalendar_access_level(String calendar_access_level) {
        this.calendar_access_level = calendar_access_level;
    }

    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    public String getSync_events() {
        return sync_events;
    }

    public void setSync_events(String sync_events) {
        this.sync_events = sync_events;
    }

    public String getCalendar_location() {
        return calendar_location;
    }

    public void setCalendar_location(String calendar_location) {
        this.calendar_location = calendar_location;
    }

    public String getCalendar_timezone() {
        return calendar_timezone;
    }

    public void setCalendar_timezone(String calendar_timezone) {
        this.calendar_timezone = calendar_timezone;
    }

    public String getOwnerAccount() {
        return ownerAccount;
    }

    public void setOwnerAccount(String ownerAccount) {
        this.ownerAccount = ownerAccount;
    }

    public String getIsPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(String isPrimary) {
        this.isPrimary = isPrimary;
    }

    public String getMaxReminders() {
        return maxReminders;
    }

    public void setMaxReminders(String maxReminders) {
        this.maxReminders = maxReminders;
    }

    public String getAllowedReminders() {
        return allowedReminders;
    }

    public void setAllowedReminders(String allowedReminders) {
        this.allowedReminders = allowedReminders;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public String getCal_sync1() {
        return cal_sync1;
    }

    public void setCal_sync1(String cal_sync1) {
        this.cal_sync1 = cal_sync1;
    }

    @Override
    public String toString() {
        return "Account{" +
                "_id='" + _id + '\'' +
                ", account_name='" + account_name + '\'' +
                ", account_type='" + account_type + '\'' +
                ", _sync_id='" + _sync_id + '\'' +
                ", dirty='" + dirty + '\'' +
                ", mutators='" + mutators + '\'' +
                ", calendar_display_name='" + calendar_display_name + '\'' +
                ", calendar_color='" + calendar_color + '\'' +
                ", calendar_color_index='" + calendar_color_index + '\'' +
                ", calendar_access_level='" + calendar_access_level + '\'' +
                ", visible='" + visible + '\'' +
                ", sync_events='" + sync_events + '\'' +
                ", calendar_location='" + calendar_location + '\'' +
                ", calendar_timezone='" + calendar_timezone + '\'' +
                ", ownerAccount='" + ownerAccount + '\'' +
                ", isPrimary='" + isPrimary + '\'' +
                ", maxReminders='" + maxReminders + '\'' +
                ", allowedReminders='" + allowedReminders + '\'' +
                ", deleted='" + deleted + '\'' +
                ", cal_sync1='" + cal_sync1 + '\'' +
                '}';
    }
}
