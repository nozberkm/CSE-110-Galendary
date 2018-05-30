package com.example.aymaan.cse110applogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * Created by Pablo on 5/14/2018.
 */

public class MyGroups extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mygroups);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.myGroupsLayout);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

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
            case R.id.nav_logout:
                break;
        }

        mDrawerLayout = (DrawerLayout) findViewById(R.id.myGroupsLayout);
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }
}
