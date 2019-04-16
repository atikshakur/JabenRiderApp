package com.techinnovationltd.jabenriderapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.goodiebag.pinview.Pinview;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class PhoneVerfiledCode extends AppCompatActivity implements View.OnClickListener {

    private TextView phoneNum,repeat_code;
    private Button  continueProcess;
    private Pinview code;

    private FirebaseAuth mAuth;
    FirebaseUser firebaseUser,firebaseUserChecking;
    DatabaseReference reference,referenceChecking;


    String codeSent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_verfiled_code);
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();

        setUpUIView();

        String phn=getIntent().getExtras().getString("phones");
        phoneNum.setText(phn);

        mAuth=FirebaseAuth.getInstance();

        sendVerificationCode();

        continueProcess.setOnClickListener(this);



    }

    private void setUpUIView(){

        phoneNum=findViewById(R.id.phone);
        continueProcess=findViewById(R.id.btn_Continue);
        repeat_code=findViewById(R.id.txt_dont_get_code);
        code=findViewById(R.id.edt_verify_code);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_Continue:

                String edt_code=code.getValue().toString();

                if(TextUtils.isEmpty(edt_code)){
                    Toast.makeText(getApplicationContext(),"Code can't be empty",Toast.LENGTH_LONG).show();
                }

                else if (edt_code.length()<6){

                    Toast.makeText(getApplicationContext(),"Code can't be less then 6",Toast.LENGTH_LONG).show();
                }
                else {
                verifySignCode();
                }
                break;

            case R.id.txt_dont_get_code:
                sendVerificationCode();
                break;
        }
    }


    private void sendVerificationCode(){

        Toast.makeText(getApplicationContext(),"Sent Code",Toast.LENGTH_LONG).show();

        String phn=getIntent().getExtras().getString("phones");

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phn,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                PhoneVerfiledCode.this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks

    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            codeSent=s;
        }
    };



    private void verifySignCode() {

        String codeEnter=code.getValue().toString();

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent, codeEnter);
        signInWithPhoneAuthCredential(credential);
    }



    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {


                            firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
                            String userID=firebaseUser.getUid();

                            reference= FirebaseDatabase.getInstance().getReference("Users").child(userID);

                            HashMap<String,Object> hashMap=new HashMap<>();

                            hashMap.put("id",userID);
                            hashMap.put("Name","UserName");
                            hashMap.put("Email","UserEmail");
                            hashMap.put("Phone","UserPhone");
                            hashMap.put("Birth_Date","Userbirth");
                            hashMap.put("Password","Userpassword");
                            hashMap.put("Service_Type","UserPassenger");
                            hashMap.put("image_url","Defalut");


                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){

                                        Intent intent = new Intent(PhoneVerfiledCode.this, VerifiedActivity.class);
                                        String phn=getIntent().getExtras().getString("phones");
                                        intent.putExtra("phones", phn);
                                        startActivity(intent);
                                        finish();

                                    }

                                    else {
                                        Toast.makeText(getApplicationContext(),"Not Sucessfull",Toast.LENGTH_LONG).show();
                                    }

                                }
                            });

                        } else {

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(getApplicationContext(),"Incorrect Given Code",Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }
}
