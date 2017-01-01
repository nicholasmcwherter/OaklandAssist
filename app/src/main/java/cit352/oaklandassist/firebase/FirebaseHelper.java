package cit352.oaklandassist.firebase;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;


import java.util.ArrayList;

import cit352.oaklandassist.model.Events;

public class FirebaseHelper {

    DatabaseReference db;
    Boolean saved;
    ArrayList<Events> eventsList = new ArrayList<>();


    public FirebaseHelper(DatabaseReference db) {
        this.db = db;

    }

    // Save/Write data
    public Boolean save(Events events)
    {
        if (events == null)
        {
            saved = false;
        } else {
            try
            {
                db.child("Events").push().setValue(events);
                saved = true;

            } catch (DatabaseException e)
            {
                e.printStackTrace();
                saved = false;
            }
        }
        return saved;
    }

    private void getData(DataSnapshot dataSnapshot) {
        eventsList.clear();
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            Events events = ds.getValue(Events.class);
            eventsList.add(events);
        }
    }


    // Read data from Firebase
    public ArrayList<Events> retrieve() {
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                getData(dataSnapshot);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                getData(dataSnapshot);

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                getData(dataSnapshot);

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                getData(dataSnapshot);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return eventsList;
    }


}



