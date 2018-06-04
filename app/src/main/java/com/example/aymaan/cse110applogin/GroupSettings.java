package com.example.aymaan.cse110applogin;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class GroupSettings extends AppCompatActivity {
    private Button code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        code = (Button) findViewById(R.id.enrollmentCode);

        code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enrollCode = MyGroups.currGroup.generateEnrollmentCode();
                Snackbar.make(v, enrollCode, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


    }

}