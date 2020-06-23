package com.example.myartikel;

import com.example.apihelper.BaseApiServer;
import com.example.apihelper.RetrofitClient;
import com.example.model.MyArtikel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyArtikelPresenter {
    private MyArtikelView view;

    public MyArtikelPresenter(MyArtikelView view) {
        this.view = view;
    }

    public void getMyArtikel(String id){
        view.showLoading();
        BaseApiServer baseApiServer = RetrofitClient.getApiClient().create(BaseApiServer.class);
        Call<List<MyArtikel>> call = baseApiServer.getMyArtikel(id);
        call.enqueue(new Callback<List<MyArtikel>>() {
            @Override
            public void onResponse(Call<List<MyArtikel>> call, Response<List<MyArtikel>> response) {
                view.hideLoading();
                if (response.isSuccessful() && response.body() != null){
                    view.onGetResult(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<MyArtikel>> call, Throwable t) {
                view.hideLoading();
                view.onErrorLoading(t.getLocalizedMessage());
            }
        });
    }
}
