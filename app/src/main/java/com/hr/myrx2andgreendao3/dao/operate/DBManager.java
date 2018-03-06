package com.hr.myrx2andgreendao3.dao.operate;

import android.content.Context;

import com.hr.myrx2andgreendao3.App;
import com.hr.myrx2andgreendao3.dao.DaoMaster;
import com.hr.myrx2andgreendao3.dao.DaoSession;

/**
 * Created by 吕 on 2017/11/9.
 */

public class DBManager {
    private final static String TAG = "DBManager";
    private final static String DB_NAME = "UserData";
    private static DBManager instance = null;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private static Context mContext;

    public static DBManager getInstance(){
        if(instance==null){
            synchronized (DBManager.class) {
                if (instance == null) {
                    instance = new DBManager();
                }
            }
        }
        return instance;
    }
    public static void setInstance(Object o){
        instance = (DBManager) o;
    }

    private DBManager() {
        DaoMaster.OpenHelper helper = new
                DaoMaster.DevOpenHelper(App.context, DB_NAME, null);
        daoMaster = new DaoMaster(helper.getWritableDatabase());
        daoSession = daoMaster.newSession();
        //数据库存贮路径修改,直接删除旧的数据库
        boolean isDelete = App.context.deleteDatabase(App.context.getPackageName());
    }
    public DaoMaster getDaoMaster() {
        return daoMaster;
    }

    public void setDaoMaster(DaoMaster daoMaster) {
        this.daoMaster = daoMaster;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public void setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
    }
    public void uninit() {
        if (daoSession != null && daoSession.getDatabase() != null) {
            daoSession.getDatabase().close();
        }
        daoSession = null;
        daoMaster = null;
    }
}
