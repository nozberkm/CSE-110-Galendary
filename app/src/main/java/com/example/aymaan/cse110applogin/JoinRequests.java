package com.example.aymaan.cse110applogin;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.jeff.database_access.DatabaseRequest;
import com.example.jeff.database_access.GroupObject;
import com.example.jeff.database_access.UserObject;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class JoinRequests extends AppCompatActivity {
    private android.support.v7.widget.Toolbar mToolbar;

    Context context;
    ListView lstview;
    ArrayAdapter adapter;
    String[] strings;

    private static String[] push(String[] array, String push) {
        String[] longer = new String[array.length + 1];
        for (int i = 0; i < array.length; i++)
            longer[i] = array[i];
        longer[array.length] = push;
        return longer;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_requests);
        context=this;

        mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        strings = new String[0];


        lstview=(ListView)findViewById(R.id.listv);
        adapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, strings);
        lstview.setAdapter(adapter);
        lstview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Do you want to add to the group?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //add to group
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //remove from join request list
                    }
                });
                builder.create().show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
