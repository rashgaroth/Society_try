package com.example.apihelper;

import com.example.model.Artikel;
import com.example.model.Komunitas;
import com.example.model.MyArtikel;
import com.example.model.UpdateMyArtikel;
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
    @GET("read_myartikel.php")
    Call<List<MyArtikel>> getMyArtikel(@Query("id") String id);
    @GET("get_read_community.php")
    Call<List<Komunitas>> getKomunitas(@Query("key") String keyword);
    @GET("user.php")
    Call<List<User>> getUser(@Query("email") String email);
    @Multipart
    @POST("update.php")
    Call<List<UpdateMyArtikel>> updatePost(@Part("id") RequestBody id,
                                     @Part("judul") RequestBody judul,
                                     @Part("deskripsi") RequestBody deskripsi);
    @GET("hapus.php")
    Call<List<UpdateMyArtikel>> hapusPost(@Query("id") String id);
}
