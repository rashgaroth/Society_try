package com.example.myartikel;

import android.os.Bundle;
import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.model.MyArtikel;
import com.example.onclick.MyArtikelSelected;
import com.example.society_try.MainMenu;
import com.example.society_try.R;

import java.util.List;

public class MyArticle extends AppCompatActivity implements MyArtikelView, ListMyArtikel.ItemClickListener {

    List<MyArtikel> list;
    SwipeRefreshLayout refresh;
    MyArtikelPresenter presenter;
    ListMyArtikel listMyArtikel;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_article);
        recyclerView = findViewById(R.id.list_myartikel);
        refresh = findViewById(R.id.refresh_myartikel);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        presenter = new MyArtikelPresenter(this);
        presenter.getMyArtikel(MainMenu.id_user());

        presenter.getMyArtikel(MainMenu.id_user());
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getMyArtikel(MainMenu.id_user());
            }
        });
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onGetResult(List<MyArtikel> artikels) {
        listMyArtikel = new ListMyArtikel(this, artikels, this);
        listMyArtikel.notifyDataSetChanged();
        recyclerView.setAdapter(listMyArtikel);

        list = artikels;
    }

    @Override
    public void onErrorLoading(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(int adapterPosition) {
        list.get(adapterPosition);
        Intent intent = new Intent(MyArticle.this, MyArtikelSelected.class);
        int id = list.get(adapterPosition).getId();
        intent.putExtra("id", id);
        intent.putExtra("judul", list.get(adapterPosition).getJudul());
        intent.putExtra("deskripsi", list.get(adapterPosition).getDeskripsi());
        intent.putExtra("author", list.get(adapterPosition).getAuthor());
        intent.putExtra("gambar", list.get(adapterPosition).getGambar());

        startActivity(intent);
    }
}
