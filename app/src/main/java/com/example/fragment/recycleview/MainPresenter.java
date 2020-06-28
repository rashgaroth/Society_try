package com.example.fragment.recycleview;

import com.example.apihelper.BaseApiServer;
import com.example.apihelper.RetrofitClient;
import com.example.model.Artikel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter {

    private MainView view;

    public MainPresenter(MainView view) {
        this.view = view;
    }

    public void getData() {
        view.showLoading();

        BaseApiServer baseApiServer = RetrofitClient.getApiClient().create(BaseApiServer.class);
        Call<List<Artikel>> call = baseApiServer.getArtikel();
        call.enqueue(new Callback<List<Artikel>>() {
            @Override
            public void onResponse(Call<List<Artikel>> call, Response<List<Artikel>> response) {
                view.hideLoading();
                view.onGetResult(response.body());
            }

            @Override
            public void onFailure(Call<List<Artikel>> call, Throwable t) {
                view.onErrorLoading(t.getLocalizedMessage());
            }
        });

    }

}
