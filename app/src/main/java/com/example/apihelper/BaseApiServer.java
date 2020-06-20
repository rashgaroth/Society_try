package com.example.apihelper;

import com.example.model.Artikel;
import com.example.model.IdUser;
import com.example.model.Image;
import com.example.model.Komunitas;
import com.example.model.User;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface BaseApiServer {
    @Multipart
    @POST("save.php")
    Call<Artikel> save(@Part("judul") RequestBody judul,
                       @Part("deskripsi") RequestBody deskripsi,
                       @Part("author") RequestBody author,
                       @Part MultipartBody.Part body,
                       @Part("user_id") RequestBody user_id
                              );
    @GET("read.php")
    Call<List<Artikel>> getArtikel();
    @GET("get_read_community.php")
    Call<List<Komunitas>> getKomunitas(@Query("key") String keyword);
    @GET("user.php")
    Call<List<User>> getUser(@Query("email") String email);
    @GET("iduser.php")
    Call<List<IdUser>> getIdUser(@Query("nama_depan") String nama_depan);
    @Multipart
    @POST("image_upload.php")
    Call<Image> sendImage(
            @Part MultipartBody.Part body
            );
}
