package com.example.onclick.myartikelselected;

import com.example.apihelper.BaseApiServer;
import com.example.apihelper.RetrofitClient;
import com.example.model.UpdateMyArtikel;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArtikelPresenter {
    private ArtikelView view;

    public ArtikelPresenter(ArtikelView view) {
        this.view = view;
    }

    public void updateData(String id, String judul, String Deskripsi){
        view.showLoading();

        String auth = id;
        String title = judul;
        String desc = Deskripsi;

        RequestBody utama = RequestBody.create(MediaType.parse("text/plain"), title);
        RequestBody isi = RequestBody.create(MediaType.parse("text/plain"), desc);
        RequestBody nama = RequestBody.create(MediaType.parse("text/plain"), auth);
        BaseApiServer baseApiServer = RetrofitClient.getApiClient().create(BaseApiServer.class);
        Call<List<UpdateMyArtikel>> call = baseApiServer.updatePost(nama, utama, isi);
        call.enqueue(new Callback<List<UpdateMyArtikel>>() {
            @Override
            public void onResponse(Call<List<UpdateMyArtikel>> call, Response<List<UpdateMyArtikel>> response) {
                if (response.isSuccessful() && response.body() != null){
                    view.hideLoading();
                    view.onSucces(response.body().toString());
                }else{
                    view.hideLoading();
                    view.onErrorLoading(response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<List<UpdateMyArtikel>> call, Throwable t) {

            }
        });
    }
}
