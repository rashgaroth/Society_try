package com.example.artikel;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.afollestad.materialdialogs.BuildConfig;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.activity.LoginActivity;
import com.example.apihelper.BaseApiServer;
import com.example.apihelper.RetrofitClient;
import com.example.model.Artikel;
import com.example.society_try.MainMenu;
import com.example.society_try.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuArtikel extends AppCompatActivity implements View.OnClickListener {
    EditText judul, deskripsi;
    ImageView imageView;
    Button upload, cameras;
    ProgressDialog progressDialog;
    File mPhotoFile;
    OutputStream outputStream;
    Bitmap bitmapUpload;
    ProgressBar progressBar;
    TextView txtProgress, namaUser, iduser;
    //init
    public static final int REQUEST_CODE_GALLERY = 002;
    private static final int CAMERA_REQUEST_CODE = 7777;
    private static final String TAG = MenuArtikel.class.getSimpleName();
    private Uri uri;
    private boolean adaGambar = false;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_artikel);

        upload = findViewById(R.id.uploads);
        judul = findViewById(R.id.judul);
        deskripsi = findViewById(R.id.deskripsi);
        imageView = findViewById(R.id.imageuplaoded);
        progressBar = findViewById(R.id.progressBar);
        txtProgress = findViewById(R.id.progress);
        namaUser = findViewById(R.id.nama_user);
        iduser = findViewById(R.id.iduser);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogGambar dg = new DialogGambar(imageView);
                dg.show(getSupportFragmentManager(), "View");
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait ...");

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            upload.setEnabled(false);
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 0);
        }else{
            upload.setEnabled(true);
        }

          String nama = MainMenu.nama_user();
          String id = MainMenu.id_user();

          iduser.setText(id);
          namaUser.setText("Hi "+nama+"!");

        upload.setOnClickListener(this);
    }

    private void hilang(){
        judul.setText("");
        deskripsi.setText("");
        imageView.setVisibility(View.GONE);
    }

    public void addToInternal(){
        try {
            BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
            Bitmap bitmap = drawable.getBitmap();

            File filepaths = Environment.getExternalStorageDirectory();
            File dir = new File (filepaths.getAbsolutePath()+"/Society/");
            dir.mkdir();
            File file = new File(dir, System.currentTimeMillis()+".jpg");
            try {
                outputStream = new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100,outputStream);
        }catch (Exception e){
            Toast.makeText(MenuArtikel.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.article_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.simpan:
                String author = MainMenu.nama_user();
                String title = judul.getText().toString().trim();
                String desc = deskripsi.getText().toString().trim();
                String idString = MainMenu.id_user();


                if (title.isEmpty()){
                    judul.setError("Kolom wajib di isi");
                }else if (desc.isEmpty()){
                    deskripsi.setError("Kolom Wajib di isi");
                }else if(title.length() > 35){
                    judul.setError("Full of String!");
                }
                else{
                    if (adaGambar){
                        progressDialog.show();
                        uploadFile(title, desc, author, bitmapUpload, idString);
                    }else{
                        Toast.makeText(this, "Tidak ada gambar yang diupload", Toast.LENGTH_SHORT).show();
                    }
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()){
            case R.id.uploads:
                new MaterialDialog.Builder(this)
                        .title(R.string.select)
                        .items(R.array.uploadImages)
                        .itemsIds(R.array.itemIds)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                switch (position){
                                    case 0 :
                                        setRequestImage();
                                        break;
                                    case 1 :
                                        setRequestCamera();
                                        break;
                                    case 2:
                                        imageView.setImageResource(R.drawable.noimage);
                                        imageView.setVisibility(View.GONE);
                                        break;
                                }
                            }
                        }).show();
                break;
        }
    }
    protected void onActivityResult(final int requestCode, int resultCode, final Intent data){
        super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == RESULT_OK){
        if (requestCode == CAMERA_REQUEST_CODE){
            try {
                bitmapUpload = (Bitmap) data.getExtras().get("data");
                imageView.setImageBitmap(bitmapUpload);
                imageView.setVisibility(View.VISIBLE);
                adaGambar = true;
            }catch (Exception e){
                Toast.makeText(MenuArtikel.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                   }
              }else if(requestCode == REQUEST_CODE_GALLERY){
            uri = data.getData();
            try {
                Uri path = data.getData();
                bitmapUpload = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                imageView.setImageBitmap(bitmapUpload);
                imageView.setVisibility(View.VISIBLE);
                adaGambar = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
         }
    }

    private void setRequestImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, REQUEST_CODE_GALLERY);
    }

    private void setRequestCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
        if (intent.resolveActivity(getPackageManager()) != null){
            File photoFile = null;
            try {
                photoFile = createImageFile();
            }catch (Exception e){
                e.printStackTrace();
            }
            if (photoFile != null){
                try {
                    mPhotoFile = photoFile;
                    Uri photoUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", photoFile);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                }catch (Exception e){
                    Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, "gagal", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Batalkan?");
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(MenuArtikel.this, LoginActivity.class));
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

    void uploadFile(final String judul, final String deskripsi, final String author, Bitmap gambar, String id){
         try{
             String title = judul;
             String desc = deskripsi;
             String auth = author;
             String user_id = id;

             File file = createTempFile(gambar);

             RequestBody utama = RequestBody.create(MediaType.parse("text/plain"), title);
             RequestBody isi = RequestBody.create(MediaType.parse("text/plain"), desc);
             RequestBody nama = RequestBody.create(MediaType.parse("text/plain"), auth);
             RequestBody id_user = RequestBody.create(MediaType.parse("text/plain"), user_id);
             RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
             MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestBody);

            addToInternal();

             BaseApiServer baseApiServer = RetrofitClient.getApiClient().create(BaseApiServer.class);
             Call<Artikel> artikelCall = baseApiServer.save(utama, isi, nama, body, id_user);

             artikelCall.enqueue(new Callback<Artikel>() {
                 @Override
                 public void onResponse(@NonNull Call<Artikel> call, @NonNull Response<Artikel> response) {

                     if (response.isSuccessful() && response.body() != null){
                         boolean success = true;
                         if (success){
                             hilang();
                             Toast.makeText(getBaseContext(), "Artikel berhasil diupload!", Toast.LENGTH_SHORT).show();
                             Intent intent = new Intent(MenuArtikel.this, MainMenu.class);
                             startActivity(intent);
                             progressDialog.dismiss();
                         }else {
                             Toast.makeText(getBaseContext(), "Artikel tidak berhasil diupload", Toast.LENGTH_SHORT).show();
                             progressDialog.dismiss();
                         }
                     }
                 }

                 @Override
                 public void onFailure(@NonNull Call<Artikel> call,@NonNull Throwable t) {
                     Toast.makeText(getBaseContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                 }
             });
         }catch (Exception e){
             Toast.makeText(getBaseContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
         }

    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String mFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File mFile = File.createTempFile(mFileName, ".jpg", storageDir);
        return mFile;
    }

    public File createTempFile(Bitmap bitmap) {
        File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                , "SOCIETY_" + System.currentTimeMillis() +"_image.jpeg");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG,100, bos);
        byte[] bitmapdata = bos.toByteArray();
        //write the bytes in file

        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
