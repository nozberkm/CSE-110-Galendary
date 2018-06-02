package com.example.aymaan.cse110applogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MemberPage extends AppCompatActivity {
    private android.support.v7.widget.Toolbar mToolbar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_with_report);
        Intent intent = getIntent();
        Bundle bd = intent.getExtras();

        TextView memberName = (TextView) findViewById(R.id.textView);
        TextView email = (TextView) findViewById(R.id.textView2);
        memberName.setText((String) bd.get("name"));
        email.setText("email: email@email.com");

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
