package com.example.jsakhare.androiddownloadmanager;

/**
 * Created by jsakhare on 12/04/15.
 */

import java.util.Date;
public class CacheEntity {
    private String key;
    private Date expiry;
    private Date access;
    private  String Value;

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public CacheEntity() {

    }

    public CacheEntity(String lkey, Date lexpiry, Date laccess ,String value) {
        setKey(lkey);
        setExpiry(lexpiry);
        setAccess(laccess);
        setValue(value);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Date getExpiry() {
        return expiry;
    }

    public void setExpiry(Date expiry) {
        this.expiry = expiry;
    }

    public Date getAccess() {
        return access;
    }

    public void setAccess(Date access) {
        this.access = access;
    }

    public boolean isExpired() {
        Date date = new Date();
        if(expiry.compareTo(date) <= 0) return true;
        return false;
    }
}
