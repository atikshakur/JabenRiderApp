package com.techinnovationltd.jabenriderapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class LocationPermissionSlideActivity extends AppCompatActivity {

    private Button btnMy_Location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_permission_slide);

        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();

        btnMy_Location= findViewById(R.id.btn_current_location);

        btnMy_Location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LocationPermissionSlideActivity.this,ProcessMain.class);
                startActivity(intent);
            }
        });

    }
}
