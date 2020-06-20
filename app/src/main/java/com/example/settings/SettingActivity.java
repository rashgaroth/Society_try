package com.example.settings;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.activity.LoginActivity;
import com.example.controller.Preferences;
import com.example.society_try.MainMenu;
import com.example.society_try.R;

public class SettingActivity extends AppCompatActivity {
    Button infoAkun, logout;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        infoAkun = findViewById(R.id.info_akun);
        logout = findViewById(R.id.logout);
        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, MainMenu.class));
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
                builder.setCancelable(false);
                builder.setMessage("Ingin Logout?");
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Preferences.clearLoggedInUser(getBaseContext());
                        Preferences.setRegisteredUser(getBaseContext(), null);
                        Preferences.setLoggedInUser(getBaseContext(), null);
                        Preferences.setRegisteredPass(getBaseContext(), null);
                        finish();
                        startActivity(new Intent(SettingActivity.this, LoginActivity.class));
                    }
                });
                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }
}
