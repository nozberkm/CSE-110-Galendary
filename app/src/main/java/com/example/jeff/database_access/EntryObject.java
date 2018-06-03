package com.example.jeff.database_access;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class EntryObject {
    public static final DateFormat DAY_DATE_FORMAT = new SimpleDateFormat("yyyy/dd/MM");
    public static final DateFormat DB_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    public static final DateFormat TIME_FORMAT = new SimpleDateFormat("hh:mm a");

    private long id;
    private long group_id;
    private String title;
    private Date start;
    private Date end;
    private int file_count;
    private int priority;
    private String recurrence;
    private String description;

    private UserObject user = null;

    public EntryObject(){
        id = -1;
        group_id = -1;
        title = null;
        start = null;
        end = null;
        file_count = 0;
        priority = -1;
        recurrence = null;
        description = null;
    }

    public EntryObject(UserObject user){
        this();
        setUser(user);
    }

    public EntryObject(JSONObject jo){
        parseIdFromJson(jo);
        parseGidFromJson(jo);
        parseTitleFromJson(jo);
        parseStartFromJson(jo);
        parseEndFromJSON(jo);
        parseFileCountFromJson(jo);
        parsePriorityFromJson(jo);
        parseRecurrenceFromJson(jo);
        parseDescriptionFromJson(jo);
    }

    public UserObject getUser() {
        return user;
    }

    public void setUser(UserObject user) {
        this.user = user;
    }







    public long getId() { return id; }
    public long getGroupId(){ return group_id; }

    public String getTitle() { return title; }

    public Date getStart() { return start; }

    public Date getEnd() { return end; }

    public int getFileCount() { return file_count; }

    public int getPriority() { return priority; }

    public String getRecurrence() { return recurrence; }

    public String getDescription() { return description; }

    public boolean isTask(){
        return getStart() == null && getEnd() != null;
    }

    public boolean isEvent(){
        return getStart() != null && getEnd() != null;
    }

    public boolean isNotice(){
        return getStart() != null && getEnd() == null;
    }


    public void setTitle(String title) { this.title = title; }

    public void setStart(Date start) { this.start = start; }

    public void setEnd(Date end) { this.end = end; }

    public void setRecurrence(String recurrence) { this.recurrence = recurrence; }

    public void setDescription(String description) { this.description = description; }








    public String toString(){
        return new StringBuilder().append("EntryObject")
                .append(", ").append(getId())
                .append(", ").append(getTitle())
                .append(", ").append(getStart())
                .append(", ").append(getEnd())
                .append(", ").append(getFileCount())
                .append(", ").append(getPriority())
                .append(", ").append(getRecurrence())
                .append(", ").append(getDescription())
                .toString();
    }






    public void applyGroupAsOwner(GroupObject group, long entry_id){
        group_id = group.getId();
        this.id = entry_id;
        group.addEntryCheckGID(this);
        setUser(group.getUser());
    }

    public static Date getDayDateFromString(String day_string){
        Date toret = null;
        try {
            toret = DAY_DATE_FORMAT.parse(day_string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return toret;
    }

    public static String getDayString(Date date){
        return DAY_DATE_FORMAT.format(date);
    }

    public String getDayString(){
        return getDayString(getStart());
    }

    public static String getDayOfWeek(Date date) {
        return new SimpleDateFormat("EEE").format(date);
    }

    public static String getTimeString(Date date) { return TIME_FORMAT.format(date);}





    // TODO:
    public void deleteEntry(){
        //TODO:


    }










    // Below are private parsing methods for handling extraction of information from JSONObjects
    // Used by the constructor.


    private static String parseStringFromJson(JSONObject jo, String key){
        String str = null;
        try {
            str = jo.isNull(key) ? null : jo.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return str;
    }
    private static long parseLongFromJson(JSONObject jo, String key){
        long toret = -1;
        try {
            toret = jo.getLong(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return toret;
    }
    private static int parseIntFromJson(JSONObject jo, String key){
        int toret = -1;
        try {
            toret = jo.isNull(key) ? -1 : jo.getInt(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return toret;
    }
    private static Date parseDateFromJson(JSONObject jo, String key){
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

    private void parseIdFromJson(JSONObject jo){
        id = parseLongFromJson(jo, "id");
    }
    private void parseGidFromJson(JSONObject jo){
        group_id = parseLongFromJson(jo, "gid");
    }
    private void parseTitleFromJson(JSONObject jo){
        title = parseStringFromJson(jo, "title");
    }
    private void parseStartFromJson(JSONObject jo){
        start = parseDateFromJson(jo, "start");
    }
    private void parseEndFromJSON(JSONObject jo){
        end = parseDateFromJson(jo, "end");
    }
    private void parseFileCountFromJson(JSONObject jo){
        file_count = parseIntFromJson(jo, "file_count");
    }
    private void parsePriorityFromJson(JSONObject jo){
        priority = parseIntFromJson(jo, "priority");
    }
    private void parseRecurrenceFromJson(JSONObject jo){
        recurrence = parseStringFromJson(jo, "recurrence");
    }
    private void parseDescriptionFromJson(JSONObject jo){
        description = parseStringFromJson(jo, "description");
    }
}
