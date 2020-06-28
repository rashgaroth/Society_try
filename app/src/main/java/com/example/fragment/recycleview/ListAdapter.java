package com.example.fragment.recycleview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
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
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    private Context ctx;
    private List<Artikel> artikel;
    private ItemClickListener itemClickListener;
    private ImageView foto;

    private ImageView tampil, tampil_less;
    private CardView cardView;
    private TextView buka;


    public ListAdapter(Context ctx, List<Artikel> artikel, ItemClickListener itemClickListener) {
        this.ctx = ctx;
        this.artikel = artikel;
        this.itemClickListener = itemClickListener;
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
        holder.suka.setText("Like : "+ String.valueOf(holder.likes));
        holder.tanggal.setText(artikels.getTanggal());
        try{
            Glide.with(ctx)
                    .load(artikels.getGambar())
                    .centerCrop()
                    .error(R.drawable.beatle)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.foto);
        }catch (Exception e){
            Toast.makeText(ctx, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return artikel.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView judul, deskripsi, suka, author, tanggal;
        CardView card;
        ImageView foto;
        ItemClickListener itemClickListener;
        LikeButton like;
        int likes = 0;
        boolean liked = false;
        AsyncTask run = new Asyntask();

        public ListViewHolder(@NonNull View itemView, ItemClickListener itemClickListener) {
            super(itemView);
            judul = itemView.findViewById(R.id.tv_item_name);
            deskripsi = itemView.findViewById(R.id.tv_item_detail);
            suka = itemView.findViewById(R.id.tv_love);
            author = itemView.findViewById(R.id.tv_profile);
            card = itemView.findViewById(R.id.card_article);
            foto = itemView.findViewById(R.id.img_item_photo);
            tanggal = itemView.findViewById(R.id.tv_item_tanggal);
            like = itemView.findViewById(R.id.like);


            like.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {
                    liked = true;
                    likes = likes + 1;
                    suka.setText(String.valueOf(likes));
                    Toast.makeText(ctx, "Liked", Toast.LENGTH_SHORT).show();
                }

                @SuppressLint("SetTextI18n")
                @Override
                public void unLiked(LikeButton likeButton) {
                    liked = false;
                    likes = likes - 1;
                    suka.setText(String.valueOf(likes));
                    Toast.makeText(ctx, "Unliked", Toast.LENGTH_SHORT).show();
                }
            });

            this.itemClickListener = itemClickListener;
            card.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onItemClick(getAdapterPosition());
        }

        private class Asyntask extends AsyncTask<Integer, Integer, Integer> {

            @Override
            protected Integer doInBackground(Integer... integers) {

                    likes = likes + 1;

                return likes;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
            }
        }

    }
    public interface ItemClickListener {
        void onItemClick(int position);
    }

}
