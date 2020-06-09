package com.example.fragment;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.artikel.MenuArtikel;
import com.example.society_try.R;

public class LoveFragment extends Fragment {
    CardView forums, community, find, articlee, chat, schedule, achievement, create;
    ScrollView sv;
    AnimationDrawable ad;
    View garis;
    Button pindah;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_love, container, false);

        articlee = v.findViewById(R.id.article);
        sv = v.findViewById(R.id.scroll_layout_love);

        articlee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MenuArtikel.class));
            }
        });

        ad = (AnimationDrawable) sv.getBackground();
        ad.setEnterFadeDuration(2000);
        ad.setExitFadeDuration(4000);
        ad.start();

        return v;

    }
}
