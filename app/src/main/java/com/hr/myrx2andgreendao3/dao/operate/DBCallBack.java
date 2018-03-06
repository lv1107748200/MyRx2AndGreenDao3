package com.hr.myrx2andgreendao3.dao.operate;

/**
 * Created by 吕 on 2017/11/10.
 */

public interface DBCallBack<T> {

    void onDBError(Throwable e);

    void onDBSuccess(T t);
}
