package com.example.jeff.database_access;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;

public class ParameterBuilder {
    // This is a really shitty way to handle null values, but it ought to work for now
    public static String NULL_STRING = "{NULL_PLS_NO_USER_TYPE_ME_IN_AS_REAL_VALUE}";


    public Map<String, String> param_map = null;
    public ParameterBuilder(){
        param_map = new HashMap<>();
    }
    public ParameterBuilder(Map<String, String> map){
        this();
        push(map);
    }
    public ParameterBuilder(String[] param) throws ArrayIndexOutOfBoundsException {
        this();
        push(param);
    }
    public ParameterBuilder(String[][] params) throws ArrayIndexOutOfBoundsException{
        this();
        push(params);
    }
    public ParameterBuilder(UserObject user){
        this();
        push("username", user.getUsername());
        push("passhash", user.getPasshash());
    }
    public ParameterBuilder(String command){
        this();
        push("command", command);
    }



    public int size() {
        return param_map.size();
    }


    public ParameterBuilder push(String key, String value){
        param_map.put(key, value);
        return this;
    }
    public ParameterBuilder push(String key, boolean value){
        return push(key, String.valueOf(value));
    }
    public ParameterBuilder push(String key, int value){
        return push(key, String.valueOf(value));
    }
    public ParameterBuilder push(String key, long value){
        return push(key, String.valueOf(value));
    }
    public ParameterBuilder push(String key, double value){
        return push(key, String.valueOf(value));
    }
    public ParameterBuilder push(String key, Date date){

        return push(key, date == null
                ? null
                : new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
    }

    public ParameterBuilder push_username(String username){
        return push("username", username);
    }
    public ParameterBuilder push_passhash(String passhash){
        return push("passhash", passhash);
    }



    public ParameterBuilder push(Map<String, String> map){
        for(Map.Entry<String, String> entry : map.entrySet())
            push(entry.getKey(), entry.getValue());
        return this;
    }
    public ParameterBuilder push(String[] param) throws ArrayIndexOutOfBoundsException{
        if(param.length != 2)
            throw new ArrayIndexOutOfBoundsException("ParameterBuilder called with array of size != 2");
        return push(param[0], param[1]);
    }
    public ParameterBuilder push(String[][] params) throws ArrayIndexOutOfBoundsException {
        for(int i=0; i<params.length; ++i)
            push(params[i]);
        return this;
    }

    public ParameterBuilder push(UserObject user){
        return push("username", user.getName()).push("passhash", user.getPasshash());
    }


    public String toString(){
        if(param_map.isEmpty()) return "";

        StringBuilder sb = new StringBuilder();
        int param_count = 0;
        for (Map.Entry<String, String> entry : param_map.entrySet()){
            sb.append( (++param_count == 1) ? '?' : '&')
                    .append(entry.getKey())
                    .append('=');
            String entry_str = entry.getValue() == null ? NULL_STRING : entry.getValue();
            try {
                sb.append(URLEncoder.encode(entry_str, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

}
