package com.example.society_try;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        TextView tv = findViewById(R.id.welcome);
        tv.setText("Selamat Datang " + Preferences.getLoggedInUser(getBaseContext()));
        findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Preferences.clearLoggedInUser(getBaseContext());
                startActivity(new Intent(getBaseContext(), LoginActivity.class));
                finish();
            }
        });
    }
}
