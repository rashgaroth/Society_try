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
                    //edit.clear();
                    //edit.commit();
                    //buat debugging
                    getEmail = pref.getString("email",null);
                    getPassword = pref.getString("password", null);

                    if(getEmail == null){
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    }else{
                        startActivity(new Intent(getApplicationContext(), MainMenu.class));
                    }
                    finish();
                }
            }, 3000);
//            password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//                @Override
//                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                    if(actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_NULL){
//                        razia();
//                        return true;
//                    }
//                    return false;
//                }
//            });
//            findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    razia();
//                }
//            });
//            findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    startActivity(new Intent(getBaseContext(), ActivityRegister.class));
//                }
//            });
    }
//    @Override
//    protected void onStart() {
//        super.onStart();
//        if(Preferences.getLoggedInStatus(getBaseContext())){
//            startActivity(new Intent(getApplicationContext(), MainMenu.class));
//            finish();
//        }
//    }
//
//    private void razia() {
//        email.setError(null);
//        password.setError(null);
//        View fokus = null;
//        boolean cancel = false;
//
//        String user = email.getText().toString();
//        String pass = password.getText().toString();
//
//        if(TextUtils.isEmpty(user)){
//            email.setError("Field harus diisi");
//            fokus = email;
//            cancel = true;
//        }else if(!cekUser(user)){
//            email.setError("Email/Username sudah digunakan!");
//            fokus = email;
//            cancel = true;
//        }
//
//        if(TextUtils.isEmpty(pass)){
//            password.setError("Field harus diisi");
//            fokus = password;
//            cancel = true;
//        }else if(!cekPassword(pass)){
//            password.setError("Password Salah!");
//            fokus = password;
//            cancel = true;
//        }
//        if(cancel == true){
//            fokus.requestFocus();
//        }else {
//            masuk();
//        }
//    }
//
//    private void masuk(){
//        Preferences.setLoggedInUser(getBaseContext(), Preferences.getRegisteredUser(getBaseContext()));
//        Preferences.setLoggedInStatus(getBaseContext(), true);
//        startActivity(new Intent(getBaseContext(), MainMenu.class));
//        finish();
//    }
//
//    private boolean cekPassword(String password){
//       return password.equals(Preferences.getRegisteredPass(getBaseContext()));
//    }
//
//    private boolean cekUser(String username){
//        return username.equals(Preferences.getRegisteredUser(getBaseContext()));
//    }
}
