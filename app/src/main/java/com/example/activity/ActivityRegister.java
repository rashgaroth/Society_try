package com.example.activity;

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
import com.example.Connect;
import com.example.society_try.R;

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
                   String pesan = "Nama depan harus di isi!";
                   String isi = "Nama depan harus di isi. Contoh : ujang, maman, cecep";
                   showDialog(pesan, isi);
               }else if(TextUtils.isEmpty(namaBelakang.getText().toString())){
                   String pesan = "Nama belakang harus di isi!";
                   String isi = "Nama belakang harus di isi. Contoh : maemunah, priyono, marcecep";
                   showDialog(pesan, isi);
               }else if(TextUtils.isEmpty(email_register.getText().toString())){
                   String pesan = "Email harus di isi!";
                   String isi = "Email harus di isi. Contoh : ujang@gmail.com";
                   showDialog(pesan, isi);
               }else if(TextUtils.isEmpty(password_register.getText().toString())){
                   String pesan = "Password harus di isi!";
                   String isi = "Password harus di isi. Contoh : ******";
                   showDialog(pesan, isi);
               }
               else{
                   register();
               }
           }
       });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Gagal Registrasi?");
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(ActivityRegister.this, LoginActivity.class));
            }
        });
        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void register() {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(true);
        pDialog.setMessage("silahkan tunggu... ");

        Connect con = new Connect();
        final String nama_depan = namaDepan.getText().toString().trim();
        final String nama_belakang = namaBelakang.getText().toString().trim();
        final String email = email_register.getText().toString().trim();
        final String password = password_register.getText().toString().trim();
        final String repassword = ulangi.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, con.Regist(), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            pDialog.show();
                            boolean cancel = false;
                            JSONObject jsonObject = new JSONObject(response);
                            success = jsonObject.optString("success");
                            if(success.equals("1")){
                                if(repassword.equals(password)){
                                    Toast.makeText(ActivityRegister.this, "sukses", Toast.LENGTH_LONG).show();
                                    pDialog.dismiss();
                                    startActivity(new Intent(ActivityRegister.this, LoginActivity.class));
                                }else{
                                    cancel = true;
                                    ulangi.setError("Penulisan Password harus sama");
                                    ulangi.setBackgroundResource(R.drawable.textfield_error);
                                    if (cancel){
                                        ulangi.requestFocus();
                                    }
                                    if (repassword.equals("")){
                                        cancel = true;
                                        ulangi.setError("Kolom wajib di isi");
                                        ulangi.setBackgroundResource(R.drawable.textfield_error);
                                        if (cancel){
                                            ulangi.requestFocus();
                                        }
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
                        Toast.makeText(getApplicationContext(), error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
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
    private void showDialog(String pesan, String isi){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title dialog
        alertDialogBuilder.setTitle(pesan);

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage(isi)
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
