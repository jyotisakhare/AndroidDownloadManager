package com.example.jsakhare.androiddownloadmanager;

/**
 * Created by jsakhare on 11/04/15.
 */
public interface ResponseHandler  {

    public void onComplete(BaseResponse result);
    public void onError(BaseResponse result);
    public  void onProgress(int value);
}
