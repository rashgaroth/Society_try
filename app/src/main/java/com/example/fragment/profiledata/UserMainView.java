package com.example.fragment.profiledata;

import com.example.model.User;

import java.util.List;

public interface UserMainView {
    void onGetResult(List<User> users);
    void onGetError(String message);
    void onGetSucces(String message);
}
