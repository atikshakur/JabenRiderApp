package com.techinnovationltd.jabenriderapp.retrofit;

import com.techinnovationltd.jabenriderapp.passenger.login.PassengerLoginModel;
import com.techinnovationltd.jabenriderapp.passenger.login.ServerResponseLogin;
import com.techinnovationltd.jabenriderapp.passenger.signup.PassengerModel;
import com.techinnovationltd.jabenriderapp.passenger.signup.ServerResponseSignup;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {

    /*
     * Passenger Registration
     * POST passenger information
     */
    @POST("user/register")
    Call<ServerResponseSignup> registerPassenger(@Body PassengerModel passengerModel);


    /*
     * Passenger Login
     */
    @POST("user/login")
    Call<ServerResponseLogin> loginPassenger(@Body PassengerLoginModel passengerLoginModel);

}
