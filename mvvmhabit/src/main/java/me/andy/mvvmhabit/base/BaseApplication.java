package me.andy.mvvmhabit.base;

import android.app.Application;

import org.litepal.LitePalApplication;


/**
 * Created by andy on 2017/6/15.
 */

public class BaseApplication extends LitePalApplication {
    private static Application sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    /**
     * 获得当前app运行的Application
     */
    public static Application getInstance() {
        if (sInstance == null) {
            throw new NullPointerException("please inherit BaseApplication or call setApplication.");
        }
        return sInstance;
    }


}
