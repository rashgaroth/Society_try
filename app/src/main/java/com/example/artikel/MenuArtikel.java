package com.example.artikel;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.AsyncTask;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.afollestad.materialdialogs.BuildConfig;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.apihelper.BaseApiServer;
import com.example.apihelper.RetrofitClient;
import com.example.model.Image;
import com.example.society_try.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuArtikel extends AppCompatActivity implements EditorView, View.OnClickListener {
    EditText judul, deskripsi;
    ImageView imageView;
    Button upload, cameras;
    ProgressDialog progressDialog;
    EditorPresenter editorPresenter;
    File mPhotoFile;
    OutputStream outputStream;
    Bitmap bitmapUpload;
    ProgressBar progressBar;
    TextView txtProgress;
    //init
    public static final int REQUEST_CODE_GALLERY = 002;
    private static final int CAMERA_REQUEST_CODE = 7777;
    private static final String TAG = MenuArtikel.class.getSimpleName();
    private Uri uri;


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

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait ...");
        editorPresenter = new EditorPresenter(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            upload.setEnabled(false);
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 0);
        }else{
            upload.setEnabled(true);
        }


        upload.setOnClickListener(this);
    }

    private void addToInternal(){
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
            Toast.makeText(MenuArtikel.this, "Image saved to storage", Toast.LENGTH_SHORT).show();
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
                String title = judul.getText().toString().trim();
                String desc = deskripsi.getText().toString().trim();
                if (title.isEmpty()){
                    judul.setError("Kolom wajib di isi");
                }else if (desc.isEmpty()){
                    deskripsi.setError("Kolom Wajib di isi");
                }else{
                    editorPresenter.save(title, desc);
                    uploadFile(bitmapUpload);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void onAddSuccess(String message) {
        Toast.makeText(MenuArtikel.this, "Berhasil ditambahkan!", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onAddError(String message) {
        Toast.makeText(MenuArtikel.this, message, Toast.LENGTH_SHORT).show();
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
                startAsynctask();
            }catch (Exception e){
                Toast.makeText(MenuArtikel.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                   }
              }else if(requestCode == REQUEST_CODE_GALLERY){
            uri = data.getData();
            try {
                Uri path = data.getData();
                bitmapUpload = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                imageView.setImageBitmap(bitmapUpload);
                startAsynctask();
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

    void uploadFile(Bitmap gambar){
        File file = createTempFile(gambar);

        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        BaseApiServer baseApiServer = RetrofitClient.getApiClient().create(BaseApiServer.class);
        Call<Image> call = baseApiServer.sendImage(body);
        addToInternal();
        try {
            call.enqueue(new Callback<Image>() {
                @Override
                public void onResponse(Call<Image> call, Response<Image> response) {
                    Toast.makeText(MenuArtikel.this, "Sukses" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<Image> call, Throwable t) {
                    Toast.makeText(MenuArtikel.this, "error | " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
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

    private File createTempFile(Bitmap bitmap) {
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

    private void startAsynctask(){

    LoadingGambar load = new LoadingGambar(this);
    load.execute(3);

    }

    private static class LoadingGambar extends AsyncTask<Integer, Integer, String> {
        private WeakReference<MenuArtikel> weakReference;

        LoadingGambar(MenuArtikel activity){
            weakReference = new WeakReference<MenuArtikel>(activity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            MenuArtikel mainMenu = weakReference.get();
            if (mainMenu == null || mainMenu.isFinishing()){
                return;
            }
            mainMenu.progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Integer... integers) {
            for (int i = 0; i<integers[0]; i++){
                publishProgress((i *100) / integers[0]);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "Gambar siap diupload!";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            MenuArtikel mainMenu = weakReference.get();
            if (mainMenu == null || mainMenu.isFinishing()){
                return;
            }
            mainMenu.progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            MenuArtikel mainMenu = weakReference.get();
            if (mainMenu == null || mainMenu.isFinishing()){
                return;
            }
            super.onPostExecute(s);
            Toast.makeText(mainMenu, s, Toast.LENGTH_SHORT).show();
            mainMenu.imageView.setVisibility(View.VISIBLE);
            mainMenu.progressBar.setProgress(0);
            mainMenu.progressBar.setVisibility(View.INVISIBLE);
        }
    }
}
