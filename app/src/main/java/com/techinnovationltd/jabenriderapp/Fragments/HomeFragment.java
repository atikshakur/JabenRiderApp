package com.techinnovationltd.jabenriderapp.Fragments;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.techinnovationltd.jabenriderapp.R;

import java.security.Permission;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleMap map;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;

    private Location lastLocation;
    private Marker currentUserMarkerLocation;

    private static final int Request_User_Location_code=99;

    public HomeFragment() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            checkUserLocationPermission();
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Inflate the layout for this fragment
        return v;

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;

        if (ContextCompat.checkSelfPermission(getContext(),Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            // TODO: Consider calling

            buildGoogleApiClients();
            map.setMyLocationEnabled(true);
        }



    }

    public boolean checkUserLocationPermission(){

        if (ContextCompat.checkSelfPermission(getContext(),Manifest.permission.ACCESS_FINE_LOCATION )!= PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION))
            {
                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION},Request_User_Location_code);
            }

            else {
                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION},Request_User_Location_code);
            }
            return false;
        }

        else {
            return true;
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode)
        {

            case Request_User_Location_code:

                if (grantResults.length> 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                    if(ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_FINE_LOCATION) ==PackageManager.PERMISSION_GRANTED)
                    {
                        if (googleApiClient == null) {
                            buildGoogleApiClients();
                        }

                        map.setMyLocationEnabled(true);
                    }
                }

                else {
                    Toast.makeText(getActivity(),"Permission Denied For Location",Toast.LENGTH_LONG).show();
                }

                return;
        }
    }

    protected synchronized void buildGoogleApiClients(){

        googleApiClient= new GoogleApiClient.Builder(getContext()).
                                addConnectionCallbacks(this)
                                .addOnConnectionFailedListener(this)
                                .addApi(LocationServices.API)
                                .build();

        googleApiClient.connect();


    }

    @Override
    public void onLocationChanged(Location location) {

        lastLocation=location;

        if (currentUserMarkerLocation != null){

            currentUserMarkerLocation.remove();
        }

        LatLng latLng=new LatLng(location.getLatitude(), location.getLongitude());

        MarkerOptions markerOptions=new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("User Current Location");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));


        currentUserMarkerLocation=map.addMarker(markerOptions);

        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        map.animateCamera(CameraUpdateFactory.zoomBy(12));

        if (googleApiClient != null){

            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
        }


    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

        locationRequest= new LocationRequest();
        locationRequest.setInterval(1100);
        locationRequest.setFastestInterval(1100);
        locationRequest.setPriority(LocationRequest.PRIORITY_LOW_POWER);

        if (ContextCompat.checkSelfPermission(getContext(),Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)

        {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient,locationRequest,this);
        }


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
