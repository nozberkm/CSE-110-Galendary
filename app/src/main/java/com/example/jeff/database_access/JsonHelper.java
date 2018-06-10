package com.example.jeff.database_access;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/* A utility class for helping with JSON text processing */
public class JsonHelper {

    // A formatter to convert date strings and objects for MySQL database format
    public static final DateFormat DB_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    /* Helpers to aide in parsing types from JSONObjects.
    Used by UserObject, EntryObject and GroupObject */
    public static String parseString(JSONObject jo, String key){
        String str = null;
        try {
            str = jo.isNull(key) ? null : jo.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return str;
    }
    public static long parseLong(JSONObject jo, String key){
        long toret = -1;
        try {
            toret = jo.getLong(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return toret;
    }
    public static int parseInt(JSONObject jo, String key){
        int toret = -1;
        try {
            toret = jo.isNull(key) ? -1 : jo.getInt(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return toret;
    }

    public static boolean parseBoolean(JSONObject jo, String key){
        boolean toret = false;

        if(jo.has(key)) {
            try {
                toret = jo.getInt(key) > 0;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return toret;
    }
    public static Date parseDate(JSONObject jo, String key){
        Date dat = null;
        if(!jo.isNull(key)){
            dat = null;
            try {
                try {
                    dat = DB_DATE_FORMAT.parse(jo.getString(key));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return dat;
    }
}
