package com.example.selection.list;

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

public class ListRating extends RecyclerView.Adapter<ListRating.ListViewHolder> {
    private Context context;
    private List<Komunitas> komunitasList;
    private ImageView foto;
    private ItemClickListener itemClickListener;
    private TextView nama, alamat, id;
    private RatingBar rating;

    public ListRating(Context context, List<Komunitas> komunitasList, ItemClickListener itemClickListener) {
        this.context = context;
        this.komunitasList = komunitasList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_komunitas_hobbies, parent, false);
        foto = view.findViewById(R.id.img_h);
        nama = view.findViewById(R.id.judul_komunitas_h);
        alamat = view.findViewById(R.id.alamat_h);
        id = view.findViewById(R.id.id_h);
        rating = view.findViewById(R.id.rating_hobbies);

        return new ListRating.ListViewHolder(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        Komunitas komunitas = komunitasList.get(position);
        holder.nama.setText(komunitas.getNamaKomunitas());
        holder.alamat.setText(komunitas.getAlamat());
        Glide.with(context)
                .load(komunitas.getGambar())
                .crossFade()
                .error(R.drawable.noimage)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.gambar);
        holder.id.setText(komunitas.getIdKomunitas());
        holder.rating.setRating(Float.parseFloat(komunitas.getRating()));
    }

    @Override
    public int getItemCount() {
        return komunitasList.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView gambar;
        private TextView nama, alamat, id;
        ItemClickListener itemClickListener;
        RelativeLayout list;
        RatingBar rating;

        public ListViewHolder(@NonNull View itemView, ItemClickListener itemClickListener) {
            super(itemView);
            gambar = itemView.findViewById(R.id.img_h);
            nama = itemView.findViewById(R.id.judul_komunitas_h);
            alamat = itemView.findViewById(R.id.alamat_h);
            id = itemView.findViewById(R.id.id_h);
            list = itemView.findViewById(R.id.list_h);
            rating = itemView.findViewById(R.id.rating_hobbies);

            this.itemClickListener = itemClickListener;
            list.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            itemClickListener.onItemClick(getAdapterPosition());
        }
    }
    public interface ItemClickListener {
        void onItemClick(int position);
    }
}
