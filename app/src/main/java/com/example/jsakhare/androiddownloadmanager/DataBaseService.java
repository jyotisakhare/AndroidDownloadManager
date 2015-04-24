package com.example.jsakhare.androiddownloadmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by jsakhare on 12/04/15.
 */

public class DataBaseService extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 3;
    // Database Name
    private static final String DATABASE_NAME = "CacheManager";

    // Contacts table name
    private static final String TABLE_CACHE = "CacheEntity";

    // Contacts Table Columns names
    private static final String KEY = "key";
    private static final String KEY_EXPIRY = "expiry";
    private static final String KEY_ACCESS = "access";
    private static final String KEY_VALUE = "value";

    public DataBaseService(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE = "CREATE TABLE " + TABLE_CACHE + "("
                + KEY + " VARCHAR PRIMARY KEY NOT NULL,"
                + KEY_EXPIRY + " TEXT," + KEY_ACCESS + " TEXT,"  + KEY_VALUE + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CACHE);
        // Create tables again
        onCreate(db);
    }

    public synchronized Boolean addEntry(CacheEntity entry) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            DateFormat dateform = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            values.put(KEY, entry.getKey());
            values.put(KEY_EXPIRY, dateform.format(entry.getExpiry()));
            values.put(KEY_ACCESS, dateform.format(entry.getAccess()));
            values.put(KEY_VALUE,entry.getValue());
            db.insert(TABLE_CACHE, null, values);
            db.close();
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    public synchronized Boolean updateAccessForEntry(String key) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            DateFormat dateform = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
            ContentValues values = new ContentValues();
            values.put(KEY_ACCESS, dateform.format(new Date()));
            int updateStatus = db.update(TABLE_CACHE, values, KEY + " = ?", new String[] { key });
            if(updateStatus != 0) return true;
        } catch (Exception e) {
        }
        return false;
    }

    public CacheEntity getOldestEntry() {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            DateFormat dateform = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
            Cursor cursor = db.query(TABLE_CACHE, new String[] { KEY, KEY_EXPIRY, KEY_ACCESS ,KEY_VALUE}, null, null, null, null, KEY_ACCESS, "1");
            if (cursor != null) cursor.moveToFirst();
            CacheEntity entry = new CacheEntity(cursor.getString(0),dateform.parse(cursor.getString(1)),dateform.parse(cursor.getString(2)),cursor.getString(3));
            return entry;
        } catch (Exception e) {
        }
        return null;
    }

    public CacheEntity getEntry(String key) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            DateFormat dateform = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
            Cursor cursor = db.query(TABLE_CACHE, new String[] { KEY, KEY_EXPIRY, KEY_ACCESS,KEY_VALUE }, KEY + "=?", new String[] { key }, null, null, null, null);
            if (cursor != null) cursor.moveToFirst();
            CacheEntity entry = new CacheEntity(cursor.getString(0),dateform.parse(cursor.getString(1)),dateform.parse(cursor.getString(2)),cursor.getString(3));
            return entry;
        } catch (Exception e) {
        }
        return null;
    }

    public synchronized Boolean deleteEntry(String key) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_CACHE, KEY + " = ?", new String[] { key });
            db.close();
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    public synchronized void clearEntries() {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_CACHE, null, null);
            db.close();
        } catch (Exception e) {
        }
    }
}
