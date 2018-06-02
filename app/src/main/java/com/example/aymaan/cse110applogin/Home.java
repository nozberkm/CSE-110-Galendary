package com.example.aymaan.cse110applogin;

import android.content.Intent;
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
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Home extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private Toolbar toolbar;
    private ActionBar actionBar;
    private SimpleDateFormat dateFormatForDisplaying = new SimpleDateFormat("dd-M-yyyy hh:mm:ss a", Locale.getDefault());
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMM - yyyy", Locale.getDefault());
    private CompactCalendarView compactCalendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = (Toolbar)findViewById(R.id.tool_bar);
        compactCalendarView = (CompactCalendarView) findViewById(R.id.compactcalendar_view);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        actionBar = (ActionBar)getSupportActionBar();

        actionBar.setTitle(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));

        mDrawerLayout = (DrawerLayout) findViewById(R.id.homePageLayout);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        final ListView listView = (ListView) findViewById(R.id.home_list);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Intent to EventActivity", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                
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
            case R.id.nav_logout:
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
