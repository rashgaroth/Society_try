package com.example.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.fragment.recycleview.ListAdapter;
import com.example.fragment.recycleview.MainPresenter;
import com.example.fragment.recycleview.MainView;
import com.example.model.Artikel;
import com.example.society_try.R;

import java.util.List;

public class HomeFragment extends Fragment implements MainView {
    ScrollView sv;
    SwipeRefreshLayout swipeRefreshLayout;
    MainPresenter mainPresenter;
    ListAdapter.ItemClickListener itemClickListener;
    List<Artikel> artikel;
    ListAdapter adapter;
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = v.findViewById(R.id.recyclerview);
        swipeRefreshLayout = v.findViewById(R.id.refresh);
        sv = v.findViewById(R.id.fragment_layout);


        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mainPresenter = new MainPresenter(this);
        mainPresenter.getData();

        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        mainPresenter.getData();
                    }
                });

        ListAdapter.ItemClickListener itemClickListener = new ListAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String judul = artikel.get(position).getJudul();
                Toast.makeText(getActivity(), judul, Toast.LENGTH_SHORT).show();
            }
        };

        return v;
    }

    @Override
    public void showLoading() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onGetResult(List<Artikel> artikels) {
        adapter = new ListAdapter(getActivity(), artikels);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        artikel = artikels;
    }

    @Override
    public void onErrorLoading(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
