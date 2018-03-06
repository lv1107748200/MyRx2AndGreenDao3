package com.hr.myrx2andgreendao3;

import android.app.Application;
import android.content.Context;

/**
 * Created by 吕 on 2017/11/9.
 */

public class App extends Application {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }
}
