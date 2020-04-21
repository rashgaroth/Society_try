package com.example.society_try;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.content.Intent;
import android.widget.TextView;
public class MainActivity extends AppCompatActivity {

    private EditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            email = findViewById(R.id.email);
            password = findViewById(R.id.password);
            password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if(actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_NULL){
                        razia();
                        return true;
                    }
                    return false;
                }
            });
            findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    razia();
                }
            });
            findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getBaseContext(), ActivityRegister.class));
                }
            });
    }
    @Override
    protected void onStart() {
        super.onStart();
        if(Preferences.getLoggedInStatus(getBaseContext())){
            startActivity(new Intent(getApplicationContext(), MainMenu.class));
            finish();
        }
    }

    private void razia() {
        email.setError(null);
        password.setError(null);
        View fokus = null;
        boolean cancel = false;

        String user = email.getText().toString();
        String pass = password.getText().toString();

        if(TextUtils.isEmpty(user)){
            email.setError("Field harus diisi");
            fokus = email;
            cancel = true;
        }else if(!cekUser(user)){
            email.setError("Email/Username sudah digunakan!");
            fokus = email;
            cancel = true;
        }

        if(TextUtils.isEmpty(pass)){
            password.setError("Field harus diisi");
            fokus = password;
            cancel = true;
        }else if(!cekPassword(pass)){
            password.setError("Password Salah!");
            fokus = password;
            cancel = true;
        }
        if(cancel == true){
            fokus.requestFocus();
        }else {
            masuk();
        }
    }

    private void masuk(){
        Preferences.setLoggedInUser(getBaseContext(), Preferences.getRegisteredUser(getBaseContext()));
        Preferences.setLoggedInStatus(getBaseContext(), true);
        startActivity(new Intent(getBaseContext(), MainMenu.class));
        finish();
    }

    private boolean cekPassword(String password){
       return password.equals(Preferences.getRegisteredPass(getBaseContext()));
    }

    private boolean cekUser(String username){
        return username.equals(Preferences.getRegisteredUser(getBaseContext()));
    }
}
