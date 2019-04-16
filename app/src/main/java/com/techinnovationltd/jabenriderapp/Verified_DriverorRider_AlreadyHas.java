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
import com.techinnovationltd.jabenriderapp.Driver.BikasPayment;
import com.techinnovationltd.jabenriderapp.Driver.DriverRegistrationSelectCityActivity;
import com.techinnovationltd.jabenriderapp.Driver.VerifiedActivityDriver;
import com.techinnovationltd.jabenriderapp.Model.Rider;

public class Verified_DriverorRider_AlreadyHas extends AppCompatActivity {

    TextView get_phone;

    FirebaseUser firebaseUser,firebaseUserChecking;
    DatabaseReference reference,referenceChecking;



    @Override
    protected void onStart() {



        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser!= null) {

            final String userID = firebaseUser.getUid();


            referenceChecking = FirebaseDatabase.getInstance().getReference("Users");

            referenceChecking.orderByChild("id").equalTo(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.getValue() != null) {
                        //it means user already registered
                        //Add code to show your prompt
                        startActivity(new Intent(Verified_DriverorRider_AlreadyHas.this, UnderConstruction.class));
                        finish();

                    } else {

                        //For checking rider has already login or not
                        final String phone_check = "defalut";

                        firebaseUserChecking= FirebaseAuth.getInstance().getCurrentUser();
                        referenceChecking= FirebaseDatabase.getInstance().getReference("Riders");

                        referenceChecking.orderByChild("Name").equalTo(phone_check).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                if (dataSnapshot.getValue() != null){


                                    Intent intent = new Intent(Verified_DriverorRider_AlreadyHas.this, DriverRegistrationSelectCityActivity.class);
                                    intent.putExtra("phones", phone_check);
                                    startActivity(intent);
                                    finish();



                                }

                                else {

                                    //it means user already registered
                                    //Add code to show your prompt
                                    final String userID = firebaseUser.getUid();

                                    final String binum1="NotGiven";
                                    String null1="";

                                    referenceChecking = FirebaseDatabase.getInstance().getReference("Riders").child(userID);


                                    referenceChecking.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                            Rider rider=dataSnapshot.getValue(Rider.class);
                                            String binum=rider.getStatus().toString().trim();


                                            if (!binum.equals(binum1)){

                                                startActivity(new Intent(getApplicationContext(),UnderConstruction.class));
                                                finish();

                                            }
                                            else {
                                                startActivity(new Intent(getApplicationContext(),BikasPayment.class));
                                            }

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


            //If not then you process from begging



        }

        else
        {
            startActivity(new Intent(Verified_DriverorRider_AlreadyHas.this, SelectAccountTypeActivity.class));
            finish();

        }

        super.onStart();
    }











    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verified);

        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();




    }
}
