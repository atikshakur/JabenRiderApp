package com.techinnovationltd.jabenriderapp.retrofit;

public class ApiUtils {
    private ApiInterface apiInterface;

    public static ApiInterface getApiService(){
        return RetrofitClient.getInstance().create(ApiInterface.class);
    }
}
