package com.example.aymaan.cse110applogin;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


public class AddNotice extends AppCompatActivity {
    private android.support.v7.widget.Toolbar mToolbar;

    ListView lstview;
    ArrayAdapter adapter;
    Context context;
    private Button add;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnotice);
        context=this;
        add = (Button) findViewById(R.id.addButton);

        mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);


        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Add a new notice!!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

}
