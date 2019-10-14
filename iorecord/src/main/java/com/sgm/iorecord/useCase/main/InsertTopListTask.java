package com.sgm.iorecord.useCase.main;

import android.support.annotation.NonNull;

import com.sgm.iorecord.databases.DbController;
import com.sgm.iorecord.model.IOTopBean;
import com.sgm.iorecord.useCase.UseCase;

import java.util.List;

/**
 * Created by s2s8tb on 2019/10/14.
 */

public class InsertTopListTask extends UseCase<InsertTopListTask.RequestValues, InsertTopListTask.ResponseValue> {

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        DbController.getInstance().getSession().getIOTopBeanDao().insertInTx(requestValues.getIoTopBeans());
        getUseCaseCallback().onSuccess(new ResponseValue());
    }

    public static final class RequestValues implements UseCase.RequestValues {

        private List<IOTopBean> ioTopBeans;

        public RequestValues(@NonNull List<IOTopBean> ioTopBeans) {
            this.ioTopBeans = ioTopBeans;
        }

        public List<IOTopBean> getIoTopBeans() {
            return ioTopBeans;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {

    }
}
