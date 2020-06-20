package com.example.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.controller.Preferences;
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
                String getData = Preferences.getLoggedInUser(getBaseContext());

                if (getData.isEmpty()) {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                } else {
                    startActivity(new Intent(getApplicationContext(), MainMenu.class));
                }
                finish();
            }
        }, 1000);
    }

}
