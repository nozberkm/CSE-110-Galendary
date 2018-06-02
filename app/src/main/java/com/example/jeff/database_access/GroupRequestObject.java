package com.example.jeff.database_access;


import org.json.JSONException;
import org.json.JSONObject;


import java.lang.reflect.Array;
import java.util.ArrayList;

public class GroupRequestObject {
    private long user_id = -1;
    private long group_id = -1;


    public GroupRequestObject(JSONObject jo){
        try {
            user_id = jo.getLong("user_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            group_id = jo.getLong("group_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public long getUserId() { return user_id; }

    public long getGroupId() { return group_id; }

    public String toString(){
        //TODO:
        return"{todo}";
//        retur n "Group:" + getGroupId() + ", User:" + getUserId() + ", " + (entries==null ? 0 : entries.size()) + ')';
    }
}
