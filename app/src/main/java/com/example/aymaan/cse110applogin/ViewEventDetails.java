package com.example.aymaan.cse110applogin;

import android.os.PersistableBundle;
import android.support.annotation.Nullable;
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

    @Override
    //TODO: Need to implement oncreate; set 3 listeners for edit, delete, and exit
    //TODO: Need to link to database

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_details);

        TextView event_name = (TextView)findViewById(R.id.ed_event_name);
        TextView group_name = (TextView)findViewById(R.id.ed_group_name);
        TextView start_date = (TextView)findViewById(R.id.ed_event_start_date);
        TextView end_date = (TextView)findViewById(R.id.ed_event_end_date);
        TextView start_time = (TextView)findViewById(R.id.ed_event_start_time);
        TextView end_time = (TextView)findViewById(R.id.ed_event_end_time);
        TextView reminder_text = (TextView)findViewById(R.id.ed_reminder_text);
        TextView description = (TextView)findViewById(R.id.ed_description_field);

        //TODO:Here we implement the part where we pull the event details from database, and parse them

        //Pull here?
        Bundle b = getIntent().getExtras();
        if(b != null) {
            event_name.setText(b.getString("event name"));
            //group_name.setText();
            start_date.setText(b.getString("event start"));
            end_date.setText(b.getString("event end"));
            start_time.setText(b.getString("event start time"));
            end_time.setText(b.getString("event end time"));
            //reminder_text.setText();
            description.setText(b.getString("event description"));
        }

/*
        event_name.setText("Armageddon");
        group_name.setText("Jeebus and the Angels");
        date.setText("06/10/2018");
        start_time.setText("12:00AM");
        end_time.setText("11:59PM");
        reminder_text.setText("Reminder: None");
        description.setText("Description: We all gon' DIE");
*/
        //TODO: Here we set the on click listeners for the top buttons
    }
}

