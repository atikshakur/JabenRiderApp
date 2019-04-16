package com.techinnovationltd.jabenriderapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

public class Splash_Screen extends AppCompatActivity {

    private Button btn_get;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__screen);

        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();

        btn_get = findViewById(R.id.btn_get_started);

        btn_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Splash_Screen.this, Verified_DriverorRider_AlreadyHas.class);
                startActivity(intent);

            }
        });


    }
}
