package com.example.fragment;

import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.society_try.R;

public class ListAdapter extends RecyclerView.Adapter {
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final ImageView tampil, tampil_less;
        final CardView cardView;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_recycler, parent, false);
        tampil = view.findViewById(R.id.expand);
        cardView = view.findViewById(R.id.card_desc);
        tampil_less = view.findViewById(R.id.expand_less);

        tampil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cardView.getVisibility()==View.GONE){
                    TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                    cardView.setVisibility(View.VISIBLE);
                    tampil_less.setVisibility(View.VISIBLE);
                    tampil.setVisibility(View.GONE);
                }else{
                    TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                    cardView.setVisibility(View.GONE);
                    tampil.setVisibility(View.VISIBLE);
                    tampil_less.setVisibility(View.GONE);
                }
            }
        });

        tampil_less.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cardView.getVisibility()==View.VISIBLE){
                    TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                    cardView.setVisibility(View.GONE);
                    tampil_less.setVisibility(View.GONE);
                    tampil.setVisibility(View.VISIBLE);
                }
            }
        });

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
        private TextView judul, deskripsi, love, author;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            judul = itemView.findViewById(R.id.tv_item_name);
            deskripsi = itemView.findViewById(R.id.tv_item_detail);
            gambar = itemView.findViewById(R.id.img_item_photo);
            love = itemView.findViewById(R.id.tv_love);
            author = itemView.findViewById(R.id.tv_profile);
        }

        public void blindView(int position){
            judul.setText(SocietyData.judul[position]);
            deskripsi.setText(SocietyData.deskripsi[position]);
            gambar.setImageResource(SocietyData.gambar[position]);
            love.setText(SocietyData.love[position]);
            author.setText(SocietyData.author[position]);
        }

        @Override
        public void onClick(View v) {

        }
    }

}
