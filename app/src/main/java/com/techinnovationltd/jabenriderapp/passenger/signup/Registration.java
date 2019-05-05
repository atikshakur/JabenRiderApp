package com.techinnovationltd.jabenriderapp.passenger.signup;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.techinnovationltd.jabenriderapp.ProcessMain;
import com.techinnovationltd.jabenriderapp.R;
import com.techinnovationltd.jabenriderapp.retrofit.ApiInterface;
import com.techinnovationltd.jabenriderapp.retrofit.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Registration extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton radioButton, passenger, driver;
    Button register;
    String emailPatternt, phone;
    EditText edt_name, edt_email, edt_phone, edt_password;
    TextView birth_date2;

    FirebaseUser firebaseUser, firebaseUserChecking;
    DatabaseReference reference, referenceChecking;

    DatePickerDialog datePickerDialog;
    ApiInterface apiInterface;
    private static final String TAG = "Registration";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        apiInterface = ApiUtils.getApiService();

        setUpUIView();
        emailPatternt = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        //firebaseUserChecking= FirebaseAuth.getInstance().getCurrentUser();
        // referenceChecking=FirebaseDatabase.getInstance().getReference("Users").child(firebaseUserChecking.getUid());
        phone = getIntent().getExtras().getString("phones");
        edt_phone.setText(phone);
        String phone_check = edt_phone.getText().toString().trim();


//        birth_date2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                final Calendar calendar = Calendar.getInstance();
//
//                final int year = calendar.get(Calendar.YEAR);
//                final int month = calendar.get(Calendar.MONTH);
//                final int date = calendar.get(Calendar.DAY_OF_MONTH);
//
//
//                datePickerDialog = new DatePickerDialog(getApplicationContext(), new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker datePicker, int year1, int month1, int day1) {
//
//                        birth_date2.setHint("Date of Birth*");
//                        birth_date2.setText("");
//
//                        Calendar today = Calendar.getInstance();
//                        int age = today.get(Calendar.YEAR) - year1;
//                        if (age < 12) {
//
//                            Toast.makeText(getApplicationContext(), "You are under 18", Toast.LENGTH_LONG).show();
//                            //do something
//                        } else {
//
//                            birth_date2.setText(day1 + "/" + (month1 + 1) + "/" + year1);
//
//                        }
//
//
//                    }
//                }, year, month, date);
//                datePickerDialog.show();
//
//
//            }
//        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name, password, nid, email, device_id;

                name = edt_name.getText().toString();
                password = edt_password.getText().toString();
                nid = "null";
                email = edt_email.getText().toString();
                device_id = "null";
                PassengerModel passenger = new PassengerModel(name, password, nid, phone, email, device_id);
                //PassengerModel passenger = new PassengerModel("name", "password", "nid", "phone", "email", "device_id");

                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(password)) {
                    Toast.makeText(Registration.this, "Input All Information", Toast.LENGTH_LONG).show();
                } else if (password.length() < 6) {
                    Toast.makeText(Registration.this, "password should be more then 6", Toast.LENGTH_LONG).show();
                } else if (!email.matches(emailPatternt)) {
                    Toast.makeText(Registration.this, "invalid Email", Toast.LENGTH_LONG).show();
                } else {
                    //Member_registration(name, email, phone, birth_date, password);
                    passengerRegistration(passenger);
                }
            }
        });

    }


    private void setUpUIView() {
        radioGroup = findViewById(R.id.check_service_type);
        register = findViewById(R.id.btn_register);
        passenger = findViewById(R.id.radio_passenger);
        driver = findViewById(R.id.radio_driver);

        edt_name = findViewById(R.id.etxt_full_name);
        edt_email = findViewById(R.id.etxt_email);
        //birth_date2 = findViewById(R.id.etxt_dob);
        edt_phone = findViewById(R.id.etxt_mobile);
        edt_password = findViewById(R.id.etxt_password);


    }

    private void passengerRegistration(PassengerModel passenger) {

        Call<ServerResponseSignup> call = apiInterface.registerPassenger(passenger);

        call.enqueue(new Callback<ServerResponseSignup>() {
            @Override
            public void onResponse(Call<ServerResponseSignup> call, Response<ServerResponseSignup> response) {

                ServerResponseSignup validity = response.body();

                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse: Success" + validity.getSuccess());
                    Log.d(TAG, "onResponse: Message" + validity.getMessage());

                    if (validity.getSuccess().equals("1")) {
                        startActivity(new Intent(Registration.this, ProcessMain.class));
                        finish();
                    } else if (validity.getSuccess().equals("0")) {
                        Toast.makeText(Registration.this, validity.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ServerResponseSignup> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                Toast.makeText(Registration.this, "Registration failed", Toast.LENGTH_SHORT).show();
            }
        });
    }


//    private void Member_registration(String name, String email, String phone, String birth, String password) {
//
//
//        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//        String userID = firebaseUser.getUid();
//
//        reference = FirebaseDatabase.getInstance().getReference("Users").child(userID);
//
//        HashMap<String, Object> hashMap = new HashMap<>();
//
//        hashMap.put("id", userID);
//        hashMap.put("Name", name);
//        hashMap.put("Email", email);
//        hashMap.put("Phone", phone);
//        hashMap.put("Birth_Date", birth);
//        hashMap.put("Password", password);
//        hashMap.put("Service_Type", "Passenger");
//        hashMap.put("image_url", "Defalut");
//
//
//        reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//
//                if (task.isSuccessful()) {
//
//                    startActivity(new Intent(Registration.this, UnderConstruction.class));
//                    finish();
//
//                    Toast.makeText(getApplicationContext(), "Sucessfull", Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(getApplicationContext(), "Not Sucessfull", Toast.LENGTH_LONG).show();
//                }
//
//            }
//        });
//
//    }

}
