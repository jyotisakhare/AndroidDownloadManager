package com.example.jsakhare.androiddownloadmanager;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by jsakhare on 10/04/15.
 */
public class MainApplication extends Application {
    private static MainApplication instance;
    private static Context appContext;
    private static long maxMemorySize;
    private static int maxCacheSize;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        this.setAppContext(getApplicationContext());
        maxMemorySize = Runtime.getRuntime().maxMemory();
        maxCacheSize = (int)(maxMemorySize / 8);

        DiskCacheService.initialize(getApplicationContext());

    }

    public static MainApplication getInstance(){
        return instance;
    }
    public static Context getAppContext() {
        return appContext;
    }
    public void setAppContext(Context mAppContext) {
        this.appContext = mAppContext;
    }

    public static boolean isNetworkReachable() {
        ConnectivityManager cm = (ConnectivityManager) MainApplication.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork == null || !activeNetwork.isConnected()) {
            return false;
        }
        else {
            return true;
        }
    }
}
