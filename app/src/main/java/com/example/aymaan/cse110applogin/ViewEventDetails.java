package com.example.aymaan.cse110applogin;

import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.widget.ImageButton;
import android.widget.TextView;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class ViewEventDetails extends AppCompatActivity {

    //TODO: Need to implement oncreate; set 3 listeners for edit, delete, and exit
    //TODO: Need to link to database

    private ImageButton exit_button;
    private ImageButton edit_button;
    private ImageButton delete_button;
    String previous;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_details);

        TextView event_name = (TextView)findViewById(R.id.ed_event_name);
        TextView group_name = (TextView)findViewById(R.id.ed_group_name);
        TextView start_date = (TextView)findViewById(R.id.ed_event_start_date);
        TextView end_date = (TextView)findViewById(R.id.ed_event_end_date);
        TextView start_time = (TextView)findViewById(R.id.ed_event_start_time);
        TextView end_time = (TextView)findViewById(R.id.ed_event_end_time);
        TextView description = (TextView)findViewById(R.id.ed_description_field);

        Bundle b = getIntent().getExtras();
        if(b != null) {
            event_name.setText(b.getString("event name"));
            group_name.setText(b.getString("group name"));
            start_date.setText(b.getString("event start"));
            end_date.setText(b.getString("event end"));
            start_time.setText(b.getString("event start time"));
            end_time.setText(b.getString("event end time"));
            description.setText(b.getString("event description"));
            previous = b.getString("previous");
        }

        exit_button = (ImageButton) findViewById(R.id.ed_exit_button);
        edit_button = (ImageButton) findViewById(R.id.ed_edit_button);
        delete_button = (ImageButton) findViewById(R.id.ed_delete_button);


        //TODO: Here we set the on click listeners for the top buttons
        exit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                /*System.err.println(previous);
                if(previous.equals("Home")) {
                    Intent toExit = new Intent(ViewEventDetails.this, Home.class);
                    startActivity(toExit);
                    finish();
                }
                else if (previous.equals("groupHome")) {
                    Intent toExit = new Intent(ViewEventDetails.this, GroupHomeActivity.class);
                    startActivity(toExit);
                    finish();
                }*/
            }
        });

        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean ifDeleted;
                if(previous.equals("Home")) {
                    Home.currentEvent.setUser(LoginActivity.userLogin);
                    ifDeleted = Home.currentEvent.delete();
                    if(ifDeleted) {
                        Intent toExit = new Intent(ViewEventDetails.this, Home.class);
                        startActivity(toExit);
                        finish();
                    }
                    else {
                        Snackbar.make(view, "The event could not be deleted", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    }
                }
                else if(previous.equals("groupHome")) {
                    GroupHomeActivity.currentGroupEvent.setUser(LoginActivity.userLogin);
                    ifDeleted = GroupHomeActivity.currentGroupEvent.delete();
                    if(ifDeleted) {
                        Intent toExit = new Intent(ViewEventDetails.this, GroupHomeActivity.class);
                        startActivity(toExit);
                        finish();
                    }
                    else{
                        Snackbar.make(view, "The event could not be deleted", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    }
                }

            }
        });
    }
}

