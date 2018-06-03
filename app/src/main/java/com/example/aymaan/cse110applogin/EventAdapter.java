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
            name.setText(currentEntryObject.getTitle() + " at " + currentEntryObject.getEnd().toString() + " with " + currentEntryObject.getDescription());
        }
        else {
            name.setText(currentEntryObject.getTitle() + " at " + currentEntryObject.getStart().toString() + " to " + currentEntryObject.getEnd().toString() + " with " + currentEntryObject.getDescription() );
        }


        return listItem;
    }

}
