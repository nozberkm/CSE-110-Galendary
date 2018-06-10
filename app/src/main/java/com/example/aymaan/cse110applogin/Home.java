package com.example.aymaan.cse110applogin;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.jeff.database_access.EntryObject;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class Home extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private Toolbar toolbar;
    private ActionBar actionBar;
    private SimpleDateFormat dateFormatForDisplaying = new SimpleDateFormat("dd-M-yyyy hh:mm:ss a", Locale.getDefault());
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMM - yyyy", Locale.getDefault());
    private CompactCalendarView compactCalendarView;
    private EventAdapter eventAdapter;

    public static EntryObject currentEvent;

    @Override
    public void onBackPressed(){}
    private AdapterView.OnItemClickListener eventClickedHandler = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            ListView l = (ListView)parent;
            EntryObject clickedItem = (EntryObject) l.getItemAtPosition(position);

            Bundle b = new Bundle();
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
            b.putString("event description",clickedItem.getDescription());
            b.putString("previous", "Home");
            try {
                if(clickedItem.getGroupName().equals("(individual group)")){
                    b.putString("group name", "Personal");
                }
                else{
                    b.putString("group name", clickedItem.getGroupName());
                }
            }
            catch (Exception e){
                b.putString("group name", "WTF");
            }
            Home.currentEvent = clickedItem;
            Intent ved = new Intent( Home.this, ViewEventDetails.class);
            ved.putExtras(b);
            startActivity(ved);
            //finish();
        }
    };
    public static Date clickDate = null;
    //public UserObject user = Hashing.global_user;
    //public void onBackPressed(){}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        clickDate = null;

        toolbar = (Toolbar)findViewById(R.id.tool_bar);
        compactCalendarView = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        LoginActivity.userLogin.synchronize();

        actionBar = (ActionBar)getSupportActionBar();

        actionBar.setTitle(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));

        mDrawerLayout = (DrawerLayout) findViewById(R.id.homePageLayout);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        final ListView listView = (ListView) findViewById(R.id.home_list);

        Map<String, ArrayList<EntryObject>> EntryMap = LoginActivity.userLogin.getEntryMap();
        if(EntryMap!= null) {
            //toolbar.setTitle("Man");
            for(String s: EntryMap.keySet()) {
                Date date = EntryObject.getDayDateFromString(s);
                if(date == null) continue;
                for(int i=0; i<EntryMap.get(s).size(); i++) {
                    if (!EntryMap.get(s).get(i).isNotice()) {
                        Event ev1 = new Event(Color.BLACK, date.getTime());
                        compactCalendarView.addEvent(ev1);
                    }
                }
            }
        }
        else {
            //Do Nothing
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                if(clickDate == null) {
                    b.putLong("date",Calendar.getInstance().getTimeInMillis());
                }
                else {
                    b.putLong("date",clickDate.getTime());
                }
                Intent aea = new Intent(Home.this, AddTaskEventActivity.class);
                aea.putExtras(b);
                startActivity(aea);
            }
        });

        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            //This should be on the group home as well
            //TODO
            @Override
            public void onDayClick(Date dateClicked) {
                clickDate = dateClicked;
                Map<String, ArrayList<EntryObject>> EntryMap = LoginActivity.userLogin.getEntryMap();
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
                    eventAdapter = new EventAdapter(Home.this, showList);
                    listView.setAdapter(eventAdapter);
                    listView.setOnItemClickListener(eventClickedHandler);
                }
                else{
                    eventAdapter = new EventAdapter(Home.this, empty_list);
                    listView.setAdapter(eventAdapter);
                }
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                toolbar.setTitle(dateFormatForMonth.format(firstDayOfNewMonth));

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
                Intent h = new Intent(Home.this, Home.class);
                startActivity(h);
                break;
            case R.id.nav_mygroups:
                Intent g = new Intent(Home.this, MyGroups.class);
                startActivity(g);
                break;
            case R.id.nav_settings:
                Intent s = new Intent(Home.this, AccountSettings.class);
                startActivity(s);
                break;
            case R.id.nav_logout:
                Intent l= new Intent(Home.this,LoginActivity.class);
                l.putExtra("logout", true);
                startActivity(l);
                finish();
                break;
        }

        mDrawerLayout = (DrawerLayout) findViewById(R.id.homePageLayout);
        mDrawerLayout.closeDrawer(GravityCompat.START);
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
