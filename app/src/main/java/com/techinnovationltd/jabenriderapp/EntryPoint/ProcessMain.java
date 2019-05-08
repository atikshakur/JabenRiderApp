package com.techinnovationltd.jabenriderapp.EntryPoint;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.techinnovationltd.jabenriderapp.Fragments.HistoryFragment;
import com.techinnovationltd.jabenriderapp.Fragments.HomeFragment;
import com.techinnovationltd.jabenriderapp.Fragments.InboxFragment;
import com.techinnovationltd.jabenriderapp.Fragments.MoreFragment;
import com.techinnovationltd.jabenriderapp.R;

public class ProcessMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_main);

        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();

        BottomNavigationView bottomnav = findViewById(R.id.bottom_navigation);
        bottomnav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;

                    switch (menuItem.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            break;

                        case R.id.nav_history:
                            selectedFragment = new HistoryFragment();
                            break;

                        case R.id.nav_inbox:
                            selectedFragment = new InboxFragment();
                            break;

                        case R.id.nav_more:
                            selectedFragment = new MoreFragment();
                            break;

                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
    };





    // change fragment's actionbar Title:part 1
    public void setActionBarTitle(String title){
        getSupportActionBar().setTitle(title);
    }
    // change fragment's actionbar Title:part 1
}
