package com.example.aymaan.cse110applogin;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jeff.database_access.EntryObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class EventAdapter extends ArrayAdapter<EntryObject> {
    private Context mContext;
    private List<EntryObject> EntryObjectList = new ArrayList<>();

    public EventAdapter(Context context, ArrayList<EntryObject> list) {
        super(context, 0 , list);
        mContext = context;
        EntryObjectList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.activity_home_list_item,parent,false);

        EntryObject currentEntryObject = EntryObjectList.get(position);


        TextView name = (TextView) listItem.findViewById(R.id.textView_event_details);

        if(currentEntryObject.getStart()== null) {
            SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm");
            String endTime = localDateFormat.format(currentEntryObject.getEnd());
            name.setText(currentEntryObject.getTitle() + "\n" +
                    endTime + "\n" +
                    currentEntryObject.getDescription());
        }
        else {
            SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm");
            String endTime = localDateFormat.format(currentEntryObject.getEnd());
            String startTime = localDateFormat.format(currentEntryObject.getStart());
            name.setText(currentEntryObject.getTitle() + "\n" +
                    startTime + " to " + endTime + "\n" +
                    currentEntryObject.getDescription());
        }


        return listItem;
    }

}
