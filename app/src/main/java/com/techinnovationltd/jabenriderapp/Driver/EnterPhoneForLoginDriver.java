package com.techinnovationltd.jabenriderapp.Driver;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.techinnovationltd.jabenriderapp.LocationPermissionSlideActivity;
import com.techinnovationltd.jabenriderapp.PhoneVerfiledCode;
import com.techinnovationltd.jabenriderapp.R;
import com.techinnovationltd.jabenriderapp.Registration;
import com.techinnovationltd.jabenriderapp.VerifiedActivity;

public class EnterPhoneForLoginDriver extends AppCompatActivity  {

    Button next;
    EditText phoneNumber;

    FirebaseAuth mAuth;
    ProgressBar phonelayoutBar;

    TextView wrongMessage;

    ProgressBar splashLog;
    int progress=0;
    Handler h=new Handler();


    FirebaseUser firebaseUser,firebaseUserChecking;
    DatabaseReference reference,referenceChecking;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_phone_for_login);
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();

        phoneNumber = findViewById(R.id.phone_number);
        next = findViewById(R.id.btn_Next);
        phonelayoutBar = findViewById(R.id.phoneProgress);
        wrongMessage=findViewById(R.id.txt_wrong);
        splashLog=findViewById(R.id.phoneProgress);




        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vaildation();


            }
        });
    }

    private void Vaildation(){
        String vaildPhone=phoneNumber.getText().toString();

        if (vaildPhone.isEmpty()){
            wrongMessage.setVisibility(View.VISIBLE);

            wrongMessage.setText("The phone number can't be empty");
        }

        else if (vaildPhone.length()<10 || vaildPhone.length()>11){
            wrongMessage.setVisibility(View.VISIBLE);
            wrongMessage.setText("Please input 10 number Phone");
        }

        else {

            final String phone_check = "+880" + phoneNumber.getText().toString();

            firebaseUserChecking= FirebaseAuth.getInstance().getCurrentUser();
            referenceChecking= FirebaseDatabase.getInstance().getReference("Users");

            referenceChecking.orderByChild("Phone").equalTo(phone_check).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.getValue() != null){
                        //it means user already registered
                        //Add code to show your prompt

                        wrongMessage.setVisibility(View.VISIBLE);
                        wrongMessage.setText("Already has account by this Number On Passenger");


                    }

                    else {


                        wrongMessage.setVisibility(View.GONE);


                        splashLog.setVisibility(View.VISIBLE);

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                for (int i=0;i<5;i++){
                                    progress+=20;
                                    h.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            splashLog.setProgress(progress);
                                            if (progress==splashLog.getMax()){

                                                String phones = "+880" + phoneNumber.getText().toString();

                                                Intent intent = new Intent(EnterPhoneForLoginDriver.this,PhoneVerfiledCodeDriver.class);
                                                intent.putExtra("phones", phones);
                                                startActivity(intent);
                                            }
                                        }
                                    });

                                    try {

                                        Thread.sleep(1200);

                                    }
                                    catch (InterruptedException e){

                                    }
                                }
                            }
                        }).start();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });






        }

    }

}
