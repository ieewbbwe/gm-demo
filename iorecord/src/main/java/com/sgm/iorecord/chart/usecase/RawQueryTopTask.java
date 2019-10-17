package com.sgm.iorecord.chart.usecase;

import android.database.Cursor;
import android.util.Log;

import com.github.mikephil.charting.data.PieEntry;
import com.sgm.iorecord.databases.DbController;
import com.sgm.iorecord.model.IOTopBean;
import com.sgm.iorecord.model.IOTopBeanDao;
import com.sgm.iorecord.useCase.UseCase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by s2s8tb on 2019/10/15.
 * 执行sql语句查询
 * 进程被杀后，proc中的数据会被清掉，此情况下的数据求和逻辑
 * 判断被杀：
 * 1.IProcessObserver，按照进程被杀时间点，查找被杀前最后一笔数据 （最精确方式）
 * 2.同样的包名，不一样的唯一标识--按照进程包名分组，将唯一标识不同的数据求和
 * 把包名和PID联合作为唯一标识，找相同包名不同PID下的最后一条数据，在求和 （有漏洞，万一PID被复用给同一个包名就可能漏掉一些数据
 */

public class RawQueryTopTask extends UseCase<RawQueryTopTask.RequestValues, RawQueryTopTask.ResponseValue> {

    private static final String TAG = "RawQueryTopTask";

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        //按照包名和PID分组，查询IOTOP最新的一条数据
        String sql = "SELECT * FROM (SELECT * FROM " + IOTopBeanDao.TABLENAME
                + " WHERE " + IOTopBeanDao.Properties.Date.columnName + " BETWEEN " + requestValues.startTime.getTime() + " AND " + requestValues.endTime.getTime()
                + " ORDER BY " + IOTopBeanDao.Properties.Date.columnName + ") AS TOP_TMP GROUP BY TOP_TMP." + IOTopBeanDao.Properties.PROCESS.columnName + ",TOP_TMP." + IOTopBeanDao.Properties.PID.columnName;
        Log.d(TAG, sql);
        Cursor c = DbController.getInstance().getReadableDatabase().rawQuery(sql, null);
        List<IOTopBean> ioTopBeans = new ArrayList<>();
        try {
            IOTopBean ioTopBean;
            if (c.moveToFirst()) {
                do {
                    int idIndex = c.getColumnIndex(IOTopBeanDao.Properties.Id.columnName);
                    int pidIndex = c.getColumnIndex(IOTopBeanDao.Properties.PID.columnName);
                    int readIndex = c.getColumnIndex(IOTopBeanDao.Properties.READ.columnName);
                    int writeIndex = c.getColumnIndex(IOTopBeanDao.Properties.WRITTEN.columnName);
                    int readSpeedIndex = c.getColumnIndex(IOTopBeanDao.Properties.READ_SPEED.columnName);
                    int writeSpeedIndex = c.getColumnIndex(IOTopBeanDao.Properties.WRITE_SPEED.columnName);
                    int processIndex = c.getColumnIndex(IOTopBeanDao.Properties.PROCESS.columnName);
                    int dateIndex = c.getColumnIndex(IOTopBeanDao.Properties.Date.columnName);
                    ioTopBean = new IOTopBean(c.getLong(idIndex), c.getString(pidIndex), c.getString(readIndex), c.getString(writeIndex)
                            , c.getString(readSpeedIndex), c.getString(writeSpeedIndex), c.getString(processIndex), new Date(c.getLong(dateIndex)));
                    ioTopBeans.add(ioTopBean);
                } while (c.moveToNext());
            }
            ArrayList<PieEntry> entries = new ArrayList<>();
            for (IOTopBean top : ioTopBeans) {
                entries.add(new PieEntry(Float.parseFloat(top.getWRITTEN()), top.getPROCESS()));
            }

            getUseCaseCallback().onSuccess(new ResponseValue(entries));
        } finally {
            c.close();
        }
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private Date startTime;
        private Date endTime;

        public RequestValues(Date startTime, Date endTime) {
            this.startTime = startTime;
            this.endTime = endTime;
        }

        public Date getStartTime() {
            return startTime;
        }

        public Date getEndTime() {
            return endTime;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {

        private final ArrayList<PieEntry> pieEntries;

        public ResponseValue(ArrayList<PieEntry> ioTopBeans) {
            this.pieEntries = ioTopBeans;
        }

        public ArrayList<PieEntry> getPieEntries() {
            return pieEntries;
        }
    }
}
