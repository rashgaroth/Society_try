package com.example.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.society_try.R;

public class ListAdapter extends RecyclerView.Adapter {
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_recycler, parent, false);

        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ListViewHolder) holder).blindView(position);
    }

    @Override
    public int getItemCount() {
        return SocietyData.judul.length;
    }

    private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView gambar;
        private TextView judul, deskripsi;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            judul = itemView.findViewById(R.id.tv_item_name);
            deskripsi = itemView.findViewById(R.id.tv_item_detail);
            gambar = itemView.findViewById(R.id.img_item_photo);
        }

        public void blindView(int position){
            judul.setText(SocietyData.judul[position]);
            deskripsi.setText(SocietyData.deskripsi[position]);
            gambar.setImageResource(SocietyData.gambar[position]);
        }

        @Override
        public void onClick(View v) {

        }
    }

}
