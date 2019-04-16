package com.techinnovationltd.jabenriderapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.techinnovationltd.jabenriderapp.Driver.DriverRegistrationActivity;
import com.techinnovationltd.jabenriderapp.Model.User;

import java.time.Year;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class Registration extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton radioButton,passenger,driver;
    Button register;
    String emailPatternt;
    EditText edt_name,edt_email,edt_phone,edt_password;
    TextView birth_date2;

    FirebaseUser firebaseUser,firebaseUserChecking;
    DatabaseReference reference,referenceChecking;

    DatePickerDialog datePickerDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();

        setUpUIView();
        emailPatternt = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        //firebaseUserChecking= FirebaseAuth.getInstance().getCurrentUser();
       // referenceChecking=FirebaseDatabase.getInstance().getReference("Users").child(firebaseUserChecking.getUid());
        String phone=getIntent().getExtras().getString("phones");
        edt_phone.setText(phone);
        String phone_check = edt_phone.getText().toString().trim();


        birth_date2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar calendar=Calendar.getInstance();

                final int year=calendar.get(Calendar.YEAR);
                final int month=calendar.get(Calendar.MONTH);
                final int date=calendar.get(Calendar.DAY_OF_MONTH);


                datePickerDialog=new DatePickerDialog(getApplicationContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year1, int month1, int day1) {

                        birth_date2.setHint("Date of Birth*");
                        birth_date2.setText("");

                        Calendar today = Calendar.getInstance();
                        int age = today.get(Calendar.YEAR) - year1;
                        if (age < 12) {

                            Toast.makeText(getApplicationContext(),"You are under 18",Toast.LENGTH_LONG).show();
                            //do something
                        } else {

                            birth_date2.setText(day1 + "/" + (month1+ 1) + "/" + year1);

                        }



                    }
                },year,month,date);
                datePickerDialog.show();


            }
        });








        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String name,email,phone,birth_date,password;

                name=edt_name.getText().toString();
                email=edt_email.getText().toString();
                phone=edt_phone.getText().toString();
                birth_date=birth_date2.getText().toString();
                password=edt_password.getText().toString();

                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(birth_date) || TextUtils.isEmpty(password)){

                    Toast.makeText(Registration.this,"Input All Information",Toast.LENGTH_LONG).show();
                }

                else if (password.length()<6){
                    Toast.makeText(Registration.this,"password should be more then 6",Toast.LENGTH_LONG).show();


                }

                else if (!email.matches(emailPatternt)){
                    Toast.makeText(Registration.this,"invalid Email",Toast.LENGTH_LONG).show();

                }

                else {
                    Member_registration(name,email,phone,birth_date,password);
                }



            }
        });

    }


    private void setUpUIView(){
        radioGroup=findViewById(R.id.check_service_type);
        register=findViewById(R.id.btn_register);
        passenger=findViewById(R.id.radio_passenger);
        driver=findViewById(R.id.radio_driver);

        edt_name=findViewById(R.id.etxt_full_name);
        edt_email=findViewById(R.id.etxt_email);
        birth_date2=findViewById(R.id.etxt_dob);
        edt_phone=findViewById(R.id.etxt_mobile);
        edt_password=findViewById(R.id.etxt_password);


    }


    private void Member_registration(String name, String email, String phone, String birth, String password){


        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        String userID=firebaseUser.getUid();

        reference= FirebaseDatabase.getInstance().getReference("Users").child(userID);

        HashMap<String,Object> hashMap=new HashMap<>();

        hashMap.put("id",userID);
        hashMap.put("Name",name);
        hashMap.put("Email",email);
        hashMap.put("Phone",phone);
        hashMap.put("Birth_Date",birth);
        hashMap.put("Password",password);
        hashMap.put("Service_Type","Passenger");
        hashMap.put("image_url","Defalut");


        reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){

                    startActivity(new Intent(Registration.this,UnderConstruction.class));
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
