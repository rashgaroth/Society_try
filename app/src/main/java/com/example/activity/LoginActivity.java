package com.example.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.Connect;
import com.example.controller.Preferences;
import com.example.society_try.MainMenu;
import com.example.society_try.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    public EditText email, password2;
    ProgressBar loading;
    CheckBox cekPw;
    ScrollView nestedScrollView;
    AnimationDrawable ad;
    private static String URL_LOGIN = "http://192.168.43.63/society_php/login.php";

    private static final String TAG = LoginActivity.class.getSimpleName();

    Connect con = new Connect();

    SharedPreferences pref;
    Preferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = new Preferences();

        setContentView(R.layout.activity_login);
        email =findViewById(R.id.email);
        nestedScrollView = findViewById(R.id.nsv);
        password2 =findViewById(R.id.password);
        loading = findViewById(R.id.loading);

        ad = (AnimationDrawable) nestedScrollView.getBackground();
        ad.setEnterFadeDuration(1000);
        ad.setExitFadeDuration(2000);
        ad.start();

//        cekPw.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(cekPw.isChecked()){
//                    password2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//                }else{
//                    password2.setTransformationMethod(PasswordTransformationMethod.getInstance());
//                }
//            }
//        });

        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mEmail = email.getText().toString().trim();
                final String mPassword = password2.getText().toString().trim();
                if (!mEmail.isEmpty() || !mPassword.isEmpty()){
                    login(mEmail, mPassword);
                }else{
                    cek_form(email);
                    cek_form(password2);
                }
            }
        });

        findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ActivityRegister.class));
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(Preferences.getLoggedInStatus(getBaseContext())){
            startActivity(new Intent(LoginActivity.this, MainMenu.class));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }

    void cek_form(EditText edittext){
        edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(email.getText().toString())){
                    email.setBackgroundResource(R.drawable.textfield_error);
                    email.setError("Field harus di isi");
                }else{
                    email.setBackgroundResource(R.drawable.rounded_edittext);
                }
                if (TextUtils.isEmpty(password2.getText().toString())){
                    password2.setBackgroundResource(R.drawable.textfield_error);
                    email.setError("Field harus di isi");
                }else{
                    password2.setBackgroundResource(R.drawable.rounded_edittext);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void login(final String username, final String password){
        loading.setVisibility(View.VISIBLE);
        StringRequest strReq = new StringRequest(Request.Method.POST, con.Login(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    String success = jObj.getString("success");

                    //cek error JSON
                    if (success.equals("1")){
                        Preferences.setLoggedInUser(getBaseContext(), Preferences.getRegisteredUser(getBaseContext()));
                        Preferences.setLoggedInStatus(getBaseContext(),true);
                        Intent intent = new Intent(LoginActivity.this, MainMenu.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("Data_User", email.getText().toString());
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    } else{
                        Toast.makeText(getApplicationContext(), "Email / Password salah!", Toast.LENGTH_LONG).show();
                        loading.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    loading.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Email / Password salah ! || ERROR "+ e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error : "+error.getMessage());
                Toast.makeText(getApplicationContext(), "ERROR "+error.getMessage(), Toast.LENGTH_LONG).show();
                loading.setVisibility(View.GONE);
            }
        })
        {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", username);
                params.put("password", password);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(strReq);


    }
}
