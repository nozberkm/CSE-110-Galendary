package com.example.aymaan.cse110applogin;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.jeff.database_access.EntryObject;

import java.util.Date;


public class AddNotice extends AppCompatActivity {
    private android.support.v7.widget.Toolbar mToolbar;

    ListView lstview;
    ArrayAdapter adapter;
    Context context;
    //private Button add;
    private EditText etAddNotice;
    private ImageButton close;
    private FloatingActionButton add;
    //private EditText etError;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnotice);
        context=this;
        add = (FloatingActionButton) findViewById(R.id.addButton);
        etAddNotice = (EditText) findViewById(R.id.editTextAddNotice);
        //etError = (EditText) findViewById(R.id.editTextAddNoticeError);
        close = (ImageButton) findViewById(R.id.ibNoticeClose);
        mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);


        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if (etAddNotice.getText().toString().equals("")) {
                //    etError.setText("Notice cannot be empty");
                //} else {
                //    etError.setText("");
                    EntryObject notice = new EntryObject();
                    notice.setDescription(etAddNotice.getText().toString());
                    notice.setStart(new Date());
                    MyGroups.currGroup.pushEntry(notice);
                    Intent nb = new Intent(AddNotice.this, NoticeBoard.class);
                    startActivity(nb);
                //}
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
