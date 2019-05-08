package com.techinnovationltd.jabenriderapp.Driver;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.techinnovationltd.jabenriderapp.R;
import com.techinnovationltd.jabenriderapp.UnderConstruction;

import java.util.HashMap;

public class BikasPayment extends AppCompatActivity {

    EditText edtbikas,edttranscation;
    Button btn_continue1;

    String BikasNum,BikasTranscation,status;

    FirebaseUser firebaseUser;
    DatabaseReference referenceChecking;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bikas_payment);
                ActionBar actionBar=getSupportActionBar();
        actionBar.hide();

        edtbikas=findViewById(R.id.edt_bikas_number);
        edttranscation=findViewById(R.id.edt_bikas_transcation);

        btn_continue1=findViewById(R.id.btn_continue12);


        btn_continue1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BikasNum=edtbikas.getText().toString();
                BikasTranscation=edttranscation.getText().toString();
                status="UnderCheck";


                if (TextUtils.isEmpty(BikasNum) || TextUtils.isEmpty(BikasTranscation)){

                    Toast.makeText(BikasPayment.this,"Input All Information",Toast.LENGTH_LONG).show();
                }

                else {
                    bikasondatabase(BikasNum,BikasTranscation,status);
                }




            }
        });
    }

    private void bikasondatabase(String bikasNum, String bikasTranscation, String status) {


        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        String userID=firebaseUser.getUid();

        referenceChecking= FirebaseDatabase.getInstance().getReference("Riders").child(userID);

        HashMap<String,Object> hashMap=new HashMap<>();

        hashMap.put("BikasNumber",bikasNum);
        hashMap.put("BikasTranscationNumber",bikasTranscation);
        hashMap.put("Status",status);




        referenceChecking.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){

                    startActivity(new Intent(BikasPayment.this, UnderConstruction.class));
                    finish();

                    Toast.makeText(getApplicationContext(),"Sucessfull",Toast.LENGTH_LONG).show();
                }

                else {
                    Toast.makeText(getApplicationContext(),"Not Sucessfull",Toast.LENGTH_LONG).show();
                }

            }
        });

    }
}
