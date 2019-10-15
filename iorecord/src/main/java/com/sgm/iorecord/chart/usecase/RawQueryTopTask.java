package com.sgm.iorecord.chart.usecase;

import android.database.Cursor;

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
 */

public class RawQueryTopTask extends UseCase<RawQueryTopTask.RequestValues, RawQueryTopTask.ResponseValue> {

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        Cursor c = DbController.getInstance().getReadableDatabase().rawQuery(requestValues.sql, null);
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
            getUseCaseCallback().onSuccess(new ResponseValue(ioTopBeans));
        } finally {
            c.close();
        }
    }

    public static final class RequestValues implements UseCase.RequestValues {

        private String sql;

        public RequestValues(String sql) {
            this.sql = sql;
        }

        public String getSql() {
            return sql;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {

        private final List<IOTopBean> ioTopBeans;

        public ResponseValue(List<IOTopBean> ioTopBeans) {
            this.ioTopBeans = ioTopBeans;
        }

        public List<IOTopBean> getIoTopBeans() {
            return ioTopBeans;
        }
    }
}
