package com.techinnovationltd.jabenriderapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthProvider;
import com.techinnovationltd.jabenriderapp.passenger.signup.PhoneVerfiledCode;

public class EnterPhoneForLogin extends AppCompatActivity  {

    Button next;
    EditText phoneNumber;

    FirebaseAuth mAuth;
    ProgressBar phonelayoutBar;

    TextView wrongMessage;

    ProgressBar splashLog;
    int progress=0;
    Handler h=new Handler();

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

                                    Intent intent = new Intent(EnterPhoneForLogin.this, PhoneVerfiledCode.class);
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

}
