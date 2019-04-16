package com.techinnovationltd.jabenriderapp.FragmentsDriver;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.techinnovationltd.jabenriderapp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class TodayFragment extends Fragment implements OnMapReadyCallback {

    GoogleMap map;

    public TodayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_today,container,false);

        SupportMapFragment mapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync( this);

        return v;
        // Inflate the layout for this fragment
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;

        LatLng Chittagong = new LatLng(22.398807, 91.823480);
        map.addMarker(new MarkerOptions().position(Chittagong).title("Chittagong"));
        map.moveCamera(CameraUpdateFactory.newLatLng(Chittagong));


    }

}
