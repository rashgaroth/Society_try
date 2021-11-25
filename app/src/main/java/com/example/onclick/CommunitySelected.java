package com.example.onclick;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.society_try.R;

public class CommunitySelected extends AppCompatActivity {
    TextView judul, alamat;
    ImageView gambar;
    Button join;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_selected);

        judul = findViewById(R.id.judul_komunitas_selected);
        alamat = findViewById(R.id.tempat);
        gambar = findViewById(R.id.image_selected);
        join = findViewById(R.id.join);

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CommunitySelected.this, "Joined!", Toast.LENGTH_SHORT).show();
                join.setBackgroundResource(R.drawable.button_joined);
                join.setText("Joined");
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            judul.setText(extras.getString("judul"));
            alamat.setText(extras.getString("alamat"));
            Glide.with(this)
                    .load(extras.getString("gambar"))
                    .crossFade()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(gambar);
        }
    }
}
