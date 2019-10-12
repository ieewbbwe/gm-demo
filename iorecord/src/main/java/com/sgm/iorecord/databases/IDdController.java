package com.sgm.iorecord.databases;

import android.database.sqlite.SQLiteDatabase;

import com.sgm.iorecord.model.DaoSession;

/**
 * Created by s2s8tb on 2019/9/26.
 */

public interface IDdController {
    DaoSession getSession();

    SQLiteDatabase getWritableDatabase();

    SQLiteDatabase getReadableDatabase();

}
