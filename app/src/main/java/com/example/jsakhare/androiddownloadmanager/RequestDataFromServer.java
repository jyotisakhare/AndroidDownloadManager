package com.example.jsakhare.androiddownloadmanager;

/**
 * Created by jsakhare on 11/04/15.
 */
public class RequestDataFromServer {

    public static String getZipResponse(ResponseHandler tHandler,String url){
        BaseRequest request = new ZipRequest(tHandler,url);
        getdata(request);
        return null;
    }

    private static void getdata(BaseRequest request) {
        FetchZipDataTask task = new FetchZipDataTask();
        task.execute(request);
    }
}
