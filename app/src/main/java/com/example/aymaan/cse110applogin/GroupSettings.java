package com.example.aymaan.cse110applogin;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.io.IOException;

public class GroupSettings extends AppCompatActivity {
    private android.support.v7.widget.Toolbar mToolbar;
    private Button code;
    private Switch privSwitch;
    private EditText grpName;
    private Button dissolve;
    Context context;

    private Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_group_settings);

        mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        code = (Button) findViewById(R.id.enrollmentCode);
        dissolve = (Button) findViewById(R.id.dissolveGroup3);
        grpName=(EditText) findViewById(R.id.groupName);
        grpName.setText(MyGroups.currGroup.getName());

        save = (Button) findViewById(R.id.saveButtonGroupSettings);

        code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enrollCode = MyGroups.currGroup.generateEnrollmentCode();
                Snackbar.make(v, enrollCode, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        dissolve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Do you want to dissolve this group?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            MyGroups.currGroup.dissolveGroup();
                        }
                        catch(IOException e) {
                            e.printStackTrace();
                        }
                        Intent toHome = new Intent(context, Home.class);
                        startActivity(toHome);
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

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSaveText = grpName.getText().toString();
                if (toSaveText.equals("")){
                    grpName.setText(MyGroups.currGroup.getName());
                } else {
                    boolean hello = MyGroups.currGroup.changeName(toSaveText);
                    grpName.setText(String.valueOf(hello));
                }
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}