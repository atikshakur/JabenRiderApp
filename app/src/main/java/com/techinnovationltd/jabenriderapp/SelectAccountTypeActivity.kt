package com.techinnovationltd.jabenriderapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.techinnovationltd.jabenriderapp.Driver.LogInWithPhoneNumberActivityDriver
import com.techinnovationltd.jabenriderapp.Driver.MapsActivityDriver
import kotlinx.android.synthetic.main.activity_select_account_type.*

//---2---
class SelectAccountTypeActivity : AppCompatActivity() {

    private var btn_passenger: Button? = null
    private var btn_driver: Button? = null

    internal var firebaseUser: FirebaseUser? = null
    internal var referenceChecking: DatabaseReference? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_account_type)

        val actionBar = supportActionBar
        actionBar!!.hide()

        btn_passenger_lets_go.setOnClickListener {
            val intent = Intent(this, LogInWithPhoneNumberActivity::class.java)
            startActivity(intent)
        }

        btn_driver_lets_go.setOnClickListener {
            startActivity(Intent(this, LogInWithPhoneNumberActivityDriver::class.java))
        }
    }
}
