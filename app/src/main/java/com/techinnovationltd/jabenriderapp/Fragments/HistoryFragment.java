package com.techinnovationltd.jabenriderapp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techinnovationltd.jabenriderapp.EntryPoint.ProcessMain;
import com.techinnovationltd.jabenriderapp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {


    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // change fragment's actionbar Title:part 2
        ((ProcessMain)getActivity()).setActionBarTitle("History");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

}
