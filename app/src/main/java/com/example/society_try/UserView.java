package com.example.society_try;

import com.example.model.User;

import java.util.List;

public interface UserView {

    void onGetResult(List<User> users);
    void onGetError(String message);
    void onGetSucces(String message);

}
