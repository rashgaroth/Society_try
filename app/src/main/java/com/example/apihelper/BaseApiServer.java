package com.example.apihelper;

import com.example.model.LoginResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface BaseApiServer {
    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> login(@Field("email") String email,
                                    @Field("password") String password);

    @FormUrlEncoded
    @POST("register")
    Call<ResponseBody> registerRequest(@Field("namaDepan") String nama_depan,
                                       @Field("namaBelakang") String nama_belakang,
                                       @Field("email_register") String email,
                                       @Field("password_register") String password);

}
