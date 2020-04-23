package com.example.society_try;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ActivityRegister extends AppCompatActivity {
    private EditText namaDepan, namaBelakang, email_register, password_register, ulangi;
    ProgressDialog pDialog;
    private static String URL_REGIST = "http://192.168.43.63/society_php/register.php";
    String success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        namaDepan = findViewById(R.id.namaDepan);
        namaBelakang = findViewById(R.id.namaBelakang);
        email_register = findViewById(R.id.email_register);
        password_register = findViewById(R.id.password_register);
        ulangi = findViewById(R.id.rePassword);
       findViewById(R.id.buttonRegister).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(TextUtils.isEmpty(namaDepan.getText().toString())){
                   showDialog();
               }else{
                   register();
               }
           }
       });
    }

    private void register() {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(true);
        pDialog.setMessage("Mencari data, silahkan tunggu... ");

        final String nama_depan = namaDepan.getText().toString().trim();
        final String nama_belakang = namaBelakang.getText().toString().trim();
        final String email = email_register.getText().toString().trim();
        final String password = password_register.getText().toString().trim();
        final String repassword = ulangi.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            success = jsonObject.optString("success");

                            if(success.equals("1")){
                                if(repassword.equals(password)){
                                    Toast.makeText(ActivityRegister.this, "sukses", Toast.LENGTH_LONG).show();
                                    pDialog.dismiss();
                                    startActivity(new Intent(ActivityRegister.this, LoginActivity.class));
                                }else{
                                    ulangi.setError("Penulisan Password harus sama");
                                    ulangi.setBackgroundResource(R.drawable.textfield_error);
                                    if (repassword.equals("")){

                                    }
                                }
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                    new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "sukses", Toast.LENGTH_LONG).show();
                        pDialog.dismiss();
                    }
                })
            {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("namaDepan", nama_depan);
                params.put("namaBelakang", nama_belakang);
                params.put("email_register", email);
                params.put("password_register", password);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void showDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title dialog
        alertDialogBuilder.setTitle("Data harus di isi!");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Hati gebetanmu aja udah di isi sama orang\nmasa data kamu engga")
                .setCancelable(false)
                .setNegativeButton("Oke",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
    }
}
