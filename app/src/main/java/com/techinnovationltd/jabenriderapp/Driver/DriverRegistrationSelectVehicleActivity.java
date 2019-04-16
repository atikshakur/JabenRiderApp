package com.techinnovationltd.jabenriderapp.Driver;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.techinnovationltd.jabenriderapp.R;

public class DriverRegistrationSelectVehicleActivity extends AppCompatActivity {

    private LinearLayout layout_bike,layout_car,layout_micro,layout_cng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_registration_select_vehicle);

        //set back bar in aActionbar:1
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //set back bar in aActionbar:1

        // set cutome title in actionbar
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setTitle("Sign Up");
        // set cutome title in actionbar

        layout_bike =  findViewById(R.id.bike);
        layout_car =  findViewById(R.id.car);
        layout_micro =  findViewById(R.id.micro);
        layout_cng =  findViewById(R.id.cng);

        final String phone=getIntent().getExtras().getString("phones");
        final String city=getIntent().getExtras().getString("city");

        layout_bike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String VehicleType="Bike";
                Intent intent = new Intent(DriverRegistrationSelectVehicleActivity.this,DriverRegistrationActivity.class);
                intent.putExtra("city",city);
                intent.putExtra("phones", phone);
                intent.putExtra("vehicle",VehicleType);
                startActivity(intent);
                finish();
            }
        });

        layout_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String VehicleType="Car";
                Intent intent = new Intent(DriverRegistrationSelectVehicleActivity.this,DriverRegistrationActivity.class);
                intent.putExtra("city",city);
                intent.putExtra("phones", phone);
                intent.putExtra("vehicle",VehicleType);
                startActivity(intent);
                finish();
            }
        });

        layout_micro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String VehicleType="Micro";
                Intent intent = new Intent(DriverRegistrationSelectVehicleActivity.this,DriverRegistrationActivity.class);
                intent.putExtra("city",city);
                intent.putExtra("phones", phone);
                intent.putExtra("vehicle",VehicleType);
                startActivity(intent);
                finish();
            }
        });

        layout_cng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String VehicleType="CNG";
                Intent intent = new Intent(DriverRegistrationSelectVehicleActivity.this,DriverRegistrationActivity.class);
                intent.putExtra("city",city);
                intent.putExtra("phones", phone);
                intent.putExtra("vehicle",VehicleType);
                startActivity(intent);
                finish();
            }
        });



    }
    //set back bar in a Actionbar:2
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==android.R.id.home)
        {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
    //set back bar in a Actionbar:2
}

