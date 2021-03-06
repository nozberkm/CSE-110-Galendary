package com.example.aymaan.cse110applogin;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jeff.database_access.EntryObject;
import com.example.jeff.database_access.UserObject;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class GroupHomeActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private Toolbar group_toolbar_month;
    private Toolbar group_toolbar_name;
    private ActionBar group_actionBar_month;
    private ActionBar group_actionBar_name;
    private SimpleDateFormat dateFormatForDisplaying = new SimpleDateFormat("dd-M-yyyy hh:mm:ss a", Locale.getDefault());
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMM - yyyy", Locale.getDefault());
    private CompactCalendarView group_compactCalendarView;
    private EventAdapter group_eventAdapter;
    public static Date clickDate = null;
    public static EntryObject currentGroupEvent;

    private AdapterView.OnItemClickListener eventClickedHandler = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            ListView l = (ListView)parent;
            EntryObject clickedItem = (EntryObject) l.getItemAtPosition(position);
            currentGroupEvent = clickedItem;
            Bundle b = new Bundle();
            //b.putLong("id",clickedItem.getId());
            //b.putLong("group id",clickedItem.getGroupId());
            if (MyGroups.currGroup.isAdmin()) {
                b.putBoolean("Admin", true);
            }
            else{
                b.putBoolean("Admin", false);
            }
            b.putString("event name",clickedItem.getTitle());
            if(clickedItem.getEnd() == null){
                b.putString("event end","");
                b.putString("event end time","");
            }
            else {
                b.putString("event end",EntryObject.getDayString(clickedItem.getEnd()));
                b.putString("event end time",EntryObject.getTimeString(clickedItem.getEnd()));
            }
            if(clickedItem.getStart() == null){
                b.putString("event start","");
                b.putString("event start time","");
            }
            else {
                b.putString("event start",EntryObject.getDayString(clickedItem.getStart()));
                b.putString("event start time",EntryObject.getTimeString(clickedItem.getStart()));
            }

            try {
                if(clickedItem.getGroupName().equals("(individual group)")){
                    b.putString("group name", "Personal");
                }
                else{
                    b.putString("group name", clickedItem.getGroupName());
                }
            }
            catch (Exception e){
                b.putString("group name", "");
            }
            b.putString("event description",clickedItem.getDescription());
            b.putString("previous", "groupHome");
            Intent ved = new Intent( GroupHomeActivity.this, ViewEventDetails.class);
            ved.putExtras(b);
            startActivity(ved);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_home);

        clickDate = null;

        group_compactCalendarView = (CompactCalendarView) findViewById(R.id.group_compactcalendar_view);

        group_toolbar_month = (Toolbar)findViewById(R.id.group_tool_bar_month);
        group_toolbar_name = (Toolbar) findViewById(R.id.group_tool_bar_name);

        group_toolbar_month.setTitleTextColor(Color.WHITE);
        setSupportActionBar(group_toolbar_month);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        group_actionBar_month = (ActionBar)getSupportActionBar();
        group_actionBar_month.setTitle(dateFormatForMonth.format(group_compactCalendarView.getFirstDayOfCurrentMonth()));

        group_toolbar_name.setTitleTextColor(Color.WHITE);
        setSupportActionBar(group_toolbar_name);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        group_actionBar_name = (ActionBar)getSupportActionBar();
        group_actionBar_name.setTitle(MyGroups.currGroup.getName());


        mDrawerLayout = (DrawerLayout) findViewById(R.id.grouphomePageLayout);


        NavigationView navigationView = (NavigationView) findViewById(R.id.group_home_navView);
        View headerView = navigationView.getHeaderView(0);
        EditText navHeader = (EditText) headerView.findViewById(R.id.group_nav_header);
        navHeader.setText(MyGroups.currGroup.getName());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.group_fab);
        final ListView listView = (ListView) findViewById(R.id.group_home_list);

        Map<String, ArrayList<EntryObject>> GroupEntryMap = MyGroups.currGroup.getEntryMap();
        if(GroupEntryMap!= null) {
            //toolbar.setTitle("Man");
            for(String s: GroupEntryMap.keySet()) {
                Date date = EntryObject.getDayDateFromString(s);
                for(int i=0; i<GroupEntryMap.get(s).size(); i++) {
                    if (!GroupEntryMap.get(s).get(i).isNotice()) {
                        Event ev1 = new Event(Color.BLACK, date.getTime());
                        group_compactCalendarView.addEvent(ev1);
                    }
                }
            }
        }
        else {
            //Do Nothing
        }

        if (!MyGroups.currGroup.isAdmin()){
            fab.setVisibility(View.INVISIBLE);
        } else {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle b = new Bundle();
                    if(clickDate == null) {
                        b.putLong("date", Calendar.getInstance().getTimeInMillis());
                    }
                    else {
                        b.putLong("date",clickDate.getTime());
                    }
                    Intent aea = new Intent(GroupHomeActivity.this, GroupAddTaskEventActivity.class);
                    aea.putExtras(b);
                    startActivity(aea);
                }
            });
        }

        group_compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            //This should be on the group home as well
            //TODO
            @Override
            public void onDayClick(Date dateClicked) {
                clickDate = dateClicked;
                Map<String, ArrayList<EntryObject>> EntryMap = MyGroups.currGroup.getEntryMap();
                String date = EntryObject.getDayString(dateClicked);
                ArrayList<EntryObject> list = EntryMap.get(date);
                ArrayList<EntryObject> showList = new ArrayList<>();
                if(list!=null) {
                    for(EntryObject e: list) {
                        if(!e.isNotice()) {
                            showList.add(e);
                        }
                    }
                }
                ArrayList<EntryObject> empty_list = new ArrayList<>();
                if(showList!=null) {
                    group_eventAdapter = new EventAdapter(GroupHomeActivity.this, showList);
                    listView.setAdapter(group_eventAdapter);
                    listView.setOnItemClickListener(eventClickedHandler);
                }
                else{
                    group_eventAdapter = new EventAdapter(GroupHomeActivity.this, empty_list);
                    listView.setAdapter(group_eventAdapter);
                }
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                group_toolbar_month.setTitle(dateFormatForMonth.format(firstDayOfNewMonth));

            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void onNavigationMenuItemClick(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_home:
                Intent h = new Intent(GroupHomeActivity.this, Home.class);
                startActivity(h);
                break;
            case R.id.nav_mygroups:
                Intent g = new Intent(GroupHomeActivity.this, MyGroups.class);
                startActivity(g);
                break;
            case R.id.nav_settings:
                Intent s = new Intent(GroupHomeActivity.this, AccountSettings.class);
                startActivity(s);
                break;
            case R.id.nav_logout:
                Intent l= new Intent(GroupHomeActivity.this,LoginActivity.class);
                l.putExtra("logout", true);
                l.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(l);
                break;
        }

        mDrawerLayout = (DrawerLayout) findViewById(R.id.grouphomePageLayout);
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    public void onGroupNavigationMenuItemClick(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.group_nav_noticeBoard:
                Intent nb = new Intent(GroupHomeActivity.this, NoticeBoard.class);
                startActivity(nb);
                break;
            case R.id.group_nav_members:
                Intent gm = new Intent(GroupHomeActivity.this, MemberList.class);
                startActivity(gm);
                break;
            case R.id.group_nav_heatmap:
                if(MyGroups.currGroup.isAdmin()) {
                    Intent h = new Intent(GroupHomeActivity.this, Heatmap.class);
                    startActivity(h);
                }
                else {
                    Snackbar.make(findViewById(android.R.id.content), "You need to be an admin", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                break;
            case R.id.group_nav_contactAdmin:
                Intent ca= new Intent(GroupHomeActivity.this,ContactAdminActivity.class);
                startActivity(ca);
                break;
            case R.id.group_nav_groupSettings:
                if(MyGroups.currGroup.isAdmin()) {
                    Intent gs = new Intent(GroupHomeActivity.this, GroupSettings.class);
                    startActivity(gs);
                }
                else {
                    Snackbar.make(findViewById(android.R.id.content), "You need to be an admin", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                break;
            case R.id.group_nav_leaveGroup:
                AlertDialog.Builder builder = new AlertDialog.Builder(GroupHomeActivity.this);
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
                        Intent gohome = new Intent(GroupHomeActivity.this, Home.class);
                        gohome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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

        mDrawerLayout = (DrawerLayout) findViewById(R.id.grouphomePageLayout);
        mDrawerLayout.closeDrawer(GravityCompat.END);
    }

    public void onFilterMenuItemClick(MenuItem item) {
        Drawable icon = item.getIcon();

        Drawable checked = this.getResources().getDrawable(R.drawable.baseline_radio_button_checked_24);
        Drawable unchecked = this.getResources().getDrawable(R.drawable.baseline_radio_button_unchecked_24);

        // First time will always be false, but there is no problem as everything will be unchecked in the beginning.
        if (icon.getConstantState().equals(checked.getConstantState())) {
            item.setIcon(unchecked);
        } else {
            item.setIcon(checked);
        }
    }
}


