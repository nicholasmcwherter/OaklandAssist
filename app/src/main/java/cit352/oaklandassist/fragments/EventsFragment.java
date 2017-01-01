package cit352.oaklandassist.fragments;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import cit352.oaklandassist.R;
import cit352.oaklandassist.adapters.EventsAdapter;
import cit352.oaklandassist.firebase.FirebaseHelper;
import cit352.oaklandassist.model.Events;

public class EventsFragment extends Fragment {

    ListView listView;
    DatabaseReference db;
    FirebaseHelper helper;
    String buildingSpinnerName;
    EventsAdapter eventsAdapter;



    EditText buildingNameEditText, eventTimeEditText, eventSpecificLocationEditText, eventDetailsEditText;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View rootView = inflater.inflate(R.layout.events_layout, container, false);


        //firebase = new Firebase("https://oakland-assist.firebaseio.com/");
        //firebase.setAndroidContext(getActivity());


        // Initialize
        //initializeFirebase();

        listView = (ListView) rootView.findViewById(R.id.listViewBuilding);

        db = FirebaseDatabase.getInstance().getReference();
        helper = new FirebaseHelper(db);
        eventsAdapter = new EventsAdapter(getActivity(), helper.retrieve());
        listView.setAdapter(eventsAdapter);



        Button btnCreateEvent = (Button) rootView.findViewById(R.id.btnCreateEvent);
        Button btnUpdate = (Button) rootView.findViewById(R.id.btnUpdate);





        btnCreateEvent.setOnClickListener(new View.OnClickListener() {
                                                               @Override
                                                               public void onClick(View view) {
                                                                   Dialog dialog = new Dialog(getActivity());
                                                                   dialog.setTitle("Create Event!");
                                                                   dialog.setContentView(R.layout.event_dialog_layout);

                                                                   eventsAdapter = new EventsAdapter(getActivity(), helper.retrieve());
                                                                   listView.setAdapter(eventsAdapter);

                                                                   final Spinner spinner = (Spinner) dialog.findViewById(R.id.buildingSpinner);
                                                                   // Create an ArrayAdapter using the string array and a default spinner layout
                                                                   final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                                                                           R.array.buildings, android.R.layout.simple_spinner_item);

                                                                   // Specify the layout to use when the list of choices appears
                                                                   adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                                   // Apply the adapter to the spinner
                                                                   spinner.setAdapter(adapter);

                                                                   buildingNameEditText = (EditText) dialog.findViewById(R.id.eventNameEditText);
                                                                   eventTimeEditText = (EditText) dialog.findViewById(R.id.eventTimeEditText);
                                                                   eventSpecificLocationEditText = (EditText) dialog.findViewById(R.id.eventSpecificLocationEditText);
                                                                   eventDetailsEditText = (EditText) dialog.findViewById(R.id.eventDetailsEditText);
                                                                   Button acceptBtn = (Button) dialog.findViewById(R.id.acceptBtn);


                                                                   spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                                                                         @Override
                                                                                                         public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                                                                                                             buildingSpinnerName = spinner.getItemAtPosition(position).toString();

                                                                                                         }

                                                                                                         @Override
                                                                                                         public void onNothingSelected(AdapterView<?> adapterView) {

                                                                                                         }
                                                                                                     });

                                                                           acceptBtn.setOnClickListener(new View.OnClickListener() {
                                                                               @Override
                                                                               public void onClick(View v) {

                                                                                   String buildingEventTitle = buildingSpinnerName.toString();
                                                                                   String buildingEventName = buildingNameEditText.getText().toString();
                                                                                   String eventTime = eventTimeEditText.getText().toString();
                                                                                   String eventSpecificLocation = eventSpecificLocationEditText.getText().toString();
                                                                                   String eventDetails = eventDetailsEditText.getText().toString();

                                                                                   Events events = new Events();
                                                                                   events.setBuildingEventTitle(buildingEventTitle);
                                                                                   events.setBuildingEventName(buildingEventName);
                                                                                   events.setEventTime(eventTime);
                                                                                   events.setEventSpecificLocation(eventSpecificLocation);
                                                                                   events.setEventDetails(eventDetails);


                                                                                   //Save!
                                                                                   if (buildingEventName != null && buildingEventName.length() > 0) {

                                                                                       if (helper.save(events)) {
                                                                                           buildingSpinnerName = ("");
                                                                                           buildingNameEditText.setText("");
                                                                                           eventTimeEditText.setText("");
                                                                                           eventSpecificLocationEditText.setText("");
                                                                                           eventDetailsEditText.setText("");

                                                                                           eventsAdapter = new EventsAdapter(getActivity(), helper.retrieve());
                                                                                           listView.setAdapter(eventsAdapter);
                                                                                       }


                                                                               } else {
                                                                                       Toast.makeText(getActivity(), "Event name must not be empty!", Toast.LENGTH_SHORT).show();
                                                                                       eventsAdapter = new EventsAdapter(getActivity(), helper.retrieve());
                                                                                       listView.setAdapter(eventsAdapter);
                                                                                   }

                                                                               }
                                                                           });

                                                                   dialog.show();
                                                               }
                                                           });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.event_dialog_layout);
                dialog.show();
                dialog.dismiss();
                eventsAdapter = new EventsAdapter(getActivity(), helper.retrieve());
                listView.setAdapter(eventsAdapter);
            }
        });

        btnUpdate.performClick();



        return rootView;
    }




    private void initializeFirebase() {

        Firebase.setAndroidContext(getActivity());
    }







    @Override
    public void onPause()
    {
        super.onPause();
    }

    @Override
    public void onStart()
    {
        eventsAdapter = new EventsAdapter(getActivity(), helper.retrieve());
        listView.setAdapter(eventsAdapter);
        super.onStart();
    }

    @Override
    public void onResume()
    {
        eventsAdapter = new EventsAdapter(getActivity(), helper.retrieve());
        listView.setAdapter(eventsAdapter);
        super.onResume();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

    @Override
    public void onLowMemory()
    {
        super.onLowMemory();
    }

}
