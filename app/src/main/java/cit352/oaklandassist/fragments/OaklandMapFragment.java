package cit352.oaklandassist.fragments;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import cit352.oaklandassist.R;
import cit352.oaklandassist.utility.PermissionChangeListener;

public class OaklandMapFragment extends Fragment implements PermissionChangeListener {

    private MapView mapView;
    private GoogleMap map;

    LatLngBounds.Builder builder;
    CameraUpdate cu;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.map_layout, container, false);

        // Get the MapView from the XML Layout
        mapView = (MapView) rootView.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);


        // Gets to GoogleMap from the MapView and does initialization stuff
        map = mapView.getMap();

        map.getUiSettings().setMapToolbarEnabled(false);
        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setZoomGesturesEnabled(true);
        map.getUiSettings().setScrollGesturesEnabled(true);

        // Needs to call MapInitializer before doing any CameraUpdateFactory
        MapsInitializer.initialize(getActivity());

        // Location Lat/Long for each Marker
        LatLng oakland = new LatLng(42.672876, -83.217975);
        LatLng southFoundation = new LatLng(42.673495, -83.217732);
        LatLng engineeringCenter = new LatLng(42.671886, -83.214964);
        LatLng msc = new LatLng(42.670963, -83.217619);



        // List of Markers/Buildings
        map.addMarker(new MarkerOptions()
                .position(oakland)
                .title("Oakland University"));
        map.addMarker(new MarkerOptions()
                .position(southFoundation)
                .title("South Foundation Hall"));
        map.addMarker(new MarkerOptions()
                .position(engineeringCenter)
                .title("Engineering Center"));
        map.addMarker(new MarkerOptions()
                .position(msc)
                .title("Mathematics and Science Center"));

        // Default map view
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(oakland, 16));


        return rootView;

    }


    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStart()
    {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
            map.getUiSettings().setMyLocationButtonEnabled(true);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    public void onPermissionChange(boolean permissionOnOff) {

        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            map.getUiSettings().setMyLocationButtonEnabled(permissionOnOff);
            map.setMyLocationEnabled(permissionOnOff);

        }

    }

    /**
    int index = getActivity().getFragmentManager().getBackStackEntryCount() - 1;
    FragmentManager.BackStackEntry backStackEntry = getFragmentManager().getBackStackEntryAt(index);
    String tag = backStackEntry.getName();
    Fragment fragment = getFragmentManager().findFragmentByTag(null);
     **/





/**
        CameraPosition cameraPosition = new CameraPosition.Builder().target(southFoundation).zoom(12).build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
**/



}
