package com.example.jsakhare.androiddownloadmanager;

/**
 * Created by jsakhare on 12/04/15.
 */

import java.lang.ref.WeakReference;


/**
 * Created by jsakhare on 11/04/15.
 */
public class ProgressDataRunnable<T> implements Runnable {

    private WeakReference<ItemDataHandler<T>> handlerObj;
    private T itemObj;
    private BaseResponse result;

    public ProgressDataRunnable(WeakReference<ItemDataHandler<T>> c, T item, Object userdata) {
        handlerObj = c;
        itemObj = item;

    }

    public ProgressDataRunnable(WeakReference<ItemDataHandler<T>> c, T result) {
        handlerObj = c;
        itemObj = result;
        //this.userData = result.getRequest().getUserData();
    }

    @Override
    public void run() {
        ItemDataHandler<T> actualObj = handlerObj.get();
        if (actualObj != null) {
            if (itemObj != null) {
                actualObj.onProgressUpdate(itemObj);
            }

        }
    }
}
