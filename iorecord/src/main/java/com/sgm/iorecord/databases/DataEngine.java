package com.sgm.iorecord.databases;

import com.sgm.iorecord.utils.CommandExecution;
import com.sgm.iorecord.model.IOBean;
import com.sgm.iorecord.model.IOTopBean;

import java.util.Date;


/**
 * Created by s2s8tb on 2019/9/26.
 */

public class DataEngine {

    public CommandExecution.CommandResult createCommandResult() {
        CommandExecution.CommandResult result = new CommandExecution.CommandResult();
        result.result = 1;
        result.successMsg = "1192,32,22,34,64,com.sgm.iorecord;1193,32,22,34,64,com.sgm.rwutils";
        return result;
    }

    private static class DataEngineHolder {
        static final DataEngine instance = new DataEngine();
    }

    public static DataEngine gerInstance() {
        return DataEngineHolder.instance;
    }

    /**
     * for test
     *
     * @return IOBean
     */
    public IOBean createIOBean() {
        IOBean ioBean = new IOBean();
        ioBean.setWchar("1231231");
        ioBean.setRchar("54545");
        ioBean.setWrite_bytes("455354545");
        ioBean.setDate(new Date());
        return ioBean;
    }

    public IOTopBean createIOTopBean() {
        IOTopBean ioBean = new IOTopBean();
        ioBean.setPID("1231231");
        ioBean.setREAD("54545");
        ioBean.setWRITE_SPEED("455354545");
        ioBean.setDate(new Date());
        return ioBean;
    }
}
