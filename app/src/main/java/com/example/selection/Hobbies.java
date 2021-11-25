package com.example.selection;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.society_try.R;

public class Hobbies extends AppCompatActivity {
    Button sports, music, social, automotive, nature, arts, technology, next;
    Button c1, c2, c3, c4, c5, c6, c7;
    ScrollView sv;
    ProgressBar pb;
    ImageView img;
    AnimatorSet animatorSet;
    public static String hobi1= "";
    public static String hobi2= "";
    public static String hobi3= "";
    public static String hobi4= "";
    public static String hobi5= "";
    public static String hobi6= "";
    public static String hobi7= "";
    private boolean hilang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hobbies);

        //memilih
        sports = findViewById(R.id.sport_button);
        music = findViewById(R.id.music_button);
        social = findViewById(R.id.social_button);
        automotive = findViewById(R.id.automotive_button);
        nature = findViewById(R.id.nature_button);
        arts = findViewById(R.id.arts_button);
        technology = findViewById(R.id.technology_button);
        img = findViewById(R.id.img_cs);
        next = findViewById(R.id.next);
        sv = findViewById(R.id.scroll_hobbies);
        //clear button
        c1 = findViewById(R.id.clear1);
        c2 = findViewById(R.id.clear2);
        c3 = findViewById(R.id.clear3);
        c4 = findViewById(R.id.clear4);
        c5 = findViewById(R.id.clear5);
        c6 = findViewById(R.id.clear6);
        c7 = findViewById(R.id.clear7);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (animatorSet == null){
                    animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(Hobbies.this, R.animator.loading_anim);
                    animatorSet.setTarget(img);
                }
                if (animatorSet.isRunning()){
                    animatorSet.cancel();
                }else{
                    animatorSet.start();
                    startActivity(new Intent(Hobbies.this, HobbySelected.class));
                    finish();
                }
            }
        });

        sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animate2(sports, c1, v);
                hobi1 = "5";
            }
        });

        music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                music.setBackgroundResource(R.drawable.button_hobbies);
                c2.setVisibility(View.VISIBLE);
                hobi2 = "1";
            }
        });

        social.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                social.setBackgroundResource(R.drawable.button_hobbies);
                c3.setVisibility(View.VISIBLE);
                hobi3 = "2";
            }
        });

        automotive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                automotive.setBackgroundResource(R.drawable.button_hobbies);
                c4.setVisibility(View.VISIBLE);
                hobi4 = "3";
            }
        });

        nature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nature.setBackgroundResource(R.drawable.button_hobbies);
                c5.setVisibility(View.VISIBLE);
                hobi5 = "4";
            }
        });

        arts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arts.setBackgroundResource(R.drawable.button_hobbies);
                c6.setVisibility(View.VISIBLE);
                hobi6 = "7";
            }
        });

        technology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                technology.setBackgroundResource(R.drawable.button_hobbies);
                c7.setVisibility(View.VISIBLE);
                hobi7 = "8";
            }
        });

        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animate(sports, c1, v);
                hobi1 = "";
            }
        });
        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animate(music, c2, v);
                hobi2 = "";
            }
        });
        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animate(social, c3, v);
                hobi3 = "";
            }
        });
        c4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animate(automotive, c4, v);
                hobi4 = "";
            }
        });
        c5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animate(nature, c5, v);
                hobi5 = "";
            }
        });
        c6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animate(arts, c6, v);
                hobi6 = "";
            }
        });
        c7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animate(technology, c7, v);
                hobi7 = "";
            }
        });
    }
    private void animate(final Button b, final Button c, View v){
        v.setAlpha(0.5f);
        v.animate()
                .translationY(0)
                .alpha(0)
                .setDuration(300)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        hilang = true;
                        b.setBackgroundResource(R.drawable.button_hobbies_pressed);
                        c.setVisibility(View.GONE);
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void animate2(final Button b, final Button c, View v){
                        hilang = true;
                        b.setBackgroundResource(R.drawable.button_hobbies);
                        c.setVisibility(View.VISIBLE);
    }
}
