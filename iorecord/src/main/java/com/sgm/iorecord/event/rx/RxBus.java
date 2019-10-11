package com.sgm.iorecord.event.rx;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Created by picher on 2018/3/8.
 * Describe：Dispatches events to listeners, and provides ways for listeners to register themselves.
 */

public class RxBus {

    private static RxBus mRxBus;
    private final Subject<Object> mSubject;
    private Map<String, CompositeDisposable> mDisposableMap;

    public static RxBus get() {
        return InstanceHolder.INSTANCE;
    }

    private static class InstanceHolder {
        private static final RxBus INSTANCE = new RxBus();
    }

    private RxBus() {
        mSubject = PublishSubject.create().toSerialized();
    }


    /**
     * 發送一個事件
     */
    public void post(Object o) {
        mSubject.onNext(o);
    }

    /**
     * 默認的注冊方法
     */
    public <T> Disposable doSubscribe(Class<T> type, Consumer<T> next, Consumer<Throwable> error) {
        Disposable disposable = getObservable(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(next, error);
        addSubscription(type.getSimpleName(), disposable);
        return disposable;
    }

    /**
     * 獲取一個支持背壓的Flowable
     */
    private <T> Flowable<T> getObservable(Class<T> type) {
        return mSubject.toFlowable(BackpressureStrategy.BUFFER)
                .ofType(type);
    }

    /**
     * 保存訂閲
     */
    public void addSubscription(String key, Disposable disposable) {
        if (mDisposableMap == null) {
            mDisposableMap = new HashMap<>();
        }
        if (mDisposableMap.get(key) != null) {
            mDisposableMap.get(key).add(disposable);
        } else {
            CompositeDisposable compositeDisposable = new CompositeDisposable();
            compositeDisposable.add(disposable);
            mDisposableMap.put(key, compositeDisposable);
        }
    }

    /**
     * 取消訂閲
     */
    public void unSubscription(Class c) {
        String key = c.getSimpleName();
        if (mDisposableMap != null) {
            CompositeDisposable compositeDisposable = mDisposableMap.get(key);
            if (compositeDisposable != null) {
                compositeDisposable.dispose();
            }
            mDisposableMap.remove(key);
        }
    }

}
