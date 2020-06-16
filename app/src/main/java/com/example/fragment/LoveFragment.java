package com.example.fragment;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.artikel.MenuArtikel;
import com.example.society_try.R;
import com.example.view_pager.Adapter;
import com.example.view_pager.Model;

import java.util.ArrayList;
import java.util.List;

public class LoveFragment extends Fragment {
    public static ViewPager viewPager;
    Adapter adapter;
    List<Model> models;
    Integer[] colors = null;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    Handler slideHandler = new Handler();

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

        models = new ArrayList<>();
        models.add(new Model(R.drawable.gambar1, "Community is funny"));
        models.add(new Model(R.drawable.gambar2, "Create your choices"));
        models.add(new Model(R.drawable.gambar3, "Talk with friends"));
        models.add(new Model(R.drawable.gambar4, "Create idea"));

        adapter = new Adapter(models, getContext());
        viewPager = v.findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(130, 0, 130, 0);

        Integer[] color_temp = {getResources().getColor(R.color.color4),
                getResources().getColor(R.color.color1),
                getResources().getColor(R.color.color2),
                getResources().getColor(R.color.color3)
        };

        colors = color_temp;

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                slideHandler.removeCallbacks(Adapter.slide);
                slideHandler.postDelayed(Adapter.slide, 2000);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return v;

    }
}
