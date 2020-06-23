package com.example.myartikel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.model.MyArtikel;
import com.example.society_try.R;

import java.util.List;

public class ListMyArtikel extends RecyclerView.Adapter<ListMyArtikel.ListViewHolder> {
    private Context context;
    private List<MyArtikel> artikels;
    private ImageView foto;
    private ItemClickListener itemClickListener;
    private TextView judul, deksripsi, author;

    public ListMyArtikel(Context context, List<MyArtikel> artikel, ItemClickListener itemClickListener) {
        this.context = context;
        this.artikels = artikel;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_myartikel, parent, false);
        foto = view.findViewById(R.id.foto_myartikel);
        judul = view.findViewById(R.id.judul_myartikel);
        deksripsi = view.findViewById(R.id.deskripsi_myartikel);
        author = view.findViewById(R.id.profile_myartikel);
        return new ListViewHolder(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        MyArtikel artikel = artikels.get(position);
        holder.judul.setText(artikel.getJudul());
        holder.deskripsi.setText(artikel.getDeskripsi());
        holder.author.setText(artikel.getAuthor());
        Glide.with(context)
                .load(artikel.getGambar())
                .crossFade()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.gambar);
    }


    @Override
    public int getItemCount() {
        return artikels.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private Context context;
        private ImageView gambar;
        private TextView judul, deskripsi, author;
        private CardView card;
        ItemClickListener itemClickListener;

        public ListViewHolder(@NonNull View itemView, ItemClickListener itemClickListener) {
            super(itemView);
            gambar = itemView.findViewById(R.id.foto_myartikel);
            judul = itemView.findViewById(R.id.judul_myartikel);
            deskripsi = itemView.findViewById(R.id.deskripsi_myartikel);
            author = itemView.findViewById(R.id.profile_myartikel);
            card = itemView.findViewById(R.id.card_myartikel);

            this.itemClickListener = itemClickListener;
            card.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onItemClick(getAdapterPosition());
        }
    }
    public interface ItemClickListener {
        void onItemClick(int adapterPosition);
    }
}
