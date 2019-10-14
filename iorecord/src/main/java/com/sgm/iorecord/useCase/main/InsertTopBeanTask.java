package com.sgm.iorecord.useCase.main;

import com.sgm.iorecord.databases.DbController;
import com.sgm.iorecord.model.IOTopBean;
import com.sgm.iorecord.useCase.UseCase;

/**
 * Created by s2s8tb on 2019/10/14.
 */

public class InsertTopBeanTask extends UseCase<InsertTopBeanTask.RequestValues, InsertTopBeanTask.ResponseValue> {

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        long id = DbController.getInstance().getSession().getIOTopBeanDao().insert(requestValues.getIoTopBean());
        getUseCaseCallback().onSuccess(new ResponseValue(id));
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private IOTopBean ioTopBean;

        public RequestValues(IOTopBean ioTopBean) {
            this.ioTopBean = ioTopBean;
        }

        public IOTopBean getIoTopBean() {
            return ioTopBean;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {
        private long id;

        public ResponseValue(long id) {
            this.id = id;
        }

        public long getId() {
            return id;
        }
    }
}
