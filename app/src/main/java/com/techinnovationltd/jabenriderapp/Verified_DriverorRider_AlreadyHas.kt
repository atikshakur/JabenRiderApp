package com.techinnovationltd.jabenriderapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.techinnovationltd.jabenriderapp.passenger.login.LogIN
import com.techinnovationltd.jabenriderapp.passenger.signup.EnterPhoneForLogin

class Verified_DriverorRider_AlreadyHas : AppCompatActivity() {

    internal var firebaseUser: FirebaseUser? = null
    internal var firebaseUserChecking: FirebaseUser? = null
    internal var reference: DatabaseReference? = null
    internal var referenceChecking: DatabaseReference? = null


    override fun onStart() {


        firebaseUser = FirebaseAuth.getInstance().currentUser

        if (firebaseUser != null) {

            startActivity(Intent(this, ProcessMain::class.java))
            finish()

        } else {
            startActivity(Intent(this, EnterPhoneForLogin::class.java))
            finish()
        }

        super.onStart()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verified)

        val actionBar = supportActionBar
        actionBar!!.hide()
    }
}
