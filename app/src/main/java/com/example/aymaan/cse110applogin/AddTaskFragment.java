package com.example.aymaan.cse110applogin;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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

import java.util.Calendar;

public class AddTaskFragment extends Fragment {
    private static final String TAG = "AddTaskFragment";

    private EditText etTaskTitle;

    private TextView tvTaskEndDate;
    private TextView tvTaskEndTime;

    private EditText etTaskDescription;

    private FloatingActionButton fabAddTask;

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
                tvTaskEndDate.setText(month+"/"+dayOfMonth+"/"+year);
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

            }
        });

        return view;
    }
}