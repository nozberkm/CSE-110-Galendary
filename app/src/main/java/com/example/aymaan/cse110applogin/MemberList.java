package com.example.aymaan.cse110applogin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MemberList extends AppCompatActivity {
    private android.support.v7.widget.Toolbar mToolbar;

    Context context;
    ListView lstview;
    ArrayAdapter adapter;
    Boolean admin;
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


        lstview=(ListView)findViewById(R.id.listv);
        String[] items={"Group Member 1","Group Member 2","Group Member 3","Group Member 4","Group Member 5"};
        adapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, items);
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
