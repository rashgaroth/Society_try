package com.example.selection.list;

import com.example.model.Komunitas;
import com.example.model.KomunitasHobby;

import java.util.List;

public interface CommunityListView {
    void showLoading();
    void hideLoading();
    void onGetResult(List<KomunitasHobby> komunitasList);
    void onGetResultRating(List<Komunitas> komunitas);
    void onErrorLoading(String message);
}
