package com.example.jeff.database_access;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class GalendaryDB {
    public static final int PORT = 3000;
    public static final String SERVER_URL_STRING =
            "http://ec2-13-57-18-173.us-west-1.compute.amazonaws.com";

    public URL server_url = null;
    public GalendaryDB()throws MalformedURLException {
        server_url = new URL(SERVER_URL_STRING);
    }


    public static String make_request(ParameterBuilder pb) throws IOException {
        URL url = new URL("http://ec2-13-57-18-173.us-west-1.compute.amazonaws.com:3000" + pb.toString());

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/json");

        InputStream inputStream;

        int status = conn.getResponseCode();

        if (status != HttpURLConnection.HTTP_OK)
            inputStream = conn.getErrorStream();
        else
            inputStream = conn.getInputStream();

        BufferedReader rd = new BufferedReader(new InputStreamReader(
                status == HttpURLConnection.HTTP_OK
                        ? conn.getInputStream()
                        : conn.getErrorStream()
        ));


        StringBuilder result = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        System.out.println('\n' + pb.toString());
        System.out.println("result:" + result.toString());
        if(result.charAt(0) != '{')
            result.insert(0,"{\"data\":").append('}');
        return result.toString();
    }


    public static JSONObject server_request(ParameterBuilder pb) throws IOException {
        try {
            return new JSONObject(make_request(pb));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }





}


