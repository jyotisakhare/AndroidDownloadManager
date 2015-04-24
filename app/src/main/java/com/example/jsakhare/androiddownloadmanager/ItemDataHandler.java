package com.example.jsakhare.androiddownloadmanager;

/**
 * Created by jsakhare on 11/04/15.
 */
public interface ItemDataHandler<T> {
    public void setItem(T item);
    public void onError(BaseResponse result);
    public  void onProgressUpdate(T value);
}
