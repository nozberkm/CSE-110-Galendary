package com.example.aymaan.cse110applogin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by Pablo on 5/14/2018.
 */

public class MyGroups extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private android.support.v7.widget.Toolbar mToolbar;

    ListView lv;
    ArrayAdapter adapter;
    private Button join;
    private Button create;
    Context context;
    String[] strings = {"My group 1", "My group 2", "My group 3", "My group 4",
            "My group 5", "My group 6", "My group 7", "My group 8"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_groups);
        context = this;
        lv = (ListView) findViewById(R.id.listView);

        adapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, strings);
        lv.setAdapter(adapter);

        join = (Button) findViewById(R.id.button2);
        create = (Button) findViewById(R.id.button);

        join.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent toJoinIntent = new Intent(MyGroups.this, JoinGroup.class);
                startActivity(toJoinIntent);
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String whichGroup = lv.getItemAtPosition(position).toString();
            }
        });

        mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.myGroupsLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onNavigationMenuItemClick(MenuItem item) {
        int id=item.getItemId();
        switch (id){

            case R.id.nav_home:
                Intent h= new Intent(MyGroups.this,Home.class);
                startActivity(h);
                break;
            case R.id.nav_mygroups:
                Intent g= new Intent(MyGroups.this,MyGroups.class);
                startActivity(g);
                break;
            case R.id.nav_settings:
                Intent s= new Intent(MyGroups.this,AccountSettings.class);
                startActivity(s);
                break;
            case R.id.nav_logout:
                Intent l= new Intent(MyGroups.this,LoginActivity.class);
                startActivity(l);
                break;
        }

        mDrawerLayout = (DrawerLayout) findViewById(R.id.myGroupsLayout);
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }
}
