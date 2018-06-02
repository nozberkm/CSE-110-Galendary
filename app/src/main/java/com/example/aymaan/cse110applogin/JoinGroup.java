package com.example.aymaan.cse110applogin;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


public class JoinGroup extends AppCompatActivity {

    ListView lstview;
    ArrayAdapter adapter;
    Context context;
    private Button search;
    private Button join;
    String[] items={"Random Group 1","Random Group 2","Random Group 3","Random Group 4","Random Group 5"};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_group);
        context=this;
        lstview=(ListView)findViewById(R.id.listv);
        join = (Button) findViewById(R.id.button);
        search = (Button) findViewById(R.id.button2);

        join.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, items);
                lstview.setAdapter(adapter);
            }
        });

        lstview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text = lstview.getItemAtPosition(position).toString();
                System.out.println(text);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Do you want to send " + text + " a join request?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //send join request
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //do nothing
                    }
                });
                builder.create().show();
            }
        });
    }
}
