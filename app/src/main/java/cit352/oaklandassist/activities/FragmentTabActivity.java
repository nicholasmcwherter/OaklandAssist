package cit352.oaklandassist.activities;

import android.content.Intent;
import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import cit352.oaklandassist.R;
import cit352.oaklandassist.firebase.FirebaseHelper;
import cit352.oaklandassist.fragments.AboutUsFragment;
import cit352.oaklandassist.fragments.EventsFragment;
import cit352.oaklandassist.fragments.OaklandMapFragment;
import cit352.oaklandassist.utility.PermissionChangeListener;

public class FragmentTabActivity extends AppCompatActivity
        implements AHBottomNavigation.OnTabSelectedListener {


    private static final int LOCATION_REQUEST = 1;

    AHBottomNavigation bottomNavigation;

    DatabaseReference db;
    FirebaseHelper helper;

    public void getLocationPerm() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                Toast.makeText(this, "This will allow you to utilize the map to improve your experience!",
                        Toast.LENGTH_SHORT).show();
            }
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST);
        }
    }

}

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                          @NonNull String permissions[],
                                          @NonNull int[] grantResults)
    {
        switch (requestCode)
        {
            case LOCATION_REQUEST:
                boolean accepted;

                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Location permission granted!", Toast.LENGTH_SHORT).show();
                    accepted = true;
                }
                    else {
                    Toast.makeText(this, "Location permission denied!", Toast.LENGTH_SHORT).show();
                    accepted = false;
                }

                Fragment f = getSupportFragmentManager().findFragmentById(R.id.content_layout);
                if(f instanceof PermissionChangeListener) {
                    ((PermissionChangeListener) f).onPermissionChange(accepted);
                }
                break;
                default:
                    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                }

        }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottomNav);

        bottomNavigation.setOnTabSelectedListener(this);
        this.createNavItems();

        getLocationPerm();

        Firebase.setAndroidContext(this);
        db = FirebaseDatabase.getInstance().getReference();
        helper = new FirebaseHelper(db);
    }


    private void createNavItems()
    {
        // Create Items for Nav Tab

        AHBottomNavigationItem mapItem = new AHBottomNavigationItem("MAP", R.drawable.map);
        AHBottomNavigationItem eventsItem = new AHBottomNavigationItem("EVENTS", R.drawable.events);
        AHBottomNavigationItem aboutItem = new AHBottomNavigationItem("ABOUT US", R.drawable.about);

        // Add to Nav Tab

        bottomNavigation.addItem(mapItem);
        bottomNavigation.addItem(eventsItem);
        bottomNavigation.addItem(aboutItem);

        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#b69a5b"));

        // Change Colors

        bottomNavigation.setAccentColor(Color.parseColor("#000000"));
        bottomNavigation.setInactiveColor(Color.parseColor("#FFFFFF"));

        // Force the titles to be displayed

        bottomNavigation.setForceTitlesDisplay(true);

        // Default Tab is Map
        bottomNavigation.setCurrentItem(0);

    }

    @Override
    public void onTabSelected(int position, boolean wasSelected)
    {

        if (position == 0) {
            OaklandMapFragment oaklandMapFragment = new OaklandMapFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_layout, oaklandMapFragment).addToBackStack(null).commit();
        }
        if (position == 1) {
            EventsFragment eventsFragment = new EventsFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_layout, eventsFragment).addToBackStack(null).commit();
        }
        if (position == 2) {
            AboutUsFragment aboutUsFragment = new AboutUsFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_layout, aboutUsFragment).addToBackStack(null).commit();
        }
    }

    public void onBackPressed() {
        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
        } else {
            getFragmentManager().popBackStackImmediate();
        }
    }
}
