package com.example.fragment;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.society_try.R;

public class LoveFragment extends Fragment {
    CardView forums, community, find, article, chat, schedule, achievement, create;
    ScrollView sv;
    AnimationDrawable ad;
    View garis;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_love, container, false);

        article = v.findViewById(R.id.article);
        sv = v.findViewById(R.id.scroll_layout_love);
        ad = (AnimationDrawable) sv.getBackground();
        ad.setEnterFadeDuration(2000);
        ad.setExitFadeDuration(4000);
        ad.start();
        return v;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        article.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Artikel", Toast.LENGTH_LONG).show();
            }
        });
    }
}
