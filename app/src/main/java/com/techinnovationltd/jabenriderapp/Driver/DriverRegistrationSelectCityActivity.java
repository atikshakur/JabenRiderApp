package com.techinnovationltd.jabenriderapp.Driver;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.techinnovationltd.jabenriderapp.R;

public class DriverRegistrationSelectCityActivity extends AppCompatActivity {

    private LinearLayout layout_dhka,layout_ctg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_registration_select_city);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        ActionBar ab = getSupportActionBar();
        ab.setTitle("Sign Up");

        final String phone=getIntent().getExtras().getString("phones");
        layout_dhka =  findViewById(R.id.dhaka);
        layout_ctg =  findViewById(R.id.ctg);

        layout_dhka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city="Dhaka";
                Intent intent = new Intent(DriverRegistrationSelectCityActivity.this,DriverRegistrationSelectVehicleActivity.class);
                intent.putExtra("city",city);
                intent.putExtra("phones", phone);
                startActivity(intent);
                finish();
            }
        });

        layout_ctg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city="Chittagong";
                Intent intent = new Intent(DriverRegistrationSelectCityActivity.this,DriverRegistrationSelectVehicleActivity.class);
                intent.putExtra("city",city);
                intent.putExtra("phones", phone);
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
