package com.example.artikel;

import androidx.annotation.NonNull;

import com.example.apihelper.BaseApiServer;
import com.example.apihelper.RetrofitClient;
import com.example.model.Artikel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditorPresenter {

    private EditorView view;
    private MenuArtikel menuArtikel;

    public EditorPresenter(EditorView view) {
        this.view = view;
    }

    public void save(final String judul, final String deskripsi) {
        view.showProgress();

        BaseApiServer baseApiServer = RetrofitClient.getApiClient().create(BaseApiServer.class);
        Call<Artikel> artikelCall = baseApiServer.save(judul, deskripsi);

        artikelCall.enqueue(new Callback<Artikel>() {
            @Override
            public void onResponse(@NonNull Call<Artikel> call, @NonNull Response<Artikel> response) {
                view.hideProgress();
                if (response.isSuccessful() && response.body() != null){
                    boolean success = response.body().getSuccess();
                    if (success){
                        view.onAddSuccess(response.body().getMessage());

                    }else {
                        view.onAddError(response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Artikel> call,@NonNull Throwable t) {
                view.hideProgress();
                view.onAddError(t.getLocalizedMessage());
            }
        });
    }

//    public void uploadImage(){
//        view.showProgress();
//        menuArtikel = new MenuArtikel();
//        File file = new File(menuArtikel.gambar());
//        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-file"), file);
//        MultipartBody.Part partImage = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
//        BaseApiServer baseApiServer = RetrofitClient.getApiClient().create(BaseApiServer.class);
//        Call<Image> call = baseApiServer.sendImage(partImage);
//        call.enqueue(new Callback<Image>() {
//            @Override
//            public void onResponse(Call<Image> call, Response<Image> response) {
//                if (response.isSuccessful() && response.body() != null){
//                    boolean success = response.body().isSuccess();
//                    if (success){
//                        view.onAddSuccess(response.body().getMessage());
//
//                    }else {
//                        view.onAddError(response.body().getMessage());
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Image> call, Throwable t) {
//
//            }
//        });
//
//   }

}
