package com.example.aymaan.cse110applogin;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.view.View;

import java.text.CollationElementIterator;
import java.util.*;
import android.widget.TextView;

import com.example.jeff.database_access.EntryObject;
import com.example.jeff.database_access.GroupObject;
import com.example.jeff.database_access.UserObject;

import org.json.JSONException;

import java.io.IOException;
import java.security.acl.Group;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.EventObject;
import java.util.Map;

import static com.example.aymaan.cse110applogin.GroupHomeActivity.clickDate;



public class Heatmap extends AppCompatActivity {
    private TextView[][] idArray;
    private int[][] peopleCountArray;
    private int iX, jX;
    private GroupObject group;
    private ArrayList<UserObject> groupMembers;
    private int groupMemberCount;
    private ArrayList<Pair<Long, EntryObject>> groupEvents;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.heatmap);
        peopleCountArray = new int[19][7];
        this.group = MyGroups.currGroup;
        this.groupMembers = this.group.loadMembers();
        this.groupMemberCount = this.groupMembers.size();

        this.groupEvents = this.group.getMemberEntries();

        Collections.sort(this.groupEvents, new Comparator<Pair<Long, EntryObject> >() {
            @Override
            public int compare(Pair<Long, EntryObject> lhs, Pair<Long, EntryObject> rhs) {
                // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                return lhs.first > rhs.first ? -1 : (lhs.first < rhs.first) ? 1 : 0;
            }
        });


        idArray = new TextView[19][7];
        idArray[0][0] = findViewById(R.id.m6);
        idArray[1][0] = findViewById(R.id.m7);
        idArray[2][0] = findViewById(R.id.m8);
        idArray[3][0] = findViewById(R.id.m9);
        idArray[4][0] = findViewById(R.id.m10);
        idArray[5][0] = findViewById(R.id.m11);
        idArray[6][0] = findViewById(R.id.m12);
        idArray[7][0] = findViewById(R.id.m13);
        idArray[8][0] = findViewById(R.id.m14);
        idArray[9][0] = findViewById(R.id.m15);
        idArray[10][0] = findViewById(R.id.m16);
        idArray[11][0] = findViewById(R.id.m17);
        idArray[12][0] = findViewById(R.id.m18);
        idArray[13][0] = findViewById(R.id.m19);
        idArray[14][0] = findViewById(R.id.m20);
        idArray[15][0] = findViewById(R.id.m21);
        idArray[16][0] = findViewById(R.id.m22);
        idArray[17][0] = findViewById(R.id.m23);
        idArray[18][0] = findViewById(R.id.m0);

        idArray[0][1] = findViewById(R.id.t6);
        idArray[1][1] = findViewById(R.id.t7);
        idArray[2][1] = findViewById(R.id.t8);
        idArray[3][1] = findViewById(R.id.t9);
        idArray[4][1] = findViewById(R.id.t10);
        idArray[5][1] = findViewById(R.id.t11);
        idArray[6][1] = findViewById(R.id.t12);
        idArray[7][1] = findViewById(R.id.t13);
        idArray[8][1] = findViewById(R.id.t14);
        idArray[9][1] = findViewById(R.id.t15);
        idArray[10][1] = findViewById(R.id.t16);
        idArray[11][1] = findViewById(R.id.t17);
        idArray[12][1] = findViewById(R.id.t18);
        idArray[13][1] = findViewById(R.id.t19);
        idArray[14][1] = findViewById(R.id.t20);
        idArray[15][1] = findViewById(R.id.t21);
        idArray[16][1] = findViewById(R.id.t22);
        idArray[17][1] = findViewById(R.id.t23);
        idArray[18][1] = findViewById(R.id.t0);

        idArray[0][2] = findViewById(R.id.w6);
        idArray[1][2] = findViewById(R.id.w7);
        idArray[2][2] = findViewById(R.id.w8);
        idArray[3][2] = findViewById(R.id.w9);
        idArray[4][2] = findViewById(R.id.w10);
        idArray[5][2] = findViewById(R.id.w11);
        idArray[6][2] = findViewById(R.id.w12);
        idArray[7][2] = findViewById(R.id.w13);
        idArray[8][2] = findViewById(R.id.w14);
        idArray[9][2] = findViewById(R.id.w15);
        idArray[10][2] = findViewById(R.id.w16);
        idArray[11][2] = findViewById(R.id.w17);
        idArray[12][2] = findViewById(R.id.w18);
        idArray[13][2] = findViewById(R.id.w19);
        idArray[14][2] = findViewById(R.id.w20);
        idArray[15][2] = findViewById(R.id.w21);
        idArray[16][2] = findViewById(R.id.w22);
        idArray[17][2] = findViewById(R.id.w23);
        idArray[18][2] = findViewById(R.id.w0);

        idArray[0][3] = findViewById(R.id.th6);
        idArray[1][3] = findViewById(R.id.th7);
        idArray[2][3] = findViewById(R.id.th8);
        idArray[3][3] = findViewById(R.id.th9);
        idArray[4][3] = findViewById(R.id.th10);
        idArray[5][3] = findViewById(R.id.th11);
        idArray[6][3] = findViewById(R.id.th12);
        idArray[7][3] = findViewById(R.id.th13);
        idArray[8][3] = findViewById(R.id.th14);
        idArray[9][3] = findViewById(R.id.th15);
        idArray[10][3] = findViewById(R.id.th16);
        idArray[11][3] = findViewById(R.id.th17);
        idArray[12][3] = findViewById(R.id.th18);
        idArray[13][3] = findViewById(R.id.th19);
        idArray[14][3] = findViewById(R.id.th20);
        idArray[15][3] = findViewById(R.id.th21);
        idArray[16][3] = findViewById(R.id.th22);
        idArray[17][3] = findViewById(R.id.th23);
        idArray[18][3] = findViewById(R.id.th0);

        idArray[0][4] = findViewById(R.id.f6);
        idArray[1][4] = findViewById(R.id.f7);
        idArray[2][4] = findViewById(R.id.f8);
        idArray[3][4] = findViewById(R.id.f9);
        idArray[4][4] = findViewById(R.id.f10);
        idArray[5][4] = findViewById(R.id.f11);
        idArray[6][4] = findViewById(R.id.f12);
        idArray[7][4] = findViewById(R.id.f13);
        idArray[8][4] = findViewById(R.id.f14);
        idArray[9][4] = findViewById(R.id.f15);
        idArray[10][4] = findViewById(R.id.f16);
        idArray[11][4] = findViewById(R.id.f17);
        idArray[12][4] = findViewById(R.id.f18);
        idArray[13][4] = findViewById(R.id.f19);
        idArray[14][4] = findViewById(R.id.f20);
        idArray[15][4] = findViewById(R.id.f21);
        idArray[16][4] = findViewById(R.id.f22);
        idArray[17][4] = findViewById(R.id.f23);
        idArray[18][4] = findViewById(R.id.f0);

        idArray[0][5] = findViewById(R.id.st6);
        idArray[1][5] = findViewById(R.id.st7);
        idArray[2][5] = findViewById(R.id.st8);
        idArray[3][5] = findViewById(R.id.st9);
        idArray[4][5] = findViewById(R.id.st10);
        idArray[5][5] = findViewById(R.id.st11);
        idArray[6][5] = findViewById(R.id.st12);
        idArray[7][5] = findViewById(R.id.st13);
        idArray[8][5] = findViewById(R.id.st14);
        idArray[9][5] = findViewById(R.id.st15);
        idArray[10][5] = findViewById(R.id.st16);
        idArray[11][5] = findViewById(R.id.st17);
        idArray[12][5] = findViewById(R.id.st18);
        idArray[13][5] = findViewById(R.id.st19);
        idArray[14][5] = findViewById(R.id.st20);
        idArray[15][5] = findViewById(R.id.st21);
        idArray[16][5] = findViewById(R.id.st22);
        idArray[17][5] = findViewById(R.id.st23);
        idArray[18][5] = findViewById(R.id.st0);

        idArray[0][6] = findViewById(R.id.s6);
        idArray[1][6] = findViewById(R.id.s7);
        idArray[2][6] = findViewById(R.id.s8);
        idArray[3][6] = findViewById(R.id.s9);
        idArray[4][6] = findViewById(R.id.s10);
        idArray[5][6] = findViewById(R.id.s11);
        idArray[6][6] = findViewById(R.id.s12);
        idArray[7][6] = findViewById(R.id.s13);
        idArray[8][6] = findViewById(R.id.s14);
        idArray[9][6] = findViewById(R.id.s15);
        idArray[10][6] = findViewById(R.id.s16);
        idArray[11][6] = findViewById(R.id.s17);
        idArray[12][6] = findViewById(R.id.s18);
        idArray[13][6] = findViewById(R.id.s19);
        idArray[14][6] = findViewById(R.id.s20);
        idArray[15][6] = findViewById(R.id.s21);
        idArray[16][6] = findViewById(R.id.s22);
        idArray[17][6] = findViewById(R.id.s23);
        idArray[18][6] = findViewById(R.id.s0);

        colorGrid();
    }

    public void myMethod(View view) {

    }

    private void colorGrid() {
        boolean[][] checkAdded = new boolean[19][7];;
        Date mapDate;
        int daysForward = 0;
        int daysBackward = 0;
        int switchMonthNum = -1;

        if (clickDate != null)
            mapDate = clickDate;
        else
            mapDate = new Date();

        SimpleDateFormat day = new SimpleDateFormat("dd");
        SimpleDateFormat month = new SimpleDateFormat("MM");
        SimpleDateFormat year = new SimpleDateFormat("yyyy");
        SimpleDateFormat hour = new SimpleDateFormat("HH");
        String selectedDay = day.format(mapDate);
        String selectedMonth = month.format(mapDate);
        String selectedDayOfWeek = EntryObject.getDayOfWeek(mapDate);
        String selectedYear = year.format(mapDate);

        switch (selectedDayOfWeek) {
            case "Mon":
                daysBackward = 0;
                daysForward = 6;
            case "Tue":
                daysBackward = 1;
                daysForward = 5;
            case "Wed":
                daysBackward = 2;
                daysForward = 4;
            case "Thu":
                daysBackward = 3;
                daysForward = 3;
            case "Fri":
                daysBackward = 4;
                daysForward = 2;
            case "Sat":
                daysBackward = 5;
                daysForward = 1;
            case "Sun":
                daysBackward = 6;
                daysForward = 0;
        }

        int daysInMonth = getDaysOfMonth(selectedMonth, selectedYear);

        int selectedMonthNum = Integer.parseInt(selectedMonth);


        int selectedDayNum = Integer.parseInt(selectedDay);

        int switchYearNum = Integer.parseInt(selectedYear);

        if (selectedDayNum + daysForward > daysInMonth)
            switchMonthNum = Integer.parseInt(selectedMonth) + 1;

        else if (selectedDayNum - daysBackward < 1)
            switchMonthNum = Integer.parseInt(selectedMonth) - 1;
        else
            switchMonthNum = selectedMonthNum;

        int startYearNum = switchYearNum;
        if (switchMonthNum == 13) {
            switchMonthNum = 1;
            switchYearNum++;
        }
        if (switchMonthNum == 0) {
            switchMonthNum = 12;
            switchYearNum--;
        }

        int startDayNum = selectedDayNum - daysBackward;
        int endDayNum = selectedDayNum + daysForward;

        if (switchMonthNum < selectedMonthNum) {
            startDayNum = getDaysOfMonth(Integer.toString(switchMonthNum), selectedYear) + startDayNum;
        } else if (switchMonthNum > selectedMonthNum) {
            endDayNum = endDayNum - getDaysOfMonth(Integer.toString(selectedMonthNum), selectedYear);
        }
        boolean switchYear = false;
        if (switchYearNum != Integer.parseInt(selectedYear)) {
            switchYear = true;
        }


        Date specifiedDateEnd;
        Date specifiedDateStart;

        int selectedYearNum = Integer.parseInt(selectedYear);

        if (switchMonthNum < selectedMonthNum && !switchYear)
            specifiedDateStart = EntryObject.getDayDateFromString(startYearNum + "/" + switchMonthNum + "/" + startDayNum);
        else if (!switchYear)
            specifiedDateStart = EntryObject.getDayDateFromString(startYearNum + "/" + selectedMonthNum + "/" + startDayNum);
        else if (switchYearNum < selectedYearNum)
            specifiedDateStart = EntryObject.getDayDateFromString(switchYear + "/" + switchMonthNum + "/" + startDayNum);
        else
            specifiedDateStart = EntryObject.getDayDateFromString(selectedYearNum + "/" + selectedMonthNum + "/" + startDayNum);

        if (switchMonthNum < selectedMonthNum && !switchYear)
            specifiedDateEnd = EntryObject.getDayDateFromString(startYearNum + "/" + selectedMonthNum + "/" + endDayNum);
        else if (!switchYear)
            specifiedDateEnd = EntryObject.getDayDateFromString(startYearNum + "/" + switchMonthNum + "/" + endDayNum);
        else if (switchYearNum < selectedYearNum)
            specifiedDateEnd = EntryObject.getDayDateFromString(selectedYearNum + "/" + selectedMonthNum + "/" + endDayNum);
        else
            specifiedDateEnd = EntryObject.getDayDateFromString(switchYear + "/" + switchMonthNum+ "/" + endDayNum);

        Long currentPerson = this.groupEvents.get(0).first;
        EntryObject current;
        for (Pair<Long, EntryObject> entry : this.groupEvents) {
            if (entry.second.isTask())
                continue;
            if (!currentPerson.equals(entry.first)) {
                checkAdded = new boolean[19][7];
            }
            current = entry.second;
            if (current.getStart() == null || current.getEnd() == null)
                continue;
            Date currentDate = EntryObject.getDayDateFromString(EntryObject.getDayString(current.getStart()));
            if (currentDate.equals(specifiedDateEnd)
                    || currentDate.equals(specifiedDateStart)
                    || (currentDate.before(specifiedDateEnd) && currentDate.after(specifiedDateStart))) {
                if (!current.isTask()) {
                    String dayOfEvent = EntryObject.getDayOfWeek(current.getStart());
                    int startDayIndex = getDayIndex(dayOfEvent);
                    int endDayIndex = getDayIndex(EntryObject.getDayOfWeek(current.getEnd()));
                    String hourStart = hour.format(current.getStart());
                    String hourEnd = hour.format(current.getEnd());
                    int hourEndNum = Integer.parseInt(hourEnd);
                    int hourStartNum = Integer.parseInt(hourStart);


                    if (startDayIndex == endDayIndex) {
                        if (hourStartNum < 6 && hourEndNum > 6) {
                            hourStartNum = 6;
                        } else if (hourStartNum < 6 && hourEndNum <= 6) {
                            continue;
                        }

                        if (hourEndNum == 0)
                            hourEndNum = 24;
                        for (int j = hourStartNum - 6; j < hourEndNum - 5; j++) {
                            if (checkAdded[j][startDayIndex] == false) {
                                peopleCountArray[j][startDayIndex] += 1;
                                checkAdded[j][startDayIndex] = true;
                            }
                        }
                    } else if (startDayIndex < endDayIndex) {
                        if (hourStartNum < 6) {
                            hourStartNum = 6;
                        }
                        if (hourEndNum == 0)
                            hourEndNum = 24;
                        for (int j = startDayIndex; j <= endDayIndex; j++) {
                            if (j != endDayIndex) {
                                for (int k = hourStartNum - 6; k < hourEndNum - 5; k++) {
                                    if (checkAdded[k][j] == false) {
                                        peopleCountArray[k][j] += 1;
                                        checkAdded[k][j] = true;
                                    }
                                }
                            } else {
                                for (int k = hourStartNum - 6; k < 19; k++) {
                                    if (checkAdded[k][j] == false) {
                                        peopleCountArray[k][j] += 1;
                                        checkAdded[k][j] = true;
                                    }
                                }
                            }
                            hourStartNum = 6;
                        }
                    }
                }
            }

        }

        insertGrid();
    }

    private void insertGrid() { ;
        for (int i = 0; i < idArray.length; i++) {
            for (int j = 0; j < idArray[0].length; j++) {
                idArray[i][j].setBackgroundColor(getColor(this.groupMemberCount, peopleCountArray[i][j]));
            }
        }
    }


    private int getDaysOfMonth(String selectedMonth, String selectedYear) {
        int daysInMonth = -1;
        switch (selectedMonth) {
            case "01":
                daysInMonth = 31;
            case "02":
                if(Integer.parseInt(selectedYear) % 4 == 0)
                    daysInMonth = 29;
                else
                    daysInMonth = 28;
            case "03":
                daysInMonth = 31;
            case "04":
                daysInMonth = 30;
            case "05":
                daysInMonth = 31;
            case "06":
                daysInMonth = 30;
            case "07":
                daysInMonth = 31;
            case "08":
                daysInMonth = 31;
            case "09":
                daysInMonth = 30;
            case "10":
                daysInMonth = 31;
            case "11":
                daysInMonth = 30;
            case "12":
                daysInMonth = 31;
        }
        return daysInMonth;
    }

    private int getColor(int totalPeople, int unavailablePeople) {
        float ratio = ((float)(totalPeople-unavailablePeople)) / ((float)totalPeople);

        @ColorInt
        int color = Color.rgb((int)(((float)255)*(1-ratio)), (int)(((float)255)*(ratio)), 0);

        return color;
    }

    private int getDayIndex(String day) {
        switch (day) {
            case "Mon":
                return 0;
            case "Tue":
                return 1;
            case "Wed":
                return 2;
            case "Thu":
                return 3;
            case "Fri":
                return 4;
            case "Sat":
                return 5;
            case "Sun":
                return 6;
        }
        return -1;
    }

}
