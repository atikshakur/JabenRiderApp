package com.techinnovationltd.jabenriderapp.retrofit;

import com.techinnovationltd.jabenriderapp.passenger.signup.PassengerModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {

    /*
     * Passenger Registration
     * POST passenger information
     */
    @POST("user/register")
    Call<ServerResponse> registerPassenger(@Body PassengerModel passengerModel);


}
