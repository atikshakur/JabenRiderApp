package com.techinnovationltd.jabenriderapp.Driver;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Interpolator;
import android.location.Location;
import com.google.android.gms.location.LocationListener;
import android.os.Handler;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.annotation.InterpolatorRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.github.glomadrian.materialanimatedswitch.MaterialAnimatedSwitch;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.dynamic.IFragmentWrapper;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.techinnovationltd.jabenriderapp.R;

import java.sql.Driver;

public class MapsActivityDriver extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleMap mMap;
    private static final int My_permission_request_code=7000;

    private static final int Play_service_request=7001;

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Location mLastLocation;

    private static int update_interval=5000;

    private static int Fatest_interval=3000;

    private static int Displacement=10;

    DatabaseReference databaseReference;
    GeoFire geoFire;


    Marker mCurrent;
    MaterialAnimatedSwitch materialAnimatedSwitch;
    SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_driver);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        materialAnimatedSwitch=findViewById(R.id.location_switch_ON_OFF);

        materialAnimatedSwitch.setOnCheckedChangeListener(new MaterialAnimatedSwitch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(boolean isOnline) {

                if (isOnline){
                    startLocaionUpdated();
                    displayLocation();
                    Snackbar.make(mapFragment.getView(),"You are Online",Snackbar.LENGTH_LONG).show();

                }

                else {
                    stopLocationUpdate();
                    mCurrent.remove();
                    Snackbar.make(mapFragment.getView(),"You are Offline",Snackbar.LENGTH_LONG).show();
                }

            }
        });


        //Geo Fire
        databaseReference= FirebaseDatabase.getInstance().getReference("Drivers");
        geoFire= new GeoFire(databaseReference);

        setUpLocation();



    }




    private void stopLocationUpdate() {


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION )!= PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION )!= PackageManager.PERMISSION_GRANTED )
        {
            return;
        }

        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,  this);
    }

    private void displayLocation() {


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (mLastLocation != null) {

             if (materialAnimatedSwitch.isChecked()){



                final double latituse = mLastLocation.getLatitude();
                final double longitiude = mLastLocation.getLongitude();


                //Updated to firebase

                geoFire.setLocation(FirebaseAuth.getInstance().getCurrentUser().getUid(), new GeoLocation(latituse, longitiude), new GeoFire.CompletionListener() {
                    @Override
                    public void onComplete(String key, DatabaseError error) {
                        //Add maker
                        if (mCurrent != null) {

                            mCurrent.remove();

                            mCurrent = mMap.addMarker(new MarkerOptions().icon
                                    (BitmapDescriptorFactory.fromResource(R.drawable.car))
                                    .position(new LatLng(latituse, longitiude))
                                    .title("You"));


                            //Move camera to this position
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latituse, longitiude), 15.0f));

                            //draw animated rotate maker
                            rotateMarker(mCurrent, -360, mMap);

                        }
                    }
                });
            } else {
                Log.d("Error", "Cannot get your Location");
            }
        }
    }


    private void rotateMarker(final Marker mCurrent, final float i, GoogleMap mMap) {

        final Handler handler=new Handler();
        final long start= SystemClock.uptimeMillis();
        final float startRotation=mCurrent.getRotation();
        final long duration=1500;

        final LinearInterpolator interpolator=new LinearInterpolator();

        handler.post(new Runnable() {
            @Override
            public void run() {

                long elapsed=SystemClock.uptimeMillis()-start;
                float t=interpolator.getInterpolation((float)elapsed/duration);
                float rot=t*i+(1-t)*startRotation;

                mCurrent.setRotation(-rot>180?rot/2:rot);

                if (t<1.0){
                    handler.postDelayed(this,16);
                }

            }
        });


    }






    private void startLocaionUpdated() {


        //fOR CHECKING THE PERMISSION FROM USER


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION )!= PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION )!= PackageManager.PERMISSION_GRANTED )
        {
            return;
        }


        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,mLocationRequest, this);

    }

    private void buildGoogleApiClients(){

        mGoogleApiClient= new GoogleApiClient.Builder(this).
                addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mGoogleApiClient.connect();


    }


    private void setUpLocation() {


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION )!= PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION )!= PackageManager.PERMISSION_GRANTED )
        {
            //Request Runtime permission

            ActivityCompat.requestPermissions(this,new String[]{

                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
            },My_permission_request_code);

        }

        else
        {
            if(checkPlayServices())
            {
                buildGoogleApiClients();
                createLocationRequest();

                if (materialAnimatedSwitch.isChecked()){


                    displayLocation();
}


            }
        }

    }



    private void createLocationRequest() {

        mLocationRequest= new LocationRequest();
        mLocationRequest.setInterval(update_interval);
        mLocationRequest.setFastestInterval(Fatest_interval);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(Displacement);

    }


    private boolean checkPlayServices() {

        int resultcode= GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

        if (resultcode != ConnectionResult.SUCCESS){
            if (GooglePlayServicesUtil.isUserRecoverableError(resultcode)){
                GooglePlayServicesUtil.getErrorDialog(resultcode,this,Play_service_request).show();
            }
            else {
                Toast.makeText(this,"This devices is not supported",Toast.LENGTH_LONG).show();
                finish();
            }
            return false;
        }
        return true;
    }




    //For overrride the permission

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){
            case My_permission_request_code:
                if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){


                    if(checkPlayServices())
                    {
                        buildGoogleApiClients();
                        createLocationRequest();

                        if (materialAnimatedSwitch.isChecked()){


                            displayLocation();}



                    }


                }
        }
    }






    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


    }



    @Override
    public void onConnected(@Nullable Bundle bundle) {

        displayLocation();
        startLocaionUpdated();

    }

    @Override
    public void onConnectionSuspended(int i) {

        mGoogleApiClient.connect();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

        mLastLocation=location;
        displayLocation();

    }
}
