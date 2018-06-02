package com.example.jeff.database_access;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class EntryObject {
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

    public UserObject getUser() {
        return user;
    }

    public void setUser(UserObject user) {
        this.user = user;
    }

    public EntryObject(JSONObject jo){
        try {
            id = jo.getLong("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            group_id = jo.has("gid") ? jo.getLong("gid") : -1;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            title = jo.isNull("title") ? null : jo.getString("title");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(jo.isNull("start")){
            start = null;
        } else {
            try {
                try {
                    start = new SimpleDateFormat("YYYY-MM-DD'T'HH:mm:ss").parse(jo.getString("start"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if(jo.isNull("end")){
            end = null;
        } else {
            try {
                try {
                    end = new SimpleDateFormat("YYYY-MM-DD'T'HH:mm:ss").parse(jo.getString("end"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        try {
            file_count = jo.isNull("file_count") ? -1 : jo.getInt("file_count");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            priority = jo.isNull("priority") ? -1 : jo.getInt("priority");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            recurrence = jo.isNull("recurrence") ? "no recurrence" :jo.getString("recurrence");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            description = jo.isNull("description") ? "" : jo.getString("description");
        } catch (JSONException e) {
            e.printStackTrace();
        }
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


    public void setTitle(String title) {
        this.title = title;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public void setRecurrence(String recurrence) {
        this.recurrence = recurrence;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public void applyGroupAsOwner(GroupObject group, long entry_id){
        group_id = group.getId();
        this.id = entry_id;
        group.addEntryCheckGID(this);
        setUser(group.getUser());
    }


    public static String getDayString(Date date){
        DateFormat df = new SimpleDateFormat("yyyy/dd/MM");

        return df.format(date);
    }

    public String getDayString(){
        return getDayString(getStart());
    }






    // TODO:
    public void deleteEntry(){
        //TODO:


    }
}
