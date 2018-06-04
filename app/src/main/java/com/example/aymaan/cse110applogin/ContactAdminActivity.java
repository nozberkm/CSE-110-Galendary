package com.example.aymaan.cse110applogin;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.support.v7.widget.Toolbar;

public class ContactAdminActivity extends AppCompatActivity {

    private Toolbar contact_admin_tb;
    private ActionBar contact_admin_ab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_admin);

        contact_admin_tb = (Toolbar)findViewById(R.id.contact_admin_toolbar);
        final EditText subjectEditText = (EditText)findViewById(R.id.subject_edit_text);
        final EditText messageEditText = (EditText)findViewById(R.id.message_edit_text);
        final CardView sendCardView = (CardView)findViewById(R.id.send_card_view);

        setSupportActionBar(contact_admin_tb);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        contact_admin_ab = (ActionBar)getSupportActionBar();

        contact_admin_ab.setTitle("Contact Admin");

        sendCardView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String subject = subjectEditText.getText().toString();
                String message = messageEditText.getText().toString();
                Intent emailAdminIntent = new Intent(Intent.ACTION_SENDTO,
                        Uri.fromParts("mailto", "", null));
                emailAdminIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
                emailAdminIntent.putExtra(Intent.EXTRA_TEXT, message);
                startActivity(Intent.createChooser(emailAdminIntent, "Send Email"));
            }
        });
    }
}