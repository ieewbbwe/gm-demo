package com.sgm.iorecord.chart;

import com.sgm.iorecord.base.BasePresenter;
import com.sgm.iorecord.bean.IOTopBean;
import com.sgm.iorecord.databases.DbController;
import com.sgm.iorecord.event.RXLoadIoTopAllEvent;
import com.sgm.iorecord.event.RxEvent;
import com.sgm.iorecord.event.RxEventBus;
import com.sgm.iorecord.event.rx.RxBus;
import com.sgm.iorecord.listener.IOTopLoadListener;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ChartPresenter extends BasePresenter<ChartContract.View>
        implements ChartContract.Presenter {

    public ChartPresenter(ChartContract.View view) {
        super(view);
    }

    @Override
    public List<IOTopBean> queryIOTopAll() {
        return DbController.getInstance().getSession().getIOTopBeanDao().loadAll();
    }

    @Override
    public void queryIOTopAllAsync() {
        Observable.create(new ObservableOnSubscribe<List<IOTopBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<IOTopBean>> emitter) throws Exception {
                emitter.onNext(DbController.getInstance().getSession().getIOTopBeanDao().loadAll());
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<IOTopBean>>() {
                    @Override
                    public void accept(List<IOTopBean> ioTopBeans) throws Exception {
                        RxBus.get().post(new RXLoadIoTopAllEvent(ioTopBeans));
                        //RxEventBus.getInstance().send(RxEvent.create(RxEvent.RxEventId.RX_LOAD_IOTOP_SUCCEED, ioTopBeans));
                    }
                });
    }
}
