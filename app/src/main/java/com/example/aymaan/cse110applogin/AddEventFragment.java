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
import android.widget.ImageButton;
import android.widget.TextView;
import android.support.v7.app.AppCompatActivity;
import android.widget.TimePicker;

import com.example.jeff.database_access.EntryObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddEventFragment extends Fragment {
    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
    private static final String TAG = "AddEventFragment";

    private EditText etEventTitle;

    private TextView tvEventStartDate;
    private TextView tvEventStartTime;

    private TextView tvEventEndDate;
    private TextView tvEventEndTime;

    private EditText etEventLocation;

    private EditText etEventDescription;

    private FloatingActionButton fabAddEvent;

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

        etEventTitle = (EditText) view.findViewById(R.id.etEventTitle);

        tvEventStartDate = (TextView) view.findViewById(R.id.tvEventStartDate);
        tvEventStartTime = (TextView) view.findViewById(R.id.tvEventStartTime);

        tvEventEndDate = (TextView) view.findViewById(R.id.tvEventEndDate);
        tvEventEndTime = (TextView) view.findViewById(R.id.tvEventEndTime);

        etEventLocation = (EditText) view.findViewById(R.id.etEventLocation);

        etEventDescription = (EditText) view.findViewById(R.id.etEventDescription);

        fabAddEvent = (FloatingActionButton) view.findViewById(R.id.fabAddEvent);

        tvEventStartDate.setText(DATE_FORMAT.format(new Date(getArguments().getLong("date"))));

        tvEventStartDate.setOnClickListener(new View.OnClickListener() {
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
                    tvEventStartDate.setText("0"+month+"/"+ "0"+ dayOfMonth+"/"+year);
                }
                else if(month<10){
                    tvEventStartDate.setText("0"+month+"/"+dayOfMonth+"/"+year);
                }
                else if(dayOfMonth<10) {
                    tvEventStartDate.setText(month+"/0"+dayOfMonth+"/"+year);
                }
                else {
                    tvEventStartDate.setText(month+"/"+dayOfMonth+"/"+year);
                }
            }
        };

        tvEventEndDate.setOnClickListener(new View.OnClickListener() {
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
                    tvEventEndDate.setText("0"+month+"/"+ "0"+ dayOfMonth+"/"+year);
                }
                else if(month<10){
                    tvEventEndDate.setText("0"+month+"/"+dayOfMonth+"/"+year);
                }
                else if(dayOfMonth<10) {
                    tvEventEndDate.setText(month+"/0"+dayOfMonth+"/"+year);
                }
                else {
                    tvEventEndDate.setText(month+"/"+dayOfMonth+"/"+year);
                }
            }
        };

        tvEventStartTime.setOnClickListener(new View.OnClickListener() {
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
                    tvEventStartTime.setText("0" + Integer.toString(hourOfDay) + ":" + "0" + Integer.toString(minute));
                else if (minute < 10)
                    tvEventStartTime.setText(Integer.toString(hourOfDay) + ":" + "0" + Integer.toString(minute));
                else if (hourOfDay < 10)
                    tvEventStartTime.setText("0" + Integer.toString(hourOfDay) + ":" + Integer.toString(minute));
                else
                    tvEventStartTime.setText(Integer.toString(hourOfDay) + ":" + Integer.toString(minute));
            }
        };

        tvEventEndTime.setOnClickListener(new View.OnClickListener() {
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
                    tvEventEndTime.setText("0" + Integer.toString(hourOfDay) + ":" + "0" + Integer.toString(minute));
                else if (minute < 10)
                    tvEventEndTime.setText(Integer.toString(hourOfDay) + ":" + "0" + Integer.toString(minute));
                else if (hourOfDay < 10)
                    tvEventEndTime.setText("0" + Integer.toString(hourOfDay) + ":" + Integer.toString(minute));
                else
                    tvEventEndTime.setText(Integer.toString(hourOfDay) + ":" + Integer.toString(minute));
            }
        };

        fabAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                endDateObject = tvEventEndDate.getText().toString() + " " + tvEventEndTime.getText().toString();
                startDateObject = tvEventStartDate.getText().toString() + " " + tvEventStartTime.getText().toString();
                SimpleDateFormat formatter1=new SimpleDateFormat("MM/dd/yyyy HH:mm");

                try {
                    Date start_date=formatter1.parse(startDateObject);
                    Date end_date=formatter1.parse(endDateObject);

                    EntryObject entryObject = new EntryObject();
                    entryObject.setDescription(etEventDescription.getText().toString() +
                                                "\n" + "Location: " + etEventLocation.getText().toString());
                    entryObject.setTitle(etEventTitle.getText().toString());
                    entryObject.setStart(start_date);
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
