package com.example.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.controller.Preferences;
import com.example.fragment.profiledata.UserDataPresenter;
import com.example.fragment.profiledata.UserMainView;
import com.example.model.User;
import com.example.society_try.R;

import java.util.List;

public class ProfileFragment extends Fragment implements UserMainView {
    TextView alamat, email, notlp, twitter, facebook;
    public static TextView nama;
    SwipeRefreshLayout sr;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        nama = v.findViewById(R.id.tv_name);
        alamat = v.findViewById(R.id.tv_address);
        email = v.findViewById(R.id.tv_email);
        notlp = v.findViewById(R.id.tv_phone);
        twitter = v.findViewById(R.id.tv_twitter);
        facebook = v.findViewById(R.id.tv_facebook);
        sr = v.findViewById(R.id.swipe_profile);

        final UserDataPresenter userDataPresenter = new UserDataPresenter(this);
        userDataPresenter.getDataUser(Preferences.getLoggedInUser(getContext()));

        sr.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                userDataPresenter.getDataUser(Preferences.getLoggedInUser(getContext()));
            }
        });


        return v;
    }
    public static String nama_user(){
        String nama_user = nama.getText().toString();


        return nama_user;
    }
    @Override
    public void onGetResult(List<User> users) {
        for (int i = 0; i < users.size(); i++){
            nama.setText(users.get(i).getNama_depan());
            alamat.setText(users.get(i).getAlamat());
            email.setText(users.get(i).getEmail());
            notlp.setText(users.get(i).getNo_tlp());
            twitter.setText(users.get(i).getTwitter());
            facebook.setText(users.get(i).getFacebook());
            sr.setRefreshing(false);
        }
    }

    @Override
    public void onGetError(String message) {
            nama.setText(message);
    }

    @Override
    public void onGetSucces(String message) {
            sr.setRefreshing(false);
    }
}
