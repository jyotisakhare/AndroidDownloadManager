package com.example.jsakhare.androiddownloadmanager;

import java.lang.ref.WeakReference;

/**
 * Created by jsakhare on 11/04/15.
 */
public class ItemDataRunnable<T> implements Runnable {

    private WeakReference<ItemDataHandler<T>> handlerObj;
    private T itemObj;
    private BaseResponse result;

    public ItemDataRunnable(WeakReference<ItemDataHandler<T>> c, T item, Object userdata) {
        handlerObj = c;
        itemObj = item;

    }

    public ItemDataRunnable(WeakReference<ItemDataHandler<T>> c, BaseResponse result) {
        handlerObj = c;
        this.result = result;
        //this.userData = result.getRequest().getUserData();
    }

    @Override
    public void run() {
        ItemDataHandler<T> actualObj = handlerObj.get();
        if (actualObj != null) {
            if (itemObj != null) {
                actualObj.setItem( itemObj);
            }
            else {
                actualObj.onError(result);
            }
        }
    }
}
