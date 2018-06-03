package com.example.aymaan.cse110applogin;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ViewEventDetails extends AppCompatActivity {

    @Override
    //TODO: Need to implement oncreate; set 3 listeners for edit, delete, and exit
    //TODO: Need to link to database
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView event_name = (TextView)findViewById(R.id.ed_event_name);
        TextView group_name = (TextView)findViewById(R.id.ed_group_name);
        TextView date = (TextView)findViewById(R.id.ed_event_date);
        TextView start_time = (TextView)findViewById(R.id.ed_event_start_time);
        TextView end_time = (TextView)findViewById(R.id.ed_event_end_time);
        TextView reminder_text = (TextView)findViewById(R.id.ed_reminder_text);
        TextView description = (TextView)findViewById(R.id.ed_description_field);

        //TODO:Here we implement the part where we pull the event details from database, and parse them
        /*
        //Pull here?
        event_name.setText();
        group_name.setText();
        date.setText();
        start_time.setText();
        end_time.setText();
        reminder_text.setText();
        description.setText();


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
