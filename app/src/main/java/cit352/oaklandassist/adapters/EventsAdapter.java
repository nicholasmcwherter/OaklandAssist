package cit352.oaklandassist.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import cit352.oaklandassist.R;
import cit352.oaklandassist.model.Events;

public class EventsAdapter extends BaseAdapter {

    Context context;
    ArrayList<Events> eventsList;

    public EventsAdapter(Context context, ArrayList<Events> eventsList) {

        this.context = context;
        this.eventsList = eventsList;

    }

    @Override
    public int getCount() {
        return eventsList.size();
    }

    @Override
    public Object getItem(int position) {
        return eventsList.get(position);
    }

    @Override
    public long getItemId(int positon) {
        return positon;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.events_cell_layout, parent, false);
        }

        TextView textViewBuildingName = (TextView) convertView.findViewById(R.id.textViewBuildingName);
        TextView textViewBuildingTitle = (TextView) convertView.findViewById(R.id.textViewBuildingTitle);
        TextView textViewEventTime = (TextView) convertView.findViewById(R.id.textViewEventTime);
        TextView textViewEventSpecificLocation = (TextView) convertView.findViewById(R.id.textViewEventSpecificLocation);
        TextView textViewBuildingDetails = (TextView) convertView.findViewById(R.id.textViewBuildingDetails);


        final Events events = (Events) this.getItem(position);

        textViewBuildingName.setText(events.getBuildingEventName());
        textViewBuildingTitle.setText(events.getBuildingEventTitle());
        textViewEventTime.setText(events.getEventTime());
        textViewEventSpecificLocation.setText(events.getEventSpecificLocation());
        textViewBuildingDetails.setText(events.getEventDetails());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, events.getBuildingEventName(), Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

}
