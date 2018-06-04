package com.example.aymaan.cse110applogin;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.jeff.database_access.EntryObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddTaskFragment extends Fragment {
    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
    private static final String TAG = "AddTaskFragment";

    private EditText etTaskTitle;

    private TextView tvTaskEndDate;
    private TextView tvTaskEndTime;

    private EditText etTaskDescription;

    private FloatingActionButton fabAddTask;
    private String endDateObject;
    private DatePickerDialog.OnDateSetListener endTaskDateListener;
    private TimePickerDialog.OnTimeSetListener endTaskTimeListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_task_fragment, container, false);

        etTaskTitle = (EditText) view.findViewById(R.id.etTaskTitle);

        tvTaskEndDate = (TextView) view.findViewById(R.id.tvTaskEndDate);
        tvTaskEndTime = (TextView) view.findViewById(R.id.tvTaskEndTime);

        etTaskDescription = (EditText) view.findViewById(R.id.etTaskDescription);

        fabAddTask = (FloatingActionButton) view.findViewById(R.id.fabAddTask);

        tvTaskEndDate.setText(DATE_FORMAT.format(new Date(getArguments().getLong("date"))));

        tvTaskEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get (Calendar.YEAR);
                int month = cal.get (Calendar.MONTH);
                int day = cal.get (Calendar.DAY_OF_MONTH);

                DatePickerDialog endDateDialog = new DatePickerDialog(
                        getContext(),
                        endTaskDateListener,
                        year, month, day);

                endDateDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                endDateDialog.show();
            }
        });

        endTaskDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                //tvTaskEndDate.setText(month+"/"+dayOfMonth+"/"+year);
                if(month<10 & dayOfMonth<10) {
                    tvTaskEndDate.setText("0"+month+"/"+ "0"+ dayOfMonth+"/"+year);
                }
                else if(month<10){
                    tvTaskEndDate.setText("0"+month+"/"+dayOfMonth+"/"+year);
                }
                else if(dayOfMonth<10) {
                    tvTaskEndDate.setText(month+"/0"+dayOfMonth+"/"+year);
                }
                else {
                    tvTaskEndDate.setText(month+"/"+dayOfMonth+"/"+year);
                }
            }
        };

        tvTaskEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int hour = cal.get (Calendar.HOUR_OF_DAY);
                int minute = cal.get (Calendar.MINUTE);

                TimePickerDialog endTimeDialog = new TimePickerDialog(
                        getContext(),
                        endTaskTimeListener,
                        hour, minute, true
                );

                endTimeDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                endTimeDialog.show();
            }
        });

        endTaskTimeListener = new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet (TimePicker view, int hourOfDay, int minute){
                if (hourOfDay < 10 && minute < 10)
                    tvTaskEndTime.setText("0" + Integer.toString(hourOfDay) + ":" + "0" + Integer.toString(minute));
                else if (minute < 10)
                    tvTaskEndTime.setText(Integer.toString(hourOfDay) + ":" + "0" + Integer.toString(minute));
                else if (hourOfDay < 10)
                    tvTaskEndTime.setText("0" + Integer.toString(hourOfDay) + ":" + Integer.toString(minute));
                else
                    tvTaskEndTime.setText(Integer.toString(hourOfDay) + ":" + Integer.toString(minute));
            }
        };

        fabAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                endDateObject = tvTaskEndDate.getText().toString() + " " + tvTaskEndTime.getText().toString();
                SimpleDateFormat formatter1=new SimpleDateFormat("MM/dd/yyyy HH:mm");

                try {
                    Date end_date=formatter1.parse(endDateObject);
                    EntryObject entryObject = new EntryObject();
                    entryObject.setDescription(etTaskDescription.getText().toString());
                    entryObject.setTitle(etTaskTitle.getText().toString());
                    entryObject.setEnd(end_date);
                    LoginActivity.userLogin.getIndividualGroup().pushEntry(entryObject);
                    LoginActivity.userLogin.synchronize();
                }
                catch(Exception e) {

                }

                Intent toHome = new Intent(getActivity(), Home.class);
                startActivity(toHome);

            }
        });

        return view;
    }
}