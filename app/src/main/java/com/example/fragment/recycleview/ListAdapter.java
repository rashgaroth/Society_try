package com.example.fragment.recycleview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.model.Artikel;
import com.example.society_try.R;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    private Context context;
    private List<Artikel> artikel;
    private ItemClickListener itemClickListener;
    private ImageView foto;

    private ImageView tampil, tampil_less;
    private CardView cardView;
    private TextView buka;

    public ListAdapter(Context context, List<Artikel> artikel) {
        this.context = context;
        this.artikel = artikel;
    }
    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_recycler, parent, false);
        cardView = view.findViewById(R.id.card_desc);
        tampil_less = view.findViewById(R.id.expand_less);
        buka = view.findViewById(R.id.tv_item_name);
        foto = view.findViewById(R.id.img_item_photo);

        return new ListViewHolder(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        Artikel artikels = artikel.get(position);
        holder.judul.setText(artikels.getJudul());
        holder.deskripsi.setText(artikels.getDeskripsi());
        holder.author.setText(artikels.getAuthor());
        holder.suka.setText(artikels.getSuka());
        Glide.with(context)
                .load(artikels.getGambar())
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(foto);
    }

    @Override
    public int getItemCount() {
        return artikel.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView gambar;
        private TextView judul, deskripsi, suka, author;
        CardView card;
        ImageView foto;
        ItemClickListener itemClickListener;

        public ListViewHolder(@NonNull View itemView, final ItemClickListener itemClickListener) {
            super(itemView);
            judul = itemView.findViewById(R.id.tv_item_name);
            deskripsi = itemView.findViewById(R.id.tv_item_detail);
            gambar = itemView.findViewById(R.id.img_item_photo);
            suka = itemView.findViewById(R.id.tv_love);
            author = itemView.findViewById(R.id.tv_profile);
            card = itemView.findViewById(R.id.card_article);
            foto = itemView.findViewById(R.id.img_item_photo);

            this.itemClickListener = itemClickListener;
            card.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(context, judul.getText(), Toast.LENGTH_SHORT).show();
        }
    }
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
