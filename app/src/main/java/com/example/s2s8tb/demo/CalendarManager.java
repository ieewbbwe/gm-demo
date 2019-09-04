package com.example.s2s8tb.demo;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Context;

import java.util.List;

/**
 * Created by s2s8tb on 2019/6/25.
 */

public class CalendarManager implements CalendarInterface {

    private static CalendarManager mInstance;
    private ContentResolver contentResolver;
    private CalendarManager(){}

    private static CalendarManager getInstance(){
        synchronized (CalendarManager.class){
            if(mInstance == null){
                synchronized (CalendarManager.class){
                    mInstance = new CalendarManager();
                }
            }
        }
        return mInstance;
    }

    private void init(Context context){
        contentResolver = context.getContentResolver();
    }

    @Override
    public void insert(Events e) {

    }

    @Override
    public void insert(List<Events> elist) {

    }

    @Override
    public void delete(String eventId) {

    }

    @Override
    public void update(Events e) {

    }

    @Override
    public void update(String eventId) {

    }

    @Override
    public void query(String eventId) {

    }
}
