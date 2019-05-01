package com.techinnovationltd.jabenriderapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.techinnovationltd.jabenriderapp.passenger.signup.Registration;

public class VerifiedActivity extends AppCompatActivity {

    TextView get_phone;

    FirebaseUser firebaseUser,firebaseUserChecking;
    DatabaseReference reference,referenceChecking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verified);

        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();

        get_phone=findViewById(R.id.txt_get_phone);


        String phone=getIntent().getExtras().getString("phones");
        get_phone.setText(phone);
        final String phone_check = get_phone.getText().toString().trim();

        firebaseUserChecking= FirebaseAuth.getInstance().getCurrentUser();
        referenceChecking= FirebaseDatabase.getInstance().getReference("Users");

        referenceChecking.orderByChild("Phone").equalTo(phone_check).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.getValue() != null){
                    //it means user already registered
                    //Add code to show your prompt
                    startActivity(new Intent(VerifiedActivity.this, UnderConstruction.class));
                    finish();

                }

                else {
                    Intent intent = new Intent(VerifiedActivity.this, Registration.class);
                    intent.putExtra("phones", phone_check);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
