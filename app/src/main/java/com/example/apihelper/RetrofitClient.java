package com.example.apihelper;

import com.example.Connect;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Connect con = new Connect();
    private static final String BASE_URL = "http://192.168.43.63/society_php/";
    private static Retrofit retrofit;

    public static Retrofit getApiClient(){
        if ( retrofit == null ){
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(con.connectAPI())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}