package com.example.aymaan.cse110applogin;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MemberPage extends AppCompatActivity {
    private android.support.v7.widget.Toolbar mToolbar;
    private Button report;
    Context context;
    String name;
    String username;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_with_report);
        context = this;
        Intent intent = getIntent();
        Bundle bd = intent.getExtras();

        report = (Button) findViewById(R.id.button2);

        TextView memberName = (TextView) findViewById(R.id.textView);
        TextView email = (TextView) findViewById(R.id.textView2);
        name = MemberList.viewedUser.getName();
        username = "email: " + MemberList.viewedUser.getUsername();
        memberName.setText(name);
        email.setText(username);

        report.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Do you want to report " + name + "?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

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

        mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
