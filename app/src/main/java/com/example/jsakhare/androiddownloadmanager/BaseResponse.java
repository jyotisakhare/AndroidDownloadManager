package com.example.jsakhare.androiddownloadmanager;

/**
 * Created by jsakhare on 11/04/15.
 */
public class BaseResponse {


    private Integer status;
    private String message;
    private BaseRequest request;
    private String response;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public BaseResponse(Integer status,String message ,String jsondata, BaseRequest requestObject){
        this.request = requestObject;
        this.status =status;
        this.message = message;
       this.response = jsondata;

    }


    public BaseRequest getRequest() {
        return request;
    }

    public void setRequest(BaseRequest request) {
        this.request = request;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
