package com.example.aymaan.cse110applogin;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class EditTaskEvent extends AppCompatActivity {

    private static final String TAG = "EditTaskEventFragment";

    ImageButton editibClose;

    private SectionsPageAdapter mSectionPageAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task_event);

        editibClose = (ImageButton) findViewById(R.id.editibClose);

        mSectionPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.editcontainer);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.edittabs);
        tabLayout.setupWithViewPager(mViewPager);

        editibClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditTaskEvent.this.finish();
            }
        });
    }
    //public void onBackPressed(){ }

    private void setupViewPager(ViewPager viewPager){
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        EditEventFragment newEvent = new EditEventFragment();
        EditTaskFragment newTask = new EditTaskFragment();
        newEvent.setArguments(getIntent().getExtras());
        newTask.setArguments(getIntent().getExtras());
        adapter.addFragment(newEvent,"Add Event");
        adapter.addFragment(newTask,"Add Task");
        viewPager.setAdapter(adapter);
    }
}
