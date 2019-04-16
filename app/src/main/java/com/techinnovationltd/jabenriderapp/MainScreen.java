package com.techinnovationltd.jabenriderapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainScreen extends AppCompatActivity {

    FirebaseUser firebaseUser;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        reference=FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainScreen.this,SelectAccountTypeActivity.class));
                finish();
                return  true;

        }
        return false;
    }
}
