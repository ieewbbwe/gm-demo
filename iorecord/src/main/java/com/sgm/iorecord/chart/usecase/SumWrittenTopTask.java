package com.sgm.iorecord.chart.usecase;

import android.database.Cursor;

import com.github.mikephil.charting.data.PieEntry;
import com.sgm.iorecord.databases.DbController;
import com.sgm.iorecord.model.IOTopBeanDao;
import com.sgm.iorecord.useCase.UseCase;
import com.sgm.iorecord.utils.Lg;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by s2s8tb on 2019/10/15.
 * 把包名和PID联合作为唯一标识，找相同包名不同PID下的最后一条数据，在求和 （有漏洞，万一PID被复用给同一个包名就可能漏掉一些数据
 * SELECT PROCESS,SUM(WRITTEN)'SUMWRITTEN'
 * FROM (SELECT PROCESS,WRITTEN FROM (SELECT * FROM IOTOP_BEAN ORDER BY DATE DESC) AS A GROUP BY A.PROCESS,A.PID)
 * group by PROCESS
 * 1.按照PID与包名分组，查找应用在时间范围内的最后一条数据
 * 2.按照包名求和
 */

public class SumWrittenTopTask extends UseCase<SumWrittenTopTask.RequestValues, SumWrittenTopTask.ResponseValue> {

    private static final String TAG = "SumWrittenTopTask";

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        String tableName = IOTopBeanDao.TABLENAME;
        String process = IOTopBeanDao.Properties.PROCESS.columnName;
        String written = IOTopBeanDao.Properties.WRITTEN.columnName;
        String pid = IOTopBeanDao.Properties.PID.columnName;
        String date = IOTopBeanDao.Properties.Date.columnName;
        //按照包名和PID分组，查询IOTOP最新的一条数据
//        String sql = "SELECT " + process + ",SUM(" + written + ")'SUMWRITTEN' FROM (SELECT " + process + "," + written
//                + " FROM (SELECT * FROM " + tableName + " ORDER BY " + date + " DESC) AS A GROUP BY A." + process + ",A." + pid
//                + ") AS B GROUP BY B." + process;
        String sql = "SELECT " + process + ",SUM(" + written + ")'SUMWRITTEN' FROM (SELECT * FROM " + tableName
                + " WHERE " + IOTopBeanDao.Properties.Date.columnName + " BETWEEN " + requestValues.startTime.getTime() + " AND " + requestValues.endTime.getTime()
                + " GROUP BY " + process + "," + pid + ") GROUP BY " + process;
        Lg.d(TAG, sql);
        Cursor c = DbController.getInstance().getReadableDatabase().rawQuery(sql, null);
        List<PieEntry> entries = new ArrayList<>();
        try {
            PieEntry pieEntry;
            if (c.moveToFirst()) {
                do {
                    int processIndex = c.getColumnIndex(process);
                    int sumWrittenIndex = c.getColumnIndex("SUMWRITTEN");
                    pieEntry = new PieEntry(c.getFloat(sumWrittenIndex), c.getString(processIndex));
                    entries.add(pieEntry);
                } while (c.moveToNext());
            }
            getUseCaseCallback().onSuccess(new SumWrittenTopTask.ResponseValue(entries));
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

        private final List<PieEntry> pieEntries;

        public ResponseValue(List<PieEntry> ioTopBeans) {
            this.pieEntries = ioTopBeans;
        }

        public List<PieEntry> getPieEntries() {
            return pieEntries;
        }
    }
}
