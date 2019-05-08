package com.techinnovationltd.jabenriderapp.passenger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.techinnovationltd.jabenriderapp.EntryPoint.ProcessMain;
import com.techinnovationltd.jabenriderapp.R;
import com.techinnovationltd.jabenriderapp.passenger.login.Passenger;
import com.techinnovationltd.jabenriderapp.passenger.login.ServerResponseLogin;
import com.techinnovationltd.jabenriderapp.passenger.signup.Registration;
import com.techinnovationltd.jabenriderapp.retrofit.ApiInterface;
import com.techinnovationltd.jabenriderapp.retrofit.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifiedActivity extends AppCompatActivity {

    TextView get_phone;

    FirebaseUser firebaseUser, firebaseUserChecking;
    private String phone;
    DatabaseReference reference, referenceChecking;

    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verified);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        get_phone = findViewById(R.id.txt_get_phone);


        phone = getIntent().getExtras().getString("phones");
        get_phone.setText(phone);
        final String phone_check = get_phone.getText().toString().trim();

//        firebaseUserChecking= FirebaseAuth.getInstance().getCurrentUser();
//        referenceChecking= FirebaseDatabase.getInstance().getReference("Users");
//
//        referenceChecking.orderByChild("Phone").equalTo(phone_check).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                if (dataSnapshot.getValue() != null){
//                    //it means user already registered
//                    //Add code to show your prompt
//                    startActivity(new Intent(VerifiedActivity.this, UnderConstruction.class));
//                    finish();
//
//                }
//
//                else {
//                    Intent intent = new Intent(VerifiedActivity.this, Registration.class);
//                    intent.putExtra("phones", phone_check);
//                    startActivity(intent);
//                    finish();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        apiInterface = ApiUtils.getApiService();

        apiInterface.passengerExist(new Passenger(phone)).enqueue(new Callback<ServerResponseLogin>() {
            @Override
            public void onResponse(Call<ServerResponseLogin> call, Response<ServerResponseLogin> response) {
                ServerResponseLogin res = response.body();

                if(res.getSuccess().equals("1")){
                    Intent intent = new Intent(VerifiedActivity.this, ProcessMain.class);
                    startActivity(intent);
                    finish();
                } else if(res.getSuccess().equals("0")){
                    Intent intent = new Intent(VerifiedActivity.this, Registration.class);
                    intent.putExtra("phone", phone);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ServerResponseLogin> call, Throwable t) {

            }
        });

    }
}
