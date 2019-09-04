package com.example.calendar;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.provider.CalendarContract.CALLER_IS_SYNCADAPTER;
import static android.provider.CalendarContract.Calendars.ACCOUNT_NAME;
import static android.provider.CalendarContract.Calendars.ACCOUNT_TYPE;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ContentResolver contentResolver;
    private static Uri calendarsUri = CalendarContract.Calendars.CONTENT_URI;
    private static Uri eventsUri = CalendarContract.Events.CONTENT_URI;
    private static Uri remindersUri = CalendarContract.Reminders.CONTENT_URI;
    private static Uri instancesUrl = CalendarContract.Instances.CONTENT_URI;
    private ListView mDataLv;
    private List<ListItemBean> listItemBeans;
    private MyAdapter myAdapter;
    private int mSelectedPos = 0;

    private String json = "{\"name\":\"picher\",\"t\":[{\"name\":\"t1\"}]}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contentResolver = getContentResolver();
        Log.d("picher", "MainOnCreate");

        findViewById(R.id.m_query_calendar_bt).setOnClickListener(this);
        findViewById(R.id.m_insert_calendar_bt).setOnClickListener(this);
        findViewById(R.id.m_demo_bt).setOnClickListener(this);
        mDataLv = (ListView) findViewById(R.id.m_data_rl);
        listItemBeans = new ArrayList<>();
        myAdapter = new MyAdapter(listItemBeans);
        mDataLv.setAdapter(myAdapter);

        ((Spinner) findViewById(R.id.m_chose_sp)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mSelectedPos = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public List<Events> createEvent(int size) {
        List<Events> events = new ArrayList<>();
        Events item;
        for (int i = 0; i <= size; i++) {
            item = new Events();
            events.add(item);
        }
        return events;
    }

    public void queryEvents() {
        listItemBeans.clear();
        List<Events> events = new ArrayList<>();
        Cursor cursor = contentResolver.query(eventsUri, null, null, null, null);
        Events event;
        if (cursor != null) {
            while (cursor.moveToNext()) {
                event = new Events();
                event.set_id(cursor.getString(cursor.getColumnIndex("_id")));
                event.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                event.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                event.setAccount_name(cursor.getString(cursor.getColumnIndex("account_name")));
                event.setCal_sync1(cursor.getString(cursor.getColumnIndex("cal_sync1")));
                event.setDeleted(cursor.getString(cursor.getColumnIndex("deleted")));
                event.setDtend(DateUtil.toDateStr(cursor.getString(cursor.getColumnIndex("dtend"))));
                event.setDtstart(DateUtil.toDateStr(cursor.getString(cursor.getColumnIndex("dtstart"))));
                event.setDuration(cursor.getString(cursor.getColumnIndex("duration")));
                event.setEvent_calendar_type(cursor.getString(cursor.getColumnIndex("event_calendar_type")));
                event.setEventColor(cursor.getString(cursor.getColumnIndex("eventColor")));
                event.setHasAlarm(cursor.getString(cursor.getColumnIndex("hasAlarm")));
                event.setLastDate(cursor.getString(cursor.getColumnIndex("lastdate")));
                event.setMaxReminders(cursor.getString(cursor.getColumnIndex("maxReminders")));
                event.setRrule(cursor.getString(cursor.getColumnIndex("rrule")));
                event.setVersion(cursor.getString(cursor.getColumnIndex("version")));
                events.add(event);
            }
            cursor.close();
        }
        for (Events e : events) {
            listItemBeans.add(new ListItemBean(e.get_id(), new Gson().toJson(e), e.getDeleted().equals("1")));
            //log("picher:" + new Gson().toJson(e));
        }
        myAdapter.notifyDataSetChanged();
    }

    public void insertEvents() {
        String accountName = "Phone";
        Calendar calendar = Calendar.getInstance();
        calendar.set(2019, 5, 28, 9, 47, 0);
        long startMills = calendar.getTimeInMillis();
        calendar.set(2019, 5, 28, 9, 50, 0);
        long endMills = calendar.getTimeInMillis();
        Account account = queryCalendarByName(accountName);
        if (account != null) {
            Log.d("picher", "id:" + account.get_id() + "type:" + account.getAccount_type());

            Uri eventUri = eventsUri;
            ContentValues contentValues = new ContentValues();
            contentValues.put("title", "代码插入的提醒事件");
            contentValues.put("dtstart", startMills);
            contentValues.put("dtend", endMills);
            contentValues.put("calendar_id", account.get_id());
            contentValues.put("hasAlarm", true);
            contentValues.put("duration", true);

            eventUri = eventUri.buildUpon()
                    .appendQueryParameter(CALLER_IS_SYNCADAPTER, "true")
                    .appendQueryParameter(ACCOUNT_NAME, accountName)
                    .appendQueryParameter(ACCOUNT_TYPE, account.getAccount_type())
                    .build();

            Uri uri = contentResolver.insert(eventUri, contentValues);

            //添加提前10分钟提醒
            insertReminder(uri.getLastPathSegment(), 10);
        } else {
            toast("找不到用户");
        }
    }

    private void toast(String s) {
        Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
    }

    public void updateEvents() {

    }

    public void deleteEvents(String eventId) {
        contentResolver.delete(eventsUri, "_id = ?", new String[]{eventId});
    }

    /**
     * Instance Module
     */
    public void queryInstance() {
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(2019, 2, 23, 8, 0);
        long startMillis = beginTime.getTimeInMillis();
        Calendar endTime = Calendar.getInstance();
        endTime.set(2019, 10, 24, 8, 0);
        long endMillis = endTime.getTimeInMillis();
        Uri.Builder builder = CalendarContract.Instances.CONTENT_URI.buildUpon();
        ContentUris.appendId(builder, startMillis);
        ContentUris.appendId(builder, endMillis);
        List<Instance> instances = new ArrayList<>();
        Cursor cursor = contentResolver.query(builder.build(), null, null, null, null);
        Instance instance;
        if (cursor != null) {
            while (cursor.moveToNext()) {
                instance = new Instance();
                instance.set_id(cursor.getString(cursor.getColumnIndex("_id")));
                instance.setEvent_id(cursor.getString(cursor.getColumnIndex("event_id")));
                instance.setBegin(DateUtil.toDateStr(cursor.getString(cursor.getColumnIndex("begin"))));
                instance.setEnd(DateUtil.toDateStr(cursor.getString(cursor.getColumnIndex("end"))));
                instance.setEndDay(DateUtil.toDateStr(cursor.getString(cursor.getColumnIndex("endDay"))));
                instance.setStartDay(DateUtil.toDateStr(cursor.getString(cursor.getColumnIndex("startDay"))));
                instances.add(instance);
            }
            cursor.close();
        }
        listItemBeans.clear();
        for (Instance i : instances) {
            listItemBeans.add(new ListItemBean(i.get_id(), new Gson().toJson(i), false));
        }
        myAdapter.notifyDataSetChanged();
    }

    /**
     * Reminder Module
     */
    public void queryReminder() {
        List<Reminder> reminders = new ArrayList<>();
        Cursor cursor = contentResolver.query(remindersUri, null, null, null, null);
        Reminder reminder;
        if (cursor != null) {
            while (cursor.moveToNext()) {
                reminder = new Reminder();
                reminder.set_id(cursor.getString(cursor.getColumnIndex("_id")));
                reminder.setEvent_id(cursor.getString(cursor.getColumnIndex("event_id")));
                reminder.setMethod(cursor.getString(cursor.getColumnIndex("method")));
                reminder.setMinutes(cursor.getString(cursor.getColumnIndex("minutes")));
                reminders.add(reminder);
            }
            cursor.close();
        }
        listItemBeans.clear();
        for (Reminder r : reminders) {
            listItemBeans.add(new ListItemBean(r.get_id(), new Gson().toJson(r), false));
        }
        myAdapter.notifyDataSetChanged();
    }

    /**
     * Calendar Module
     */
    private void deleteCalendar(String calendarId) {
        log("UpdateId:" + calendarId);
        ContentValues contentValues = new ContentValues();
        contentValues.put(CalendarContract.Calendars.DELETED, 1);
        Uri uri = calendarsUri;
        uri = uri.buildUpon().
                appendQueryParameter(CALLER_IS_SYNCADAPTER, "true").
                appendQueryParameter(ACCOUNT_NAME, "picher").
                appendQueryParameter(ACCOUNT_TYPE, "local").
                build();
        int i = contentResolver.update(uri, contentValues, "_id = ?", new String[]{calendarId});
        //int i = contentResolver.delete(uri, "_id = ?", new String[]{calendarId});
        if (i > 0) {
            log("Delete Succeed!");
        }
    }

    public void queryCalendar() {
        List<Account> accounts = new ArrayList<>();
        Cursor cursor = contentResolver.query(calendarsUri, null, null, null, null);
        while (cursor != null && cursor.moveToNext()) {
            accounts.add(createAccount(cursor));
        }
        if (cursor != null) {
            cursor.close();
        }
        listItemBeans.clear();
        for (Account i : accounts) {
            //if (!i.isDeleted()) {
            log("id:" + i.get_id() + "-->isDeleted:" + i.isDeleted());
            listItemBeans.add(new ListItemBean(i.get_id(), i.toString(), i.isDeleted()));
            //}
        }
        myAdapter.notifyDataSetChanged();
    }

    public Account queryCalendarByName(String name) {
        List<Account> accounts = new ArrayList<>();
        Account account = null;
        Cursor cursor = contentResolver.query(calendarsUri, null, "account_name = ?", new String[]{name}, null);
        while (cursor != null && cursor.moveToNext()) {
            accounts.add(createAccount(cursor));
        }
        if (cursor != null) {
            cursor.close();
        }
        if (accounts.size() > 0) {
            account = accounts.get(0);
        }
        return account;
    }

    public void insertCalendar() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ACCOUNT_NAME, "picher");
        contentValues.put(ACCOUNT_TYPE, "local");
        Uri uri = calendarsUri;
        uri = uri.buildUpon().
                appendQueryParameter(CALLER_IS_SYNCADAPTER, "true").
                appendQueryParameter(ACCOUNT_NAME, "picher").
                appendQueryParameter(ACCOUNT_TYPE, "local").
                build();
        Uri u = contentResolver.insert(uri, contentValues);
        log("CalendarPath：" + (u != null ? u.getPath() : null));
    }

    private void log(String s) {
        Log.d("picher", s);
    }

    private Account createAccount(Cursor cursor) {
        Account account = new Account();
        account.set_id(cursor.getString(cursor.getColumnIndex("_id")));
        account.setAccount_name(cursor.getString(cursor.getColumnIndex("account_name")));
        account.setAccount_type(cursor.getString(cursor.getColumnIndex("account_type")));
        account.setMaxReminders(cursor.getString(cursor.getColumnIndex("maxReminders")));
        account.setDeleted(cursor.getInt(cursor.getColumnIndex("deleted")));
        //account.setCalendar_display_name(cursor.getString(cursor.getColumnIndex("calendar_display_name")));
        return account;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.m_demo_bt:
                Intent intent = new Intent(MainActivity.this, JumpActivity.class);
                intent.putExtra("TAG", "dynamic send intent");
                startActivity(intent);
                break;
            //throw new IllegalArgumentException("test exception");
            case R.id.m_query_calendar_bt:
                switch (mSelectedPos) {
                    case 0:
                        queryCalendar();
                        break;
                    case 1:
                        queryEvents();
                        break;
                    case 2:
                        queryReminder();
                        break;
                    case 3:
                        queryInstance();
                        break;
                }
                break;
            case R.id.m_insert_calendar_bt:
                switch (mSelectedPos) {
                    case 0:
                        insertCalendar();
                        break;
                    case 1:
                        insertEvents();
                        break;
                    case 2:

                        break;
                    case 3:
                        break;
                }
        }
    }

    private void insertReminder(String eventId, int minutes) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("event_id", eventId);
        contentValues.put("minutes", minutes);
        contentValues.put("method", CalendarContract.Reminders.METHOD_ALERT);
        contentResolver.insert(remindersUri, contentValues);
    }

    private void delete(String id) {
        switch (mSelectedPos) {
            case 0:
                deleteCalendar(id);
                break;
            case 1:
                deleteEvents(id);
                break;
            case 2:
                break;
            case 3:
                break;
        }
    }

    public class MyAdapter extends BaseAdapter {

        private final List<ListItemBean> listItemBeans;

        public MyAdapter(List<ListItemBean> listItemBeans) {
            this.listItemBeans = listItemBeans;
        }

        @Override
        public int getCount() {
            return listItemBeans.size();
        }

        @Override
        public Object getItem(int i) {
            return listItemBeans.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int i, View convertView, ViewGroup viewGroup) {
            ViewHolder vh;
            if (convertView == null) {
                convertView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_layout, null);
                vh = new ViewHolder(convertView);
                convertView.setTag(vh);
            } else {
                vh = (ViewHolder) convertView.getTag();
            }
            final ListItemBean item = listItemBeans.get(i);
            vh.mListItemTv.setText(item.getName());
            vh.mIdTv.setText("" + i);
            if (!item.isDelete()) {
                vh.mListItemBt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        delete(item.getId());
                    }
                });
                vh.mListItemBt.setVisibility(View.VISIBLE);
            } else {
                vh.mListItemBt.setVisibility(View.GONE);
            }
            return convertView;
        }

        class ViewHolder {
            TextView mIdTv;
            TextView mListItemTv;
            TextView mListItemBt;

            ViewHolder(View convertView) {
                mListItemTv = (TextView) convertView.findViewById(R.id.m_list_item_tv);
                mListItemBt = (TextView) convertView.findViewById(R.id.m_list_item_bt);
                mIdTv = (TextView) convertView.findViewById(R.id.m_id_tv);
            }
        }
    }

}
