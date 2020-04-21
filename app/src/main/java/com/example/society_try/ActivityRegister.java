package com.example.society_try;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

public class ActivityRegister extends AppCompatActivity {
    private EditText namaDepan, namaBelakang, email_register, password_register, ulangi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        namaDepan = findViewById(R.id.namaDepan);
        namaBelakang = findViewById(R.id.namaBelakang);
        email_register = findViewById(R.id.email_register);
        password_register = findViewById(R.id.password_register);
        ulangi = findViewById(R.id.rePassword);

        ulangi.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_NULL){
                    razia();
                    return true;
                }
                return false;
            }
        });
        findViewById(R.id.buttonRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                razia();
            }
        });
    }

    private void razia() {
        namaDepan.setError(null);
        namaBelakang.setError(null);
        email_register.setError(null);
        password_register.setError(null);
        ulangi.setError(null);
        View fokus = null;
        boolean cancel = false;

        String repassword = ulangi.getText().toString();
        String nmdepan = namaDepan.getText().toString();
        String nmbelakang = namaBelakang.getText().toString();
        String email = email_register.getText().toString();
        String pass = password_register.getText().toString();

        if(TextUtils.isEmpty(nmdepan)){
            namaDepan.setError("Field harus diisi");
            fokus = namaDepan;
            cancel = true;
        }else if(cekUser(nmdepan)){
            namaDepan.setError(("Username sudah ada sebelumnya"));
            fokus = namaDepan;
            cancel = true;
        }
        if (TextUtils.isEmpty(nmbelakang)){
            namaBelakang.setError("Field harus diisi");
            fokus = namaBelakang;
            cancel = true;
        }
        if (TextUtils.isEmpty(email)){
            email_register.setError("Email harus diisi");
            fokus = email_register;
            cancel = true;
        }
        if(TextUtils.isEmpty(pass)){
            password_register.setError("Field harus diisi");
            fokus = password_register;
            cancel = true;
        }else if(!cekPassword(pass, repassword)){
            password_register.setError("Password tidak sama");
            fokus = password_register;
            cancel = true;
        }
        if(cancel){
            fokus.requestFocus();
        }else{
            Preferences.setRegisteredUser(getBaseContext(), nmdepan);
            Preferences.setRegisteredPass(getBaseContext(), pass);
            finish();
        }
    }
    private boolean cekPassword(String pass, String repassword){
        return pass.equals(repassword);
    }

    private boolean cekUser(String nmdepan){
        return nmdepan.equals(Preferences.getRegisteredUser(getBaseContext()));
    }
}
