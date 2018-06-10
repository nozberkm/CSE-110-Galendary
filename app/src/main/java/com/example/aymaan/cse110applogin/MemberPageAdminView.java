package com.example.aymaan.cse110applogin;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.lang.reflect.Member;

public class MemberPageAdminView extends AppCompatActivity {
    private android.support.v7.widget.Toolbar mToolbar;
    private Button promote;
    private Button remove;
    Context context;
    String name;
    String username;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_with_admin);
        context = this;

        Intent intent = getIntent();
        Bundle bd = intent.getExtras();

        TextView memberName = (TextView) findViewById(R.id.textView);
        TextView email = (TextView) findViewById(R.id.textView2);
        name = MemberList.viewedUser.getUsername();
        username = MemberList.viewedUser.getUsername();
        memberName.setText(name);
        email.setText("email: " + username);

       promote = (Button) findViewById(R.id.button2);
       remove = (Button) findViewById(R.id.button3);


        promote.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Do you want to promote " + name + "?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            MyGroups.currGroup.promoteToAdmin(MemberList.viewedUser.getId());
                        }
                        catch(IOException e) {
                            e.printStackTrace();
                        }
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


        remove.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(MemberList.viewedUser.getId() != LoginActivity.userLogin.getId()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Do you want to remove " + name + "?");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                MemberList.viewedUser.leave_group(MemberList.viewedUser.getId(), MyGroups.currGroup.getId());
                            }
                            catch(IOException e) {
                                e.printStackTrace();
                            }
                            Intent toGroupHome = new Intent(context, GroupHomeActivity.class);
                            startActivity(toGroupHome);
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
                else {
                    Snackbar.make(v, "You can't remove yourself", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });


        mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Member: "+MemberList.viewedUser.getName());

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
