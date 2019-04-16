package com.techinnovationltd.jabenriderapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.techinnovationltd.jabenriderapp.Driver.LogInWithPhoneNumberActivityDriver;
import com.techinnovationltd.jabenriderapp.Driver.MapsActivityDriver;

public class SelectAccountTypeActivity extends AppCompatActivity {

    private Button btn_passenger,btn_driver;

    FirebaseUser firebaseUser;
    DatabaseReference referenceChecking;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_account_type);

        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();

        btn_passenger = findViewById(R.id.btn_passenger_lets_go);
        btn_driver = findViewById(R.id.btn_driver_lets_go);

        btn_passenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectAccountTypeActivity.this, LogInWithPhoneNumberActivity.class);
                startActivity(intent);
            }
        });

        btn_driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(SelectAccountTypeActivity.this,LogInWithPhoneNumberActivityDriver.class));

           }
       });

    }
}
