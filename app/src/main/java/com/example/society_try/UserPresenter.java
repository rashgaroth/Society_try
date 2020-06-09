package com.example.society_try;

import com.example.apihelper.BaseApiServer;
import com.example.apihelper.RetrofitClient;
import com.example.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserPresenter {

    UserView view;

    public UserPresenter(UserView view) {
        this.view = view;
    }

    public void getDataUser(String email_user){
        BaseApiServer baseApiServer = RetrofitClient.getApiClient().create(BaseApiServer.class);
        Call<List<User>> call = baseApiServer.getUser(email_user);

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful() && response.body() != null){
                    view.onGetResult(response.body());
                    view.onGetSucces(response.body().toString());
                }else{
                    view.onGetError("gagal");
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                view.onGetError(t.getLocalizedMessage());
            }
        });
    }
}
