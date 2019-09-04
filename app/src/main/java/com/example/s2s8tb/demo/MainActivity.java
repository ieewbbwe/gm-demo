package com.example.s2s8tb.demo;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ContentResolver contentResolver;
    private static Uri calendarsUri = CalendarContract.Calendars.CONTENT_URI;
    private static Uri eventsUri = CalendarContract.Events.CONTENT_URI;
    private static Uri remindersUri = CalendarContract.Reminders.CONTENT_URI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        contentResolver = getContentResolver();
//        findViewById(R.id.m_demo_bt).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(MainActivity.this,"click ",Toast.LENGTH_SHORT).show();
//                query();
//            }
//        });
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

    public void insertEvents() {


        //contentResolver.insert()

    }

    public void updateEvents() {

    }

    public void deleteEvents() {

    }

    public void query() {
        List<Account> accounts = new ArrayList<>();
        Cursor cursor = contentResolver.query(calendarsUri, null, null, null, null);
        while (cursor.moveToNext()){
            accounts.add(createAccount(cursor));
        }
        Log.d("picher",new Gson().toJson(accounts));
        cursor.close();
    }

    private Account createAccount(Cursor cursor) {
        Account account = new Account();
        account.set_id(cursor.getString(cursor.getColumnIndex("_id")));
        account.setAccount_name(cursor.getString(cursor.getColumnIndex("account_name")));
        account.setAccount_type(cursor.getString(cursor.getColumnIndex("account_type")));
        account.setMaxReminders(cursor.getString(cursor.getColumnIndex("maxReminders")));
        return account;
    }
}
