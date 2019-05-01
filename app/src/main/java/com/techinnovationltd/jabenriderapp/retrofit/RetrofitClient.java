package com.techinnovationltd.jabenriderapp.retrofit;

import java.io.IOException;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    public static String BASE_URL = "http://helplaa.com/jaben/public/";

    public static Retrofit retrofit;

    public static Retrofit getInstance(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }


    // With logging
    public static Retrofit getRetrofit() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        AuthenticationInterceptor interceptor = new AuthenticationInterceptor(
                Credentials.basic("", ""));

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL);


        if (!httpClient.interceptors().contains(interceptor)) {
            httpClient.addInterceptor(interceptor);
            httpClient.addInterceptor(logging);
            builder.client(httpClient.build());
            retrofit = builder
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

        }

        return retrofit;
    }

    public static class AuthenticationInterceptor implements Interceptor {

        private String authToken;

        public AuthenticationInterceptor(String token) {
            this.authToken = token;
        }

        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request original = chain.request();

            Request.Builder builder = original.newBuilder()
                    .header("Authorization", authToken);//Remember header() vs addHeader

            Request request = builder.build();
            return chain.proceed(request);
        }
    }


}
