package com.sgm.iorecord.useCase.main;

import com.sgm.iorecord.databases.DbController;
import com.sgm.iorecord.model.IOTopBean;
import com.sgm.iorecord.useCase.UseCase;

import java.util.List;

/**
 * Created by s2s8tb on 2019/10/14.
 */

public class QueryTask extends UseCase<QueryTask.RequestValues, QueryTask.ResponseValue> {
    @Override
    protected void executeUseCase(RequestValues requestValues) {
        List<IOTopBean> ioTopBeanList = DbController.getInstance().getSession().getIOTopBeanDao().loadAll();
        getUseCaseCallback().onSuccess(new ResponseValue(ioTopBeanList));
    }

    public static final class RequestValues implements UseCase.RequestValues {

    }

    public static final class ResponseValue implements UseCase.ResponseValue {

        private final List<IOTopBean> ioTopBeans;

        public ResponseValue(List<IOTopBean> ioTopBeanList) {
            ioTopBeans = ioTopBeanList;
        }

        public List<IOTopBean> getIoTopBeans(){
            return ioTopBeans;
        }
    }
}
