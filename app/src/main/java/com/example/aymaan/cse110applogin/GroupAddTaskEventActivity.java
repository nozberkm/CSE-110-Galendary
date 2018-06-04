package com.example.aymaan.cse110applogin;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class GroupAddTaskEventActivity extends AppCompatActivity {

    private static final String TAG = "GroupAddTaskEventFragment";

    ImageButton ibClose;

    private SectionsPageAdapter mGroupSectionPageAdapter;
    private ViewPager mGroupViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_add_task_event);

        ibClose = (ImageButton) findViewById(R.id.group_ibClose);

        mGroupSectionPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        mGroupViewPager = (ViewPager) findViewById(R.id.group_container);
        setupViewPager(mGroupViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.group_tabs);
        tabLayout.setupWithViewPager(mGroupViewPager);

        ibClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroupAddTaskEventActivity.this.finish();
            }
        });
    }
    //public void onBackPressed(){ }

    private void setupViewPager(ViewPager viewPager){
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        GroupAddEventFragment newGroupEvent = new GroupAddEventFragment();
        GroupAddTaskFragment newGroupTask = new GroupAddTaskFragment();
        newGroupEvent.setArguments(getIntent().getExtras());
        newGroupTask.setArguments(getIntent().getExtras());
        adapter.addFragment(newGroupEvent,"Add Group Event");
        adapter.addFragment(newGroupTask,"Add Group Task");
        viewPager.setAdapter(adapter);
    }
}
