package com.example.selection.list;

import com.example.apihelper.BaseApiServer;
import com.example.apihelper.RetrofitClient;
import com.example.model.Komunitas;
import com.example.model.KomunitasHobby;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommunityListHobbiesPresenter {
    CommunityListView view;

    public CommunityListHobbiesPresenter(CommunityListView view) {
        this.view = view;
    }

    public void getData(String hobi1, String hobi2, String hobi3, String hobi4, String hobi5, String hobi6, String hobi7){
        view.showLoading();
        RequestBody hobby1 = RequestBody.create(MediaType.parse("text/plain"), hobi1);
        RequestBody hobby2 = RequestBody.create(MediaType.parse("text/plain"), hobi2);
        RequestBody hobby3 = RequestBody.create(MediaType.parse("text/plain"), hobi3);
        RequestBody hobby4 = RequestBody.create(MediaType.parse("text/plain"), hobi4);
        RequestBody hobby5 = RequestBody.create(MediaType.parse("text/plain"), hobi5);
        RequestBody hobby6 = RequestBody.create(MediaType.parse("text/plain"), hobi6);
        RequestBody hobby7 = RequestBody.create(MediaType.parse("text/plain"), hobi7);
        BaseApiServer baseApiServer = RetrofitClient.getApiClient().create(BaseApiServer.class);
        Call<List<KomunitasHobby>> call = baseApiServer.getCommunity(hobby1, hobby2, hobby3, hobby4, hobby5, hobby6, hobby7);
        call.enqueue(new Callback<List<KomunitasHobby>>() {
            @Override
            public void onResponse(Call<List<KomunitasHobby>> call, Response<List<KomunitasHobby>> response) {
                if (response.isSuccessful() && response.body() != null){
                    view.onGetResult(response.body());
                    view.hideLoading();
                }else{
                    view.onErrorLoading("gagal");
                }
            }

            @Override
            public void onFailure(Call<List<KomunitasHobby>> call, Throwable t) {
                    view.onErrorLoading(t.getLocalizedMessage());
                    view.hideLoading();
            }
        });
    }
    public void getRating(){
        view.showLoading();
        BaseApiServer baseApiServer = RetrofitClient.getApiClient().create(BaseApiServer.class);
        Call<List<Komunitas>> call = baseApiServer.getData();
        call.enqueue(new Callback<List<Komunitas>>() {
            @Override
            public void onResponse(Call<List<Komunitas>> call, Response<List<Komunitas>> response) {
                if (response.isSuccessful() && response.body() != null){
                    view.onGetResultRating(response.body());
                    view.hideLoading();
                }else{
                    view.onErrorLoading("gagal");
                }
            }

            @Override
            public void onFailure(Call<List<Komunitas>> call, Throwable t) {
                view.onErrorLoading(t.getLocalizedMessage());
                view.hideLoading();
            }
        });
    }
}
