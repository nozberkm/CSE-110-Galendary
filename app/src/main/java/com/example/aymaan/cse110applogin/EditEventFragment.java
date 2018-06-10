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

public class EditEventFragment extends Fragment {

    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
    private static final String TAG = "AddEventFragment";

    private EditText editEventTitle;

    private TextView edittvEventStartDate;
    private TextView edittvEventStartTime;

    private TextView edittvEventEndDate;
    private TextView edittvEventEndTime;

    //private EditText editetEventLocation;

    private EditText editetEventDescription;

    private FloatingActionButton editfabAddEvent;

    private DatePickerDialog.OnDateSetListener startEventDateListener;
    private DatePickerDialog.OnDateSetListener endEventDateListener;
    private TimePickerDialog.OnTimeSetListener startEventTimeListener;
    private TimePickerDialog.OnTimeSetListener endEventTimeListener;
    private String startDateObject;
    private String endDateObject;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_event_fragment, container, false);

        editEventTitle = (EditText) view.findViewById(R.id.etEventTitle);

        edittvEventStartDate = (TextView) view.findViewById(R.id.tvEventStartDate);
        edittvEventStartTime = (TextView) view.findViewById(R.id.tvEventStartTime);

        edittvEventEndDate = (TextView) view.findViewById(R.id.tvEventEndDate);
        edittvEventEndTime = (TextView) view.findViewById(R.id.tvEventEndTime);

        //editetEventLocation = (EditText) view.findViewById(R.id.etEventLocation);

        editetEventDescription = (EditText) view.findViewById(R.id.etEventDescription);

        editfabAddEvent = (FloatingActionButton) view.findViewById(R.id.fabAddEvent);

        edittvEventStartDate.setText(DATE_FORMAT.format(new Date(getArguments().getLong("start date"))));
        edittvEventEndDate.setText(DATE_FORMAT.format((new Date(getArguments().getLong("end date")))));
        editEventTitle.setText(getArguments().getString("event name"));
        editetEventDescription.setText(getArguments().getString("event description"));
        final String previous = getArguments().getString("previous");

        edittvEventStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get (Calendar.YEAR);
                int month = cal.get (Calendar.MONTH);
                int day = cal.get (Calendar.DAY_OF_MONTH);

                DatePickerDialog startDateDialog = new DatePickerDialog(
                        getContext(),
                        startEventDateListener,
                        year, month, day);

                startDateDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                startDateDialog.show();
            }
        });

        startEventDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                //tvEventStartDate.setText(month+"/"+dayOfMonth+"/"+year);
                if(month<10 & dayOfMonth<10) {
                    edittvEventStartDate.setText("0"+month+"/"+ "0"+ dayOfMonth+"/"+year);
                }
                else if(month<10){
                    edittvEventStartDate.setText("0"+month+"/"+dayOfMonth+"/"+year);
                }
                else if(dayOfMonth<10) {
                    edittvEventStartDate.setText(month+"/0"+dayOfMonth+"/"+year);
                }
                else {
                    edittvEventStartDate.setText(month+"/"+dayOfMonth+"/"+year);
                }
            }
        };

        edittvEventEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get (Calendar.YEAR);
                int month = cal.get (Calendar.MONTH);
                int day = cal.get (Calendar.DAY_OF_MONTH);

                DatePickerDialog endDateDialog = new DatePickerDialog(
                        getContext(),
                        endEventDateListener,
                        year, month, day);

                endDateDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                endDateDialog.show();
            }
        });

        endEventDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                //tvEventEndDate.setText(month+"/"+dayOfMonth+"/"+year);
                if(month<10 & dayOfMonth<10) {
                    edittvEventEndDate.setText("0"+month+"/"+ "0"+ dayOfMonth+"/"+year);
                }
                else if(month<10){
                    edittvEventEndDate.setText("0"+month+"/"+dayOfMonth+"/"+year);
                }
                else if(dayOfMonth<10) {
                    edittvEventEndDate.setText(month+"/0"+dayOfMonth+"/"+year);
                }
                else {
                    edittvEventEndDate.setText(month+"/"+dayOfMonth+"/"+year);
                }
            }
        };

        edittvEventStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int hour = cal.get (Calendar.HOUR_OF_DAY);
                int minute = cal.get (Calendar.MINUTE);

                TimePickerDialog endTimeDialog = new TimePickerDialog(
                        getContext(),
                        startEventTimeListener,
                        hour, minute, true
                );

                endTimeDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                endTimeDialog.show();
            }
        });

        startEventTimeListener = new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet (TimePicker view, int hourOfDay, int minute){
                if (hourOfDay < 10 && minute < 10)
                    edittvEventStartTime.setText("0" + Integer.toString(hourOfDay) + ":" + "0" + Integer.toString(minute));
                else if (minute < 10)
                    edittvEventStartTime.setText(Integer.toString(hourOfDay) + ":" + "0" + Integer.toString(minute));
                else if (hourOfDay < 10)
                    edittvEventStartTime.setText("0" + Integer.toString(hourOfDay) + ":" + Integer.toString(minute));
                else
                    edittvEventStartTime.setText(Integer.toString(hourOfDay) + ":" + Integer.toString(minute));
            }
        };

        edittvEventEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int hour = cal.get (Calendar.HOUR_OF_DAY);
                int minute = cal.get (Calendar.MINUTE);

                TimePickerDialog endTimeDialog = new TimePickerDialog(
                        getContext(),
                        endEventTimeListener,
                        hour, minute, true
                );

                endTimeDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                endTimeDialog.show();
            }
        });

        endEventTimeListener = new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet (TimePicker view, int hourOfDay, int minute){
                if (hourOfDay < 10 && minute < 10)
                    edittvEventEndTime.setText("0" + Integer.toString(hourOfDay) + ":" + "0" + Integer.toString(minute));
                else if (minute < 10)
                    edittvEventEndTime.setText(Integer.toString(hourOfDay) + ":" + "0" + Integer.toString(minute));
                else if (hourOfDay < 10)
                    edittvEventEndTime.setText("0" + Integer.toString(hourOfDay) + ":" + Integer.toString(minute));
                else
                    edittvEventEndTime.setText(Integer.toString(hourOfDay) + ":" + Integer.toString(minute));
            }
        };

        editfabAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                endDateObject = edittvEventEndDate.getText().toString() + " " + edittvEventEndTime.getText().toString();
                startDateObject = edittvEventStartDate.getText().toString() + " " + edittvEventStartTime.getText().toString();
                SimpleDateFormat formatter1=new SimpleDateFormat("MM/dd/yyyy HH:mm");

                try {
                    Date start_date=formatter1.parse(startDateObject);
                    Date end_date=formatter1.parse(endDateObject);

                    if(previous.equals("Home")){
                        Home.currentEvent.setDescription(editetEventDescription.getText().toString());
                        Home.currentEvent.setTitle(editEventTitle.getText().toString());
                        Home.currentEvent.setStart(start_date);
                        Home.currentEvent.setEnd(end_date);
                        Home.currentEvent.pushUpdate();
                        Intent toHome = new Intent(getActivity(), Home.class);
                        startActivity(toHome);
                    }
                    else if(previous.equals("groupHome")){
                        GroupHomeActivity.currentGroupEvent.setDescription(editetEventDescription.getText().toString());
                        GroupHomeActivity.currentGroupEvent.setTitle(editEventTitle.getText().toString());
                        GroupHomeActivity.currentGroupEvent.setStart(start_date);
                        GroupHomeActivity.currentGroupEvent.setEnd(end_date);
                        GroupHomeActivity.currentGroupEvent.pushUpdate();
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
