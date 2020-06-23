package com.example.myartikel;

import com.example.model.MyArtikel;

import java.util.List;

public interface MyArtikelView {
    void showLoading();
    void hideLoading();
    void onGetResult(List<MyArtikel> artikels);
    void onErrorLoading(String message);
}
