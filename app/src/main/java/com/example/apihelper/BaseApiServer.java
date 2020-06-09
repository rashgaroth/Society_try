package com.example.apihelper;

import com.example.model.Artikel;
import com.example.model.Image;
import com.example.model.User;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface BaseApiServer {
    @FormUrlEncoded
    @POST("save.php")
    Call<Artikel> save(@Field("judul") String judul,
                       @Field("deskripsi") String deskripsi
                              );
    @GET("read.php")
    Call<List<Artikel>> getArtikel();
    @GET("user.php")
    Call<List<User>> getUser(@Query("email") String email);
    @Multipart
    @POST("image_upload.php")
    Call<Image> sendImage(
            @Part MultipartBody.Part body
            );
}
