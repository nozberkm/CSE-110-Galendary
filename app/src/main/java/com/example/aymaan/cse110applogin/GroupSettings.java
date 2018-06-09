package com.example.aymaan.cse110applogin;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class GroupSettings extends AppCompatActivity {
    private Button code;
    private Switch privSwitch;
    private TextView grpName;
    private Button dissolve;
    private TextInputEditText groupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_settings);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
       // groupName=(TextInputEditText)findViewById(R.id.grpName);
        code = (Button) findViewById(R.id.enrollmentCode);
       // grpName=(TextView)findViewById(R.id.groupNameHeader);
        grpName.setText(MyGroups.currGroup.getName());
        groupName.setText(MyGroups.currGroup.getName());

        code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enrollCode = MyGroups.currGroup.generateEnrollmentCode();
                Snackbar.make(v, enrollCode, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        privSwitch=(Switch) findViewById(R.id.privacySwitch);
        privSwitch.setChecked(!MyGroups.currGroup.isPublic());

        privSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(MyGroups.currGroup.isPublic()){
                    System.out.printf("lolll");
                    MyGroups.currGroup.makePrivate();
                    privSwitch.setChecked(true);
                }
                else{
                    System.out.printf("lol");
                    MyGroups.currGroup.makePublic();
                    privSwitch.setChecked(false);
                }

            }
        });


    }

}