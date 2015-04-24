package com.example.jsakhare.androiddownloadmanager;

/**
 * Created by jsakhare on 11/04/15.
 */
public class ZipRequest extends BaseRequest {
    public ZipRequest(ResponseHandler tHandler, String url) {
        super(tHandler,GET);
        setUrlStr(url);
    }
}
