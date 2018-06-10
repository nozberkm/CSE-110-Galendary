package com.example.aymaan.cse110applogin;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
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
import android.widget.Toast;

import com.example.jeff.database_access.EntryObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

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

    private static ArrayList<EntryObject> orderByDate(ArrayList<EntryObject> array) {
        ArrayList<EntryObject> toReturn = new ArrayList<EntryObject>();
        int size = array.size();
        int indexToReturn = 0;
        while (size>0) {
            Date toPushDate = new Date();
            EntryObject toPushEntry = new EntryObject();
            int toDeletePosition = 0;
            for (EntryObject notice : array){
                 if(notice.getStart().before(toPushDate)){
                    toDeletePosition = array.indexOf(notice);
                    toPushEntry = notice;
                    toPushDate = notice.getStart();
                 }
            }
            toReturn.add(indexToReturn, toPushEntry);
            indexToReturn ++;
            array.remove(toDeletePosition);
            size = array.size();
        }
        return toReturn;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticeboard);

        context = this;

        strings = new String[0];

        noticeList = MyGroups.currGroup.getNotices();
        //ArrayList<EntryObject> orderedNoticeList = orderByDate(noticeList);
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

        NavigationView navigationView = (NavigationView) findViewById(R.id.notice_board_navView);
        View headerView = navigationView.getHeaderView(0);
        EditText navHeader = (EditText) headerView.findViewById(R.id.group_nav_header);
        navHeader.setText(MyGroups.currGroup.getName());

        FloatingActionButton noticeBoard_fab = (FloatingActionButton) findViewById(R.id.noticeboard_fab);

        if (!MyGroups.currGroup.isAdmin()){
            noticeBoard_fab.setVisibility(View.INVISIBLE);
        } else {
            noticeBoard_fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent an = new Intent(NoticeBoard.this, AddNotice.class);
                    startActivity(an);
                }
            });
        }

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
                if(MyGroups.currGroup.isAdmin()) {
                    Intent h = new Intent(NoticeBoard.this, Heatmap.class);
                    startActivity(h);
                }
                else {
                    Snackbar.make(findViewById(android.R.id.content), "You need to be an admin", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                break;
            case R.id.group_nav_contactAdmin:
                Intent ca= new Intent(NoticeBoard.this,ContactAdminActivity.class);
                startActivity(ca);
                break;
            case R.id.group_nav_groupSettings:
                if(MyGroups.currGroup.isAdmin()) {
                    Intent gs = new Intent(NoticeBoard.this, GroupSettings.class);
                    startActivity(gs);
                }
                else {
                    Snackbar.make(findViewById(android.R.id.content), "You need to be an admin", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                break;
            case R.id.group_nav_leaveGroup:
                AlertDialog.Builder builder = new AlertDialog.Builder(NoticeBoard.this);
                builder.setMessage("Do you want to leave this group?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try{
                            LoginActivity.userLogin.leave_group(LoginActivity.userLogin.getId(), MyGroups.currGroup.getId());
                        }
                        catch(IOException e) {
                            e.printStackTrace();
                        }
                        LoginActivity.userLogin.synchronize();
                        Intent gohome = new Intent(NoticeBoard.this, Home.class);
                        startActivity(gohome);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //do nothing
                    }
                });
                builder.create().show();
                break;
        }

        mDrawerLayout = (DrawerLayout) findViewById(R.id.noticeBoardLayout);
        mDrawerLayout.closeDrawer(GravityCompat.END);
    }
}
