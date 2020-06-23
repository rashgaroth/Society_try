package com.example.onclick.myartikelselected;

public interface ArtikelView {
    void showLoading();
    void hideLoading();
    void onSucces(String message);
    void onErrorLoading(String message);
}
