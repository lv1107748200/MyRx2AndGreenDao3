package com.hr.myrx2andgreendao3.dao.operate;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by 吕 on 2017/11/10.
 */

public class DBSubscribe<T> implements Observer<T> {

    private DBCallBack<T> dbCallBack;

    public DBSubscribe(DBCallBack<T> dbCallBack) {
        this.dbCallBack = dbCallBack;
    }


    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T t) {
        if(null != dbCallBack){
            dbCallBack.onDBSuccess(t);
        }
    }

    @Override
    public void onError(Throwable e) {
        if(null != dbCallBack){
            dbCallBack.onDBError(e);
        }
    }

    @Override
    public void onComplete() {

    }
}
