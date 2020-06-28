package com.example.fragment.recycleview;

import com.example.model.Artikel;
import com.example.model.MyArtikel;

import java.util.List;

public interface MainView {
    void showLoading();
    void hideLoading();
    void onGetResult(List<Artikel> artikels);
    void onErrorLoading(String message);
}
