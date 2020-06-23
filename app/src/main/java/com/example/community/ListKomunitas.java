package com.example.community;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.model.Komunitas;
import com.example.society_try.R;

import java.util.List;

public class ListKomunitas extends AppCompatActivity implements ListView{

    List<Komunitas> komunitasList;
    SwipeRefreshLayout refresh;
    ListPresenter listPresenter;
    ListView listView;
    ListCommunity listCommunity;
    RecyclerView recyclerView;
    EditText search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_komunitas);
        recyclerView = findViewById(R.id.list_komunitas);
        refresh = findViewById(R.id.refresh_komunitas);
        search = findViewById(R.id.search);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listPresenter = new ListPresenter(this);
        listPresenter.getKomunitas("");

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listPresenter.getKomunitas("");
            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                listPresenter.getKomunitas(s.toString());
            }
        });

    }

    @Override
    public void showLoading() {
        refresh.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        refresh.setRefreshing(false);
    }

    @Override
    public void onGetResult(List<Komunitas> komunitasList) {
        listCommunity = new ListCommunity(this, komunitasList);
        listCommunity.notifyDataSetChanged();
        recyclerView.setAdapter(listCommunity);

        this.komunitasList = komunitasList;
    }

    @Override
    public void onErrorLoading(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
