package com.example.community;

import com.example.model.Komunitas;

import java.util.List;

public interface ListView {
    void showLoading();
    void hideLoading();
    void onGetResult(List<Komunitas> komunitasList);
    void onErrorLoading(String message);
}
