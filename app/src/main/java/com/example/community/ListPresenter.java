package com.example.community;

import com.example.apihelper.BaseApiServer;
import com.example.apihelper.RetrofitClient;
import com.example.model.Komunitas;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListPresenter {
    private ListView view;

    public ListPresenter(ListView view) {
        this.view = view;
    }

    public void getKomunitas(String keyword){
        view.showLoading();
        BaseApiServer baseApiServer = RetrofitClient.getApiClient().create(BaseApiServer.class);
        Call<List<Komunitas>> call = baseApiServer.getKomunitas(keyword);
        call.enqueue(new Callback<List<Komunitas>>() {
            @Override
            public void onResponse(Call<List<Komunitas>> call, Response<List<Komunitas>> response) {
                view.hideLoading();
                if(response.isSuccessful() && response.body() != null){
                    view.onGetResult(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Komunitas>> call, Throwable t) {
                view.hideLoading();
                view.onErrorLoading(t.getLocalizedMessage());
            }
        });
    }
}
