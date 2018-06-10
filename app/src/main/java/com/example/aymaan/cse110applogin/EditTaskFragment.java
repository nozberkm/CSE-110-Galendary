package com.example.aymaan.cse110applogin;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class EditTaskFragment extends Fragment {

    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
    private static final String TAG = "AddTaskFragment";

    private EditText editTaskTitle;

    private TextView edittvTaskEndDate;
    private TextView edittvTaskEndTime;

    private EditText editetTaskDescription;

    private FloatingActionButton editfabAddTask;
    private String endDateObject;
    private DatePickerDialog.OnDateSetListener endTaskDateListener;
    private TimePickerDialog.OnTimeSetListener endTaskTimeListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_edit_task_fragment, container, false);

        editTaskTitle = (EditText) view.findViewById(R.id.editTaskTitle);

        edittvTaskEndDate = (TextView) view.findViewById(R.id.edittvTaskEndDate);
        edittvTaskEndTime = (TextView) view.findViewById(R.id.edittvTaskEndTime);

        editetTaskDescription = (EditText) view.findViewById(R.id.editetTaskDescription);

        editfabAddTask = (FloatingActionButton) view.findViewById(R.id.editfabAddTask);

        edittvTaskEndDate.setText(DATE_FORMAT.format((new Date(getArguments().getLong("end date")))));
        editTaskTitle.setText(getArguments().getString("event name"));
        editetTaskDescription.setText(getArguments().getString("event description"));
        final String previous = getArguments().getString("previous");

        edittvTaskEndDate.setOnClickListener(new View.OnClickListener() {
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
                    edittvTaskEndDate.setText("0"+month+"/"+ "0"+ dayOfMonth+"/"+year);
                }
                else if(month<10){
                    edittvTaskEndDate.setText("0"+month+"/"+dayOfMonth+"/"+year);
                }
                else if(dayOfMonth<10) {
                    edittvTaskEndDate.setText(month+"/0"+dayOfMonth+"/"+year);
                }
                else {
                    edittvTaskEndDate.setText(month+"/"+dayOfMonth+"/"+year);
                }
            }
        };

        edittvTaskEndTime.setOnClickListener(new View.OnClickListener() {
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
                    edittvTaskEndTime.setText("0" + Integer.toString(hourOfDay) + ":" + "0" + Integer.toString(minute));
                else if (minute < 10)
                    edittvTaskEndTime.setText(Integer.toString(hourOfDay) + ":" + "0" + Integer.toString(minute));
                else if (hourOfDay < 10)
                    edittvTaskEndTime.setText("0" + Integer.toString(hourOfDay) + ":" + Integer.toString(minute));
                else
                    edittvTaskEndTime.setText(Integer.toString(hourOfDay) + ":" + Integer.toString(minute));
            }
        };

        editfabAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                endDateObject = edittvTaskEndDate.getText().toString() + " " + edittvTaskEndTime.getText().toString();
                SimpleDateFormat formatter1=new SimpleDateFormat("MM/dd/yyyy HH:mm");

                try {
                    System.err.println("gets into try");
                    Date end_date=formatter1.parse(endDateObject);

                    if(previous.equals("Home")){
                        System.err.println("getting into home");
                        Home.currentEvent.setDescription(editetTaskDescription.getText().toString());
                        Home.currentEvent.setTitle(editTaskTitle.getText().toString());
                        Home.currentEvent.setStart(null);
                        Home.currentEvent.setEnd(end_date);

                        boolean status = Home.currentEvent.pushUpdate();
                        System.err.println("status = " + status);
                        Intent toHome = new Intent(getActivity(), Home.class);
                        startActivity(toHome);
                    }
                    else if(previous.equals("groupHome")){
                        GroupHomeActivity.currentGroupEvent.setDescription(editetTaskDescription.getText().toString());
                        GroupHomeActivity.currentGroupEvent.setTitle(editTaskTitle.getText().toString());
                        GroupHomeActivity.currentGroupEvent.setStart(null);
                        GroupHomeActivity.currentGroupEvent.setEnd(end_date);
                        boolean status = GroupHomeActivity.currentGroupEvent.pushUpdate();
                        System.err.println("status = " + status);
                        Intent toHome = new Intent(getActivity(), GroupHomeActivity.class);
                        startActivity(toHome);
                    }
                }
                catch(Exception e) {

                }

            }
        });

        return view;
    }
}
