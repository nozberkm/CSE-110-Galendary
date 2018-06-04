package com.example.aymaan.cse110applogin;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class GroupAddTaskEventActivity extends AppCompatActivity {

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
                GroupAddTaskEventActivity.this.finish();
            }
        });
    }
    public void onBackPressed(){
    }

    private void setupViewPager(ViewPager viewPager){
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new AddEventFragment(),"Add Event");
        adapter.addFragment(new AddTaskFragment(),"Add Task");
        viewPager.setAdapter(adapter);
    }
}
