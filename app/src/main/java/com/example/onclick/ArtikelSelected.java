package com.example.onclick;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.society_try.R;

public class ArtikelSelected extends AppCompatActivity {

    public TextView judul, author, deskripsi;
    ImageView gambar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artikel_try);
        judul = findViewById(R.id.judul_artikel);
        author = findViewById(R.id.author_artikel);
        deskripsi = findViewById(R.id.deskripsi_artikel);
        gambar = findViewById(R.id.gambar_artikel);


        Bundle extras = getIntent().getExtras();
        if (extras != null){
            judul.setText(extras.getString("judul"));
            author.setText(extras.getString("author"));
            deskripsi.setText(extras.getString("deskripsi"));
            Glide.with(this)
                    .load(extras.getString("gambar"))
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(gambar);
        }
    }
}
