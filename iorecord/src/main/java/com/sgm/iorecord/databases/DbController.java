package com.sgm.iorecord.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.sgm.iorecord.bean.DaoMaster;
import com.sgm.iorecord.bean.DaoSession;
import com.sgm.iorecord.bean.IOBeanDao;

/**
 * Created by s2s8tb on 2019/9/26.
 */

public class DbController implements IDdController {

    private static final String DB_NAME = "iorecord.db";
    private IOBeanDao mIoDao;
    private DaoSession mDaoSession;
    private DaoMaster mDaoMaster;
    private DaoMaster.DevOpenHelper mHelper;
    private Context context;
    private SQLiteDatabase writableDatabase;
    private static DbController mInstance;

    public static void init(Context context) {
        if (mInstance == null) {
            synchronized (DbController.class) {
                if (mInstance == null) {
                    mInstance = new DbController(context);
                }
            }
        }
    }

    public static DbController getInstance() {
        if (mInstance == null) {
            throw new IllegalStateException("DbController should be init first!!");
        }
        return mInstance;
    }

    private DbController() {
    }

    private DbController(Context context) {
        this.context = context;
        mHelper = new DaoMaster.DevOpenHelper(context, DB_NAME, null);
        mDaoMaster = new DaoMaster(getWritableDatabase());
        mDaoSession = mDaoMaster.newSession();
        mIoDao = mDaoSession.getIOBeanDao();

    }

    public SQLiteDatabase getWritableDatabase() {
        if (mHelper == null) {
            mHelper = new DaoMaster.DevOpenHelper(context, DB_NAME, null);
        }
        return mHelper.getWritableDatabase();
    }

    public SQLiteDatabase getReadableDatabase() {
        if (mHelper == null) {
            mHelper = new DaoMaster.DevOpenHelper(context, DB_NAME, null);
        }
        return mHelper.getReadableDatabase();
    }

    @Override
    public DaoSession getSession() {
        return mDaoSession;
    }

}
