package com.example.jsakhare.androiddownloadmanager;

/**
 * Created by jsakhare on 11/04/15.
 */
public class BaseRequest {
    public static final int GET = 0;
    public static final int POST = 1;
    public static final int PUT = 2;
    public static final int DELETE = 3;
    private String urlStr;
    private ResponseHandler handler;
    private int requestType;

    public BaseRequest(ResponseHandler rhandler, int type) {
        this.handler = rhandler;
        this.requestType = type;
    }

    public String getUrlStr() {
        return urlStr;
    }

    public void setUrlStr(String urlStr) {
        this.urlStr = urlStr;
    }

    public ResponseHandler getHandler() {
        return handler;
    }

    public void setHandler(ResponseHandler handler) {
        this.handler = handler;
    }

}
