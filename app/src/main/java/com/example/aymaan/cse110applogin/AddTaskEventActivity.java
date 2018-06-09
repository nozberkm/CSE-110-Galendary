package com.example.aymaan.cse110applogin;

import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageButton;
import android.widget.TextView;

public class AddTaskEventActivity extends AppCompatActivity {

    private static final String TAG = "AddTaskEventFragment";

    ImageButton ibClose;

    private SectionsPageAdapter mSectionPageAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task_event);

        ibClose = (ImageButton) findViewById(R.id.ibClose);

        mSectionPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        ibClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddTaskEventActivity.this.finish();
            }
        });
    }
    //public void onBackPressed(){ }

    private void setupViewPager(ViewPager viewPager){
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        AddEventFragment newEvent = new AddEventFragment();
        AddTaskFragment newTask = new AddTaskFragment();
        newEvent.setArguments(getIntent().getExtras());
        newTask.setArguments(getIntent().getExtras());
        adapter.addFragment(newEvent,"Add Event");
        adapter.addFragment(newTask,"Add Task");
        viewPager.setAdapter(adapter);
    }
}
