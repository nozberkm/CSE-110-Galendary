package com.example.aymaan.cse110applogin;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;


public class CreateGroup extends AppCompatActivity {

    private android.support.v7.widget.Toolbar mToolbar;

    private Button cancel;
    private Button create;
    private EditText etGroupName;
    private EditText etError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        cancel = (Button) findViewById(R.id.cancelCreateButton);
        create = (Button) findViewById(R.id.createGroupButton);
        etGroupName = (EditText) findViewById(R.id.editTextGroupName);
        etError = (EditText) findViewById(R.id.editTextCreateError);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etGroupName.getText().toString().equals("")) {
                    etError.setText("Group Name cannot be empty");
                } else {
                    etError.setText("");
                    Snackbar.make(v, "CREATE GROUP!!!!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

        mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);


        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
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
