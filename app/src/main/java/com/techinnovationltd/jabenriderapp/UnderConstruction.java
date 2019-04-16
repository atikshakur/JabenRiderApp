package com.techinnovationltd.jabenriderapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.techinnovationltd.jabenriderapp.Driver.BikasPayment;
import com.techinnovationltd.jabenriderapp.Driver.DriverRegistrationActivity;

import java.util.HashMap;

public class UnderConstruction extends AppCompatActivity {

    FirebaseUser firebaseUser;
    DatabaseReference reference;

    TextView hint1,hint2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snack_constructor);

        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();

        //set back bar in aActionbar:1

        //set back bar in aActionbar:1

        hint1=findViewById(R.id.text1);
        hint2=findViewById(R.id.text2);

      reference=FirebaseDatabase.getInstance().getReference("construction").child("main_screen");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String firstHint=dataSnapshot.child("firstHint").getValue().toString();
                String secHint=dataSnapshot.child("secHint").getValue().toString();


                Snackbar.make(findViewById(android.R.id.content),firstHint+"."+secHint,Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

}
