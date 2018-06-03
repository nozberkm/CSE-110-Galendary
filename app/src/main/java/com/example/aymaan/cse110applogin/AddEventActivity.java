package com.example.aymaan.cse110applogin;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddEventActivity extends AppCompatActivity {

    private EditText tvEventTitle;

    private ImageButton ibCloseAddEvent;
    private ImageButton ibAddEvent;

    private TextView tvStartDate;
    private TextView tvStartTime;

    private TextView tvEndDate;
    private TextView tvEndTime;

    private EditText etDescription;

    private DatePickerDialog.OnDateSetListener startDateListener;
    private DatePickerDialog.OnDateSetListener endDateListener;
    private TimePickerDialog.OnTimeSetListener startTimeListener;
    private TimePickerDialog.OnTimeSetListener endTimeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        EditText tvEventTitle = (EditText) findViewById(R.id.tvEventTitle);

        ImageButton ibCloseAddEvent = (ImageButton) findViewById(R.id.ibCloseAddEvent);
        ImageButton ibAddEvent = (ImageButton) findViewById(R.id.ibAddEvent);

        final TextView tvStartDate = (TextView) findViewById(R.id.tvStartDate);
        TextView tvStartTime = (TextView) findViewById(R.id.tvStartTime);

        TextView tvEndDate = (TextView) findViewById(R.id.tvEndDate);
        TextView tvEndTime = (TextView) findViewById(R.id.tvEndTime);

        EditText etDescription = (EditText) findViewById(R.id.etDescription);

        tvStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get (Calendar.YEAR);
                int month = cal.get (Calendar.MONTH);
                int day = cal.get (Calendar.DAY_OF_MONTH);

                DatePickerDialog startDateDialog = new DatePickerDialog(
                        AddEventActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        startDateListener,
                        year, month, day);

                startDateDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                startDateDialog.show();
            }
        });

        startDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                tvStartDate.setText(month+"/"+dayOfMonth+"/"+year);
            }
        };
    }
}
