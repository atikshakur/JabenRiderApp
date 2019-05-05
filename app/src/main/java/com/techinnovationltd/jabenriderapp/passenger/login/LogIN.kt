package com.techinnovationltd.jabenriderapp.passenger.login

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.techinnovationltd.jabenriderapp.ProcessMain
import com.techinnovationltd.jabenriderapp.passenger.signup.EnterPhoneForLogin
import com.techinnovationltd.jabenriderapp.R
import com.techinnovationltd.jabenriderapp.retrofit.ApiInterface
import com.techinnovationltd.jabenriderapp.retrofit.ApiUtils
import kotlinx.android.synthetic.main.activity_log_in.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LogIN : AppCompatActivity() {

    lateinit var apiInterface: ApiInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
        val actionBar = supportActionBar
        actionBar!!.hide()

        apiInterface = ApiUtils.getApiService()

        btn_register.setOnClickListener {
            startActivity(Intent(this, EnterPhoneForLogin::class.java))
        }

        btn_log_in.setOnClickListener {

            var phone: String = login_phone.text.toString()
            var password: String = login_password.text.toString()
            passengerLoginWithCredential(phone, password)
        }

    }

    fun passengerLoginWithCredential(phone: String, password: String){

        var call = apiInterface.loginPassenger(PassengerLoginModel(phone, password, "null"))

        call.enqueue(object: Callback<ServerResponseLogin>{

            override fun onResponse(call: Call<ServerResponseLogin>, response: Response<ServerResponseLogin>) {
                var validity = response.body()
                Toast.makeText(this@LogIN, "blah", Toast.LENGTH_LONG).show()

                if (response.isSuccessful) {
                    Log.d("Login", validity?.message)
                    Log.d("Login", validity?.success)

                    if (validity?.success == "1") {
                        startActivity(Intent(this@LogIN, ProcessMain::class.java))
                        finish()
                    } else if (validity?.success == "0"){
                        Toast.makeText(this@LogIN, validity.message, Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onFailure(call: Call<ServerResponseLogin>, t: Throwable) {
                Toast.makeText(this@LogIN, "Check Your internet connection", Toast.LENGTH_LONG).show()
            }

        })
    }

}
