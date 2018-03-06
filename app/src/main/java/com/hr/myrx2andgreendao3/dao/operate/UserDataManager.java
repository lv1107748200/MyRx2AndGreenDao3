package com.hr.myrx2andgreendao3.dao.operate;


import com.hr.myrx2andgreendao3.dao.DogDao;
import com.hr.myrx2andgreendao3.dao.UserDao;
import com.hr.myrx2andgreendao3.entiy.Dog;
import com.hr.myrx2andgreendao3.entiy.User;

import org.greenrobot.greendao.AbstractDao;
import org.reactivestreams.Subscriber;

import java.util.List;


import javax.xml.validation.Schema;
import javax.xml.validation.Validator;
import javax.xml.validation.ValidatorHandler;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 吕 on 2017/11/9.
 */

public class UserDataManager {

    private static UserDataManager instance = null;
    public static UserDataManager getInstance(){
        if(instance==null){
            synchronized (UserDataManager.class) {
                if (instance == null) {
                    instance = new UserDataManager();
                }
            }
        }
        return instance;
    }

    private UserDao userDao;
    private DogDao dogDao;


    public void insertOrReplaceInTx(List<Dog> list,DBCallBack<List<Dog>> dbCallBack){
        if(null == dogDao){
            dogDao = DBManager.getInstance().getDaoSession().getDogDao();
        }
        toSubscribe(getInsertOrReplaceList(dogDao,list),new DBSubscribe<List<Dog>>(dbCallBack));
    }

    public void insertOrReplace(User user,DBCallBack<User> dbCallBack){
        if(null == userDao){
            userDao = DBManager.getInstance().getDaoSession().getUserDao();
        }
        toSubscribe(getInsertOrReplace(user,userDao),new DBSubscribe<User>(dbCallBack));
    }

    public void LoadAll(DBCallBack<List<User>> dbCallBack){
        if(null == userDao){
            userDao = DBManager.getInstance().getDaoSession().getUserDao();
        }
        toSubscribe(getLoadAll(List.class,userDao),new DBSubscribe<List<User>>(dbCallBack));
    }

    private  Observable<List> getInsertOrReplaceList(final AbstractDao abstractDao, final List entiy){

        Observable<java.util.List> observable =  Observable.create(new ObservableOnSubscribe<java.util.List>() {
            @Override
            public void subscribe(ObservableEmitter<java.util.List> e) throws Exception {
                abstractDao.insertOrReplaceInTx(entiy);
                e.onNext( entiy);
            }
        });

        return observable;
    }


    private <T> Observable<T> getInsertOrReplace(final T entiy, final AbstractDao abstractDao){

        Observable<T> observable =  Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> e) throws Exception {
                abstractDao.insertOrReplace(entiy);
                e.onNext(entiy);
            }
        });

        return observable;
    }

    private <T> Observable<T> getLoadAll(Class<T> tClass,final AbstractDao abstractDao){

        Observable<T> observable =  Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> e) throws Exception {
                e.onNext((T) abstractDao.loadAll());
            }
        });

        return observable;
    }



    //添加线程管理并订阅
    @SuppressWarnings("unchecked")
    public void toSubscribe(Observable o, Observer s){
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }

    public void close(){
        DBManager.getInstance().uninit();
        DBManager.setInstance(null);

    }

}
