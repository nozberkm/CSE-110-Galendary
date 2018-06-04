package com.example.jeff.database_access;


import java.net.MalformedURLException;
import java.util.Date;

public class Main {

    public static void main(String[] args) {

//        ParameterBuilder pb = new ParameterBuilder();
//        pb.push("command", "statement");
//        pb.push("statement", "SELECT * FROM users;");

//        System.out.println(pb.toString());


        GalendaryDB galdb;
        try{
            galdb = new GalendaryDB();
        }catch(MalformedURLException e){
            System.err.print("Problem with url formation");
            e.printStackTrace();
            return;
        }

        System.out.println("successfully made galdb");

        UserObject existing_user = DatabaseRequest.get_user("jeff", "not_sha1");

        System.out.println(existing_user);

        existing_user.synchronize();

        System.out.println(existing_user);

        GroupObject new_group = existing_user.createGroup("testing new group creation method");
        //DatabaseRequest.create_group(existing_user, "testing group creation java");

        System.out.println(new_group.toString());


//        UserObject new_user = DatabaseRequest.create_user("jeffJAVA2", "not_sha1");
//
//        System.out.println(new_user);

        System.out.println(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        EntryObject new_entry = new EntryObject();
        new_entry.setTitle("Test new EntryObject for new group method");
        new_entry.setDescription("testytest");
        new_entry.setStart(new Date());

        new_group.pushEntry(new_entry);

    }


}
