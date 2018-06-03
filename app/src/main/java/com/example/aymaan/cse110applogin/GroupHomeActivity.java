package com.example.aymaan.cse110applogin;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jeff.database_access.EntryObject;
import com.example.jeff.database_access.UserObject;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class GroupHomeActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private Toolbar group_toolbar;
    private ActionBar group_actionBar;
    private SimpleDateFormat dateFormatForDisplaying = new SimpleDateFormat("dd-M-yyyy hh:mm:ss a", Locale.getDefault());
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMM - yyyy", Locale.getDefault());
    private CompactCalendarView group_compactCalendarView;
    private EventAdapter group_eventAdapter;

    public static Date clickDate = null;
    //public UserObject user = Hashing.global_user;

    private AdapterView.OnItemClickListener eventClickedHandler = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            ListView l = (ListView)parent;
            EntryObject clickedItem = (EntryObject) l.getItemAtPosition(position);

            Bundle b = new Bundle();
            b.putLong("id",clickedItem.getId());
            b.putLong("group id",clickedItem.getGroupId());
            b.putString("event name",clickedItem.getTitle());
            b.putString("event start",EntryObject.getDayString(clickedItem.getStart()));
            b.putString("event end",EntryObject.getDayString(clickedItem.getEnd()));
            b.putString("event start time",EntryObject.getTimeString(clickedItem.getStart()));
            b.putString("event end time",EntryObject.getTimeString(clickedItem.getEnd()));
            b.putString("event description",clickedItem.getDescription());

            Intent ved = new Intent( GroupHomeActivity.this, ViewEventDetails.class);
            ved.putExtras(b);
            startActivity(ved);
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_home);

        group_toolbar = (Toolbar)findViewById(R.id.group_tool_bar);
        group_compactCalendarView = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        group_toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(group_toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);


        group_actionBar = (ActionBar)getSupportActionBar();

        group_actionBar.setTitle(dateFormatForMonth.format(group_compactCalendarView.getFirstDayOfCurrentMonth()));

        mDrawerLayout = (DrawerLayout) findViewById(R.id.grouphomePageLayout);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.group_fab);
        final ListView listView = (ListView) findViewById(R.id.group_home_list);

        Map<String, ArrayList<EntryObject>> GroupEntryMap = MyGroups.currGroup.getEntryMap();
        if(GroupEntryMap!= null) {
            //toolbar.setTitle("Man");
            for(String s: GroupEntryMap.keySet()) {
                Date date = EntryObject.getDayDateFromString(s);
                for(int i=0; i<GroupEntryMap.get(s).size(); i++) {
                    Event ev1 = new Event(Color.BLACK, date.getTime());
                    group_compactCalendarView.addEvent(ev1);
                }
            }
        }
        else {
            //Do Nothing
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent aea = new Intent(GroupHomeActivity.this, AddEventActivity.class);
                startActivity(aea);
            }
        });

        group_compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            //This should be on the group home as well
            //TODO
            @Override
            public void onDayClick(Date dateClicked) {
                clickDate = dateClicked;
                Map<String, ArrayList<EntryObject>> EntryMap = LoginActivity.userLogin.getEntryMap();
                String date = EntryObject.getDayString(dateClicked);
                ArrayList<EntryObject> list = EntryMap.get(date);
                ArrayList<EntryObject> empty_list = new ArrayList<>();
                if(list!=null) {
                    group_eventAdapter = new EventAdapter(GroupHomeActivity.this, list);
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
                group_toolbar.setTitle(dateFormatForMonth.format(firstDayOfNewMonth));

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
                startActivity(l);
                break;
        }

        mDrawerLayout = (DrawerLayout) findViewById(R.id.homePageLayout);
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
                Intent h = new Intent(GroupHomeActivity.this, Heatmap.class);
                startActivity(h);
                break;
            case R.id.group_nav_contactAdmin:
                Intent ca= new Intent(GroupHomeActivity.this,NoticeBoard.class);
                startActivity(ca);
                break;
            case R.id.group_nav_relatedGroups:
                Intent rg = new Intent(GroupHomeActivity.this, RelatedGroups.class);
                startActivity(rg);
                break;
            case R.id.group_nav_leaveGroup:
                break;
        }

        mDrawerLayout = (DrawerLayout) findViewById(R.id.homePageLayout);
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


