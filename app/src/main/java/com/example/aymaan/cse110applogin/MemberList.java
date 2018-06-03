package com.example.aymaan.cse110applogin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.jeff.database_access.DatabaseRequest;
import com.example.jeff.database_access.GroupObject;
import com.example.jeff.database_access.UserObject;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class MemberList extends AppCompatActivity {
    private android.support.v7.widget.Toolbar mToolbar;

    Context context;
    ListView lstview;
    ArrayAdapter adapter;
    Boolean admin;
    ArrayList<UserObject> userList;
    String[] strings;

    private static String[] push(String[] array, String push) {
        String[] longer = new String[array.length + 1];
        for (int i = 0; i < array.length; i++)
            longer[i] = array[i];
        longer[array.length] = push;
        return longer;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_list);
        context=this;

        //for now set admin to whatever
        admin = true;

        mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        strings = new String[0];
        userList = MyGroups.currGroup.loadMembers();


        for (UserObject user : userList){
            strings = push(strings, user.getUsername());
        }

        lstview=(ListView)findViewById(R.id.listv);
        adapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, strings);
        lstview.setAdapter(adapter);
        lstview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                if(admin) {
                    Intent toMemberAdmin = new Intent(MemberList.this, MemberPageAdminView.class);
                    toMemberAdmin.putExtra("name", lstview.getItemAtPosition(position).toString());
                    startActivity(toMemberAdmin);
                }
                else {
                    Intent toMember = new Intent(MemberList.this, MemberPage.class);
                    toMember.putExtra("name", lstview.getItemAtPosition(position).toString());
                    startActivity(toMember);
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
