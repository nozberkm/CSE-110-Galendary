package com.example.aymaan.cse110applogin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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

import com.example.jeff.database_access.EntryObject;

import java.util.ArrayList;

/**
 * Created by Pablo on 5/14/2018.
 */

public class NoticeBoard extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private android.support.v7.widget.Toolbar mToolbar;

    ListView lv;
    ArrayAdapter adapter;
    Context context;
    String[] strings;

    ArrayList<EntryObject> noticeList;

    private static String[] push(String[] array, String push) {
        String[] longer = new String[array.length + 1];
        for (int i = 0; i < array.length; i++)
            longer[i] = array[i];
        longer[array.length] = push;
        return longer;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticeboard);

        context = this;

        strings = new String[0];

        noticeList = MyGroups.currGroup.getNotices();

        for (EntryObject notice : noticeList){
            strings = push(strings, notice.getDescription());
        }


        lv = (ListView) findViewById(R.id.noticeBoardListView);

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, strings);

        lv.setAdapter(adapter);

        mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.noticeBoardLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton noticeBoard_fab = (FloatingActionButton) findViewById(R.id.noticeboard_fab);

        noticeBoard_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent an = new Intent(NoticeBoard.this, AddNotice.class);
                startActivity(an);
            }
        });

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
                Intent h= new Intent(NoticeBoard.this,Home.class);
                startActivity(h);
                break;
            case R.id.nav_mygroups:
                Intent g= new Intent(NoticeBoard.this,MyGroups.class);
                startActivity(g);
                break;
            case R.id.nav_settings:
                Intent s= new Intent(NoticeBoard.this,AccountSettings.class);
                startActivity(s);
                break;
            case R.id.nav_logout:
                Intent l= new Intent(NoticeBoard.this,LoginActivity.class);
                startActivity(l);
                break;
        }

        mDrawerLayout = (DrawerLayout) findViewById(R.id.noticeBoardLayout);
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    public void onGroupNavigationMenuItemClick(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.group_nav_noticeBoard:
                Intent nb = new Intent(NoticeBoard.this, NoticeBoard.class);
                startActivity(nb);
                break;
            case R.id.group_nav_members:
                Intent gm = new Intent(NoticeBoard.this, MemberList.class);
                startActivity(gm);
                break;
            case R.id.group_nav_heatmap:
                Intent h = new Intent(NoticeBoard.this, Heatmap.class);
                startActivity(h);
                break;
            case R.id.group_nav_contactAdmin:
                Intent ca= new Intent(NoticeBoard.this,NoticeBoard.class);
                startActivity(ca);
                break;
            case R.id.group_nav_relatedGroups:
                Intent rg = new Intent(NoticeBoard.this, RelatedGroups.class);
                startActivity(rg);
                break;
            case R.id.group_nav_leaveGroup:
                break;
        }

        mDrawerLayout = (DrawerLayout) findViewById(R.id.noticeBoardLayout);
        mDrawerLayout.closeDrawer(GravityCompat.END);
    }
}
