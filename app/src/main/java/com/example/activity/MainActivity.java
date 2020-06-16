package com.example.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.society_try.MainMenu;
import com.example.society_try.R;

public class MainActivity extends AppCompatActivity {

    private EditText email, password;
    String getEmail = "", getPassword = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("login Data", MODE_PRIVATE);
                SharedPreferences.Editor edit = pref.edit();
                getEmail = pref.getString("email", null);
                getPassword = pref.getString("password", null);

                if (getEmail == null) {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                } else {
                    startActivity(new Intent(getApplicationContext(), MainMenu.class));
                }
                finish();
            }
        }, 3000);
    }
}
