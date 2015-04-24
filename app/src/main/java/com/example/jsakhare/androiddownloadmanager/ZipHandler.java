package com.example.jsakhare.androiddownloadmanager;

import android.os.Handler;
import android.os.Looper;

import java.lang.ref.WeakReference;

/**
 * Created by jsakhare on 11/04/15.
 */
public class ZipHandler implements  ResponseHandler{
    private WeakReference<ItemDataHandler<String>> mContext;

    public ZipHandler(ItemDataHandler<String> mContext) {
        this.mContext = new WeakReference<ItemDataHandler<String>>(mContext);
    }

    @Override
    public void onComplete(BaseResponse result) {
        //Log.d("onComplete ",result.getResponse());
        String item = result.getResponse();
        new Handler(Looper.getMainLooper()).post(new ItemDataRunnable<String>(mContext, item,result));
    }

    @Override
    public void onError(BaseResponse result) {
        //Log.d("onComplete ",result.getResponse());
        new Handler(Looper.getMainLooper()).post(new ItemDataRunnable<String>(mContext, result));
    }

    @Override
    public void onProgress(int value) {
        String strValue = String.valueOf(value);
        new Handler(Looper.getMainLooper()).post(new ProgressDataRunnable<String>(mContext, strValue));
    }
}
