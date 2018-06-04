package com.example.aymaan.cse110applogin;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.jeff.database_access.GroupObject;

import java.util.ArrayList;


public class JoinGroup extends AppCompatActivity {
    private android.support.v7.widget.Toolbar mToolbar;
    public static GroupObject selectedGroup;

    ListView lstview;
    ArrayAdapter adapter;
    Context context;
    private Button search;
    private Button join;
    private EditText joinCode;
    private EditText searchIn;
    String[] strings;
    ArrayList<GroupObject> searchResults;

    private static String[] push(String[] array, String push) {
        String[] longer = new String[array.length + 1];
        for (int i = 0; i < array.length; i++)
            longer[i] = array[i];
        longer[array.length] = push;
        return longer;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_group);
        context=this;
        lstview=(ListView)findViewById(R.id.listv);
        join = (Button) findViewById(R.id.button);
        search = (Button) findViewById(R.id.button2);
        joinCode = (EditText) findViewById(R.id.editText);
        searchIn = (EditText) findViewById(R.id.editText2);

        mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        join.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String code = joinCode.getText().toString();
                GroupObject joinedGroup = LoginActivity.userLogin.joinGroupByEnrollmentCode(code);
                if(joinedGroup == null) {
                    Snackbar.make(v, "Error: not a valid enrollment code", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else {
                    MyGroups.currGroup = joinedGroup;
                    Intent toGroup = new Intent(JoinGroup.this, GroupHomeActivity.class);
                    startActivity(toGroup);
                }
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strings = new String[0];
                String sendSearch = searchIn.getText().toString();
                searchResults = LoginActivity.userLogin.getGroupsMatchingString(sendSearch);
                if(searchResults != null) {
                    for (GroupObject group : searchResults) {
                        strings = push(strings, group.getName());
                    }
                }
                strings = push(strings, "Definitely A Real Group");
                adapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, strings);
                lstview.setAdapter(adapter);
            }
        });

        lstview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text = lstview.getItemAtPosition(position).toString();
                //selectedGroup = searchResults.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Do you want to join " + text + "?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //send join request
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
