package com.example.jsakhare.androiddownloadmanager;

import android.content.Context;
import android.net.http.HttpResponseCache;
import android.os.StatFs;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * Created by jsakhare on 12/04/15.
 */
public class DiskCacheService {
    private static String cache = null;
    private static long maxSize = 1024 * 1024 * 10; //10MB
    private static long currentSize = 0;
    private static DataBaseService dbService = null;
    private static Context context = null;
    private static String logTag = "DISKCACHESERVICE";

    public static void initialize(Context context) {
        dbService = new DataBaseService(context);
        cache = context.getFilesDir().getAbsolutePath();
        Log.i(logTag, "Cache path is: " + cache);
        StatFs stats = new StatFs(cache);
        long freeSpace = (long) stats.getAvailableBlocks() * (long) stats.getBlockSize();
        maxSize = (maxSize > freeSpace ? freeSpace : maxSize);
        DiskCacheService.context = context;

        try {
            File httpCacheDir = new File(context.getCacheDir(), "image-cache");
            HttpResponseCache.install(httpCacheDir, maxSize);
        } catch (IOException e) {
            Log.e(logTag, "HttpResponseCache installation failed.");
        }
    }

    public static String getData(String key) {

            CacheEntity entry = dbService.getEntry(key);
            if(entry != null && !entry.isExpired()){
                dbService.updateAccessForEntry(key);
                return entry.getValue();
            }else{
                removeData(key);
            }
            return  null;


    }

    private static synchronized void cull(long size) {

                CacheEntity oldentry = dbService.getOldestEntry();
                removeData(oldentry.getKey());

    }

    public static synchronized Boolean checkData(String md5) {
            CacheEntity entry = dbService.getEntry(md5);
            if ( entry != null && !entry.isExpired()) {
                return true;
            } else {
                removeData(md5);
            }

        return false;
    }

    public static synchronized void saveData(String key, String response, Date expiryDate) {
        if((response == null) || (checkData(key))) return;
        if (currentSize + response.length() > maxSize) {
            cull(currentSize + response.length() - maxSize);
        }
        Log.d(logTag, "Expiry date for " + key + " => " + expiryDate.toString());

            currentSize += response.length();
            Date timestamp = new Date();
            CacheEntity entry = new CacheEntity(key, expiryDate, timestamp,response);
            dbService.addEntry(entry);

    }

    public static synchronized void removeData(String key) {

            dbService.deleteEntry(key);

    }



    public static synchronized void clear() {

        dbService.clearEntries();
    }


}
