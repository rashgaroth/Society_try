package com.example.society_try;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.fragment.HomeFragment;
import com.example.fragment.LoveFragment;
import com.example.fragment.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainMenu extends AppCompatActivity {
    FrameLayout frameLayout;
    ImageView logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        BottomNavigationView bottomnav = findViewById(R.id.bottomNavigationView);
        bottomnav.setOnNavigationItemSelectedListener(navListener);
        frameLayout = (FrameLayout) findViewById(R.id.fragment_layout);


        findViewById(R.id.foto_profil).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Preferences.clearLoggedInUser(getBaseContext());
                startActivity(new Intent(MainMenu.this, LoginActivity.class));
                finish();
            }
        });
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, new LoveFragment()).commit();
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            switch (item.getItemId()){
                case R.id.navigation_love:
                    selectedFragment = new LoveFragment();
                    break;
                case R.id.navigation_home:
                    selectedFragment = new HomeFragment();
                    break;
                case R.id.navigation_profile:
                    selectedFragment = new ProfileFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, selectedFragment)
                    .setCustomAnimations(R.anim.anim_1, R.anim.anim_2,R.anim.anim_1, R.anim.anim_2).commit();
            return true;
        }
    };
    private static final int TIME_INTERVAL = 2000;
    private long tombolKeluar;
    @Override
    public void onBackPressed() {

        if(tombolKeluar + TIME_INTERVAL > System.currentTimeMillis()){
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);
        }else{
            Toast.makeText(getBaseContext(), "TEKAN SEKALI LAGI UNTUK KELUAR", Toast.LENGTH_LONG).show();
        }
        tombolKeluar = System.currentTimeMillis();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar, menu);

        return true;
    }
}
