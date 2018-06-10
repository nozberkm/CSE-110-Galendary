package com.example.aymaan.cse110applogin;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.util.Pair;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.support.v7.widget.Toolbar;

import com.example.jeff.database_access.UserObject;

import java.util.ArrayList;

public class ContactAdminActivity extends AppCompatActivity {

    private android.support.v7.widget.Toolbar mToolbar;
    private ActionBar contact_admin_ab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_admin);

        final EditText subjectEditText = (EditText)findViewById(R.id.subject_edit_text);
        final EditText messageEditText = (EditText)findViewById(R.id.message_edit_text);
        final CardView sendCardView = (CardView)findViewById(R.id.send_card_view);

        mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        sendCardView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String subject = subjectEditText.getText().toString();
                String message = messageEditText.getText().toString();
                ArrayList<UserObject> groupMembers = MyGroups.currGroup.loadMembers();
                //TODO
                for (int i = 0; i < groupMembers.size(); i++)
                    MyGroups.currGroup.getId();
                Intent emailAdminIntent = new Intent(Intent.ACTION_SENDTO,
                        Uri.fromParts("mailto", "", null));
                emailAdminIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
                emailAdminIntent.putExtra(Intent.EXTRA_TEXT, message);
                startActivity(Intent.createChooser(emailAdminIntent, "Send Email"));
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}