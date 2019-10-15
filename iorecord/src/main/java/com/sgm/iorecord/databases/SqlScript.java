package com.sgm.iorecord.databases;

import com.sgm.iorecord.model.IOTopBeanDao;

/**
 * Created by s2s8tb on 2019/10/15.
 * sql命令集中管理
 */

public interface SqlScript {
    //按照包名分组，查询IOTOP最新的一条数据
    String SQL_LATEST_BY_PACKAGE = "SELECT * FROM (SELECT * FROM " + IOTopBeanDao.TABLENAME + " ORDER BY "
            + IOTopBeanDao.Properties.Date.columnName + ") AS TOP_TMP GROUP BY TOP_TMP." + IOTopBeanDao.Properties.PROCESS.columnName;

}
