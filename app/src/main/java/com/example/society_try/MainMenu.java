package com.example.society_try;

import android.annotation.SuppressLint;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.background.JobServices;
import com.example.controller.Preferences;
import com.example.fragment.HomeFragment;
import com.example.fragment.LoveFragment;
import com.example.fragment.ProfileFragment;
import com.example.model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class MainMenu extends AppCompatActivity implements UserView{
    private static final String TAG = "MainMenu";
    FrameLayout frameLayout;
    public static TextView user, emailUser;
    public static TextView society;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        final BottomNavigationView bottomnav = findViewById(R.id.bottomNavigationView);
        bottomnav.setOnNavigationItemSelectedListener(navListener);
        frameLayout = (FrameLayout) findViewById(R.id.fragment_layout);
        user = findViewById(R.id.user_name);
        emailUser = findViewById(R.id.emailUser);
        society = findViewById(R.id.iid);

        user.setText(Preferences.getLoggedInUser(getBaseContext()));

        UserPresenter userPresenter = new UserPresenter(this);
        userPresenter.getDataUser(user.getText().toString());
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, new LoveFragment()).commit();

    }
    public static String nama_user(){
        String nama = user.getText().toString();


        return nama;
    }

    public static String id_user(){
        String idUser = society.getText().toString();

        return idUser;
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
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.fragment_layout, selectedFragment).commit();
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

    @Override
    public void onGetResult(List<User> users) {
        for (int i = 0; i < users.size(); i++){
            String cobaId = String.valueOf(users.get(i).getId());
            user.setText(users.get(i).getNama_depan());
            society.setText(cobaId);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onGetError(String message) {
            user.setText("dewa");
    }

    @Override
    public void onGetSucces(String message) {

    }

    public void scheduleJob(View v){
        ComponentName componentName = new ComponentName(this, JobServices.class);
        JobInfo info = new JobInfo.Builder(123, componentName)
                .setRequiresCharging(true)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setPersisted(true)
                .setPeriodic(15 * 60 * 1000)
                .build();
        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        int resultCode = scheduler.schedule(info);
        if (resultCode == JobScheduler.RESULT_SUCCESS){
            Log.d(TAG, "Shceduled");
        }else{
            Log.d(TAG, "Shceduled failure");
        }
    }

    public void cancleJob(View v){
        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.cancel(123);
        Log.d(TAG, "canceled");
    }
}
