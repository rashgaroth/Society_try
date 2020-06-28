package com.example.community;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.model.Komunitas;
import com.example.society_try.R;

import java.util.List;

public class ListCommunity extends RecyclerView.Adapter<ListCommunity.ListViewHolder> {

    private Context context;
    private List<Komunitas> komunitasList;
    private ImageView foto;
    private ItemClickListener itemClickListener;
    private TextView nama, alamat, rating;
    private RatingBar ratingKomunitas;

    public ListCommunity(Context context, List<Komunitas> komunitasList) {
        this.context = context;
        this.komunitasList = komunitasList;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_komunitas, parent, false);
        foto = view.findViewById(R.id.img);
        nama = view.findViewById(R.id.judul_komunitas);
        alamat = view.findViewById(R.id.alamat);
        rating = view.findViewById(R.id.rating);
        ratingKomunitas = view.findViewById(R.id.ratingbar);
        return new ListCommunity.ListViewHolder(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        Komunitas komunitas = komunitasList.get(position);
        holder.nama.setText(komunitas.getNamaKomunitas());
        holder.alamat.setText(komunitas.getAlamat());
        Glide.with(context)
                .load(komunitas.getGambar())
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.gambar);
        holder.rating.setText(komunitas.getRating());
        holder.ratingBar.setRating(Float.parseFloat(holder.rating.getText().toString()));

    }

    @Override
    public int getItemCount() {
        return komunitasList.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        RatingBar ratingBar;
        private ImageView gambar;
        private TextView nama, alamat, rating;
        ItemClickListener itemClickListener;
        RelativeLayout list;

        public ListViewHolder(@NonNull View itemView, ItemClickListener itemClickListener) {
            super(itemView);
            gambar = itemView.findViewById(R.id.img);
            nama = itemView.findViewById(R.id.judul_komunitas);
            alamat = itemView.findViewById(R.id.alamat);
            rating = itemView.findViewById(R.id.rating);
            list = itemView.findViewById(R.id.list);
            ratingBar = itemView.findViewById(R.id.ratingbar);

            this.itemClickListener = itemClickListener;
            list.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
