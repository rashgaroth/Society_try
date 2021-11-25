package com.example.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ActivityRegister extends AppCompatActivity {
    private EditText namaDepan, namaBelakang, email_register, password_register, ulangi, alamat, notlp;
    ProgressDialog pDialog;
    String success;
    ScrollView sv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        namaDepan = findViewById(R.id.namaDepan);
        namaBelakang = findViewById(R.id.namaBelakang);
        email_register = findViewById(R.id.email_register);
        password_register = findViewById(R.id.password_register);
        ulangi = findViewById(R.id.rePassword);
        sv = (ScrollView) findViewById(R.id.scroll_register);
        alamat = findViewById(R.id.alamat);
        notlp = findViewById(R.id.nomorTlp);

       findViewById(R.id.buttonRegister).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(TextUtils.isEmpty(namaDepan.getText().toString())){
                   String pesan = "Required Fields";
                   String isi = "Example : rash, dwi, smith, allan";
                   showDialog(pesan, isi);
               }else if(TextUtils.isEmpty(namaBelakang.getText().toString())){
                   String pesan = "Required Fields";
                   String isi = "Example : yan, lennon, mccartney";
                   showDialog(pesan, isi);
               }else if(TextUtils.isEmpty(email_register.getText().toString())){
                   String pesan = "Required Fields";
                   String isi = "Example : ujang@gmail.com";
                   showDialog(pesan, isi);
               }else if(TextUtils.isEmpty(password_register.getText().toString())){
                   String pesan = "Required Fields";
                   String isi = "Example : ******";
                   showDialog(pesan, isi);
               }else if (TextUtils.isEmpty(alamat.getText().toString()) && TextUtils.isEmpty(notlp.getText().toString())){
                   String pesan = "Address and Phone number cannot be empty";
                   String isi = "Address and Phone number cannot be empty";
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
        builder.setMessage("Cancel Registration?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(ActivityRegister.this, LoginActivity.class));
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
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
        pDialog.setMessage("Wait a minute...");

        Connect con = new Connect();
        pDialog.show();
        final String nama_depan = namaDepan.getText().toString().trim();
        final String nama_belakang = namaBelakang.getText().toString().trim();
        final String email = email_register.getText().toString().trim();
        final String password = password_register.getText().toString().trim();
        final String repassword = ulangi.getText().toString().trim();
        final String alamat_user = alamat.getText().toString().trim();
        final String notlp_user = notlp.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, con.Regist(), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            boolean cancel = false;
                            JSONObject jsonObject = new JSONObject(response);
                            success = jsonObject.getString("success");
                            if(success.equals("1")){
                                if(repassword.equals(password)){
                                    Toast.makeText(ActivityRegister.this, "Success Registration!", Toast.LENGTH_LONG).show();
                                    pDialog.dismiss();
                                    startActivity(new Intent(ActivityRegister.this, LoginActivity.class));
                                }else{
                                    cancel = true;
                                    pDialog.dismiss();
                                    ulangi.setError("Password must be same!");
                                    ulangi.setBackgroundResource(R.drawable.textfield_error);
                                    if (cancel){
                                        ulangi.requestFocus();
                                    }
                                    if (repassword.equals("")){
                                        cancel = true;
                                        ulangi.setError("This column cannot be empty!");
                                        ulangi.setBackgroundResource(R.drawable.textfield_error);
                                        pDialog.dismiss();
                                        if (cancel){
                                            ulangi.requestFocus();
                                        }
                                    }
                                }
                            }
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Error1 : "+e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                    new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error2 : "+error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
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
                params.put("alamat", alamat_user);
                params.put("no_tlp", notlp_user);
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
                .setNegativeButton("Ok",new DialogInterface.OnClickListener() {
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
