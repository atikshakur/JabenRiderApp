package com.techinnovationltd.jabenriderapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LogIN extends AppCompatActivity implements View.OnClickListener {

    private Button btn_reg,btn_log;
    private TextView txt_forget_pass;
    private EditText edt_phone,edt_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();

        SetupUIView();
        btn_reg.setOnClickListener(this);
        btn_log.setOnClickListener(this);
    }


    private void SetupUIView(){

        btn_log=findViewById(R.id.btn_log_in);
        btn_reg=findViewById(R.id.btn_register);
        edt_phone=findViewById(R.id.login_phone);
        edt_pass=findViewById(R.id.loginpassword);
        txt_forget_pass=findViewById(R.id.forgot_password);

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_register:
                startActivity(new Intent(this,EnterPhoneForLogin.class));
                break;

        }

    }
}
