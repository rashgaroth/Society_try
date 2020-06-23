package com.example.onclick;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.onclick.myartikelselected.ArtikelPresenter;
import com.example.onclick.myartikelselected.ArtikelView;
import com.example.society_try.R;

public class MyArtikelSelected extends AppCompatActivity implements Dialog.KirimData, ArtikelView {
    ImageView gambar;
    TextView judul, deskripsi, author, id;
    SwipeRefreshLayout refreshLayout;
    ArtikelPresenter presenter;
    Menu menu;
    ArtikelView view;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        this.menu = menu;
        inflater.inflate(R.menu.myartikel_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.hapus:

                hapusArtikel();

                break;
            case R.id.update:

                updateArtikel();

                break;
            case R.id.post:
                updatePost();
        }

        return true;
    }

    private void updatePost() {
        presenter.updateData(id.getText().toString(), judul.getText().toString(), deskripsi.getText().toString());
    }

    private void hapusArtikel() {
    }

    private void updateArtikel(){
            Dialog dialog = new Dialog(judul.getText().toString(), deskripsi.getText().toString(), Integer.parseInt(id.getText().toString()));
            dialog.show(getSupportFragmentManager(), "Update data");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_artikel_selected);
        gambar = findViewById(R.id.gambar_myartikel_selected);
        judul = findViewById(R.id.judul_myartikel_selected);
        deskripsi = findViewById(R.id.deskripsi_myartikel_selected);
        author = findViewById(R.id.author_myartikel_selected);
        refreshLayout = findViewById(R.id.refresh_myartikel_selected);
        id = findViewById(R.id.id);

        presenter = new ArtikelPresenter(this);
        view = new ArtikelView() {
            @Override
            public void showLoading() {
                refreshLayout.setRefreshing(true);
            }

            @Override
            public void hideLoading() {
                refreshLayout.setRefreshing(false);
            }

            @Override
            public void onSucces(String message) {
                Toast.makeText(MyArtikelSelected.this, "Updated!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onErrorLoading(String message) {
                Toast.makeText(MyArtikelSelected.this, "There's problem on server!", Toast.LENGTH_SHORT).show();
            }
        };
        refreshLayout.setRefreshing(true);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Data();
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id.setText(String.valueOf(extras.getInt("id")));
            judul.setText(extras.getString("judul"));
            author.setText(extras.getString("author"));
            deskripsi.setText(extras.getString("deskripsi"));
            Glide.with(this)
                    .load(extras.getString("gambar"))
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(gambar);
            refreshLayout.setRefreshing(false);
        }

    }

    private void Data(){
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void kirim(String j, String d) {
        judul.setText(j);
        deskripsi.setText(d);
        MenuItem menuPost = menu.findItem(R.id.post);
        menuPost.setVisible(true);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onSucces(String message) {
        Toast.makeText(MyArtikelSelected.this, "Updated!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onErrorLoading(String message) {
        Toast.makeText(MyArtikelSelected.this, "There's problem on server!", Toast.LENGTH_SHORT).show();
    }
}
