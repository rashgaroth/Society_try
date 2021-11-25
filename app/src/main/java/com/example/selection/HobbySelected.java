package com.example.selection;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.model.Komunitas;
import com.example.model.KomunitasHobby;
import com.example.onclick.CommunitySelected;
import com.example.selection.list.CommunityListByHobbies;
import com.example.selection.list.CommunityListHobbiesPresenter;
import com.example.selection.list.CommunityListView;
import com.example.selection.list.ListRating;
import com.example.society_try.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.List;

public class HobbySelected extends AppCompatActivity implements CommunityListView, CommunityListByHobbies.ItemClickListener, ListRating.ItemClickListener {

    private BottomSheetBehavior mbottom;
    boolean pencet;
    int WEIGHT_KEY_ADRESS = 0;
    int WEIGHT_KEY_RATING = 0;
    int WEIGHT_KEY_PEOPLE = 0;
    int WEIGHT_KEY_ACTIVE = 0;
    RecyclerView rv;
    TextView kosong;
    List<KomunitasHobby> komunitasList;
    List<Komunitas> list;
    CommunityListByHobbies communityListByHobbies;
    CommunityListHobbiesPresenter communityListHobbiesPresenter;
    AnimatorSet animatorSet;
    ListRating listRating;
    ImageView load;
    String h1 = Hobbies.hobi1;
    String h2 = Hobbies.hobi2;
    String h3 = Hobbies.hobi3;
    String h4 = Hobbies.hobi4;
    String h5 = Hobbies.hobi5;
    String h6 = Hobbies.hobi6;
    String h7 = Hobbies.hobi7;
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hobby_selected);

        final View bottom = findViewById(R.id.bottom_sheet);
        rv = findViewById(R.id.list_komunitas_hobby);
        mbottom = BottomSheetBehavior.from(bottom);
        kosong = findViewById(R.id.kosong);
        load = findViewById(R.id.loading_list);

        communityListHobbiesPresenter = new CommunityListHobbiesPresenter(this);
        communityListHobbiesPresenter.getData(h1,h2,h3,h4,h5,h6,h7);
        rv.setLayoutManager(new LinearLayoutManager(this));

        mbottom.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                    final Button b1, b2, b3, b4, b5;
                    final TextView tx;
                    b1 = bottomSheet.findViewById(R.id.alamat_tombol);
                    b2 = bottomSheet.findViewById(R.id.rating_tombol);
                    b3 = bottomSheet.findViewById(R.id.people_tombol);
                    b4 = bottomSheet.findViewById(R.id.active_tombol);
                    b5 = bottomSheet.findViewById(R.id.find_tombol);
                    b1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            pencet = true;
                            WEIGHT_KEY_ADRESS = 1;
                            b1.setBackgroundResource(R.drawable.button_delete_pressed);
                            Toast.makeText(HobbySelected.this, "Long click for cancel", Toast.LENGTH_SHORT).show();
                        }
                    });
                    b1.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            WEIGHT_KEY_ADRESS= 0;
                            b1.setBackgroundResource(R.drawable.button_delete);
                            Toast.makeText(HobbySelected.this, "Cancelled!", Toast.LENGTH_SHORT).show();
                            return true;
                        }
                    });
                    b2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            WEIGHT_KEY_RATING = 2;
                            pencet = true;
                            b2.setBackgroundResource(R.drawable.button_delete_pressed);
                            Toast.makeText(HobbySelected.this, "Long click for cancel", Toast.LENGTH_SHORT).show();
                        }
                    });
                    b2.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            WEIGHT_KEY_RATING = 0;
                            b2.setBackgroundResource(R.drawable.button_delete);
                            Toast.makeText(HobbySelected.this, "Cancelled!", Toast.LENGTH_SHORT).show();
                            return true;
                        }
                    });
                    b3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            WEIGHT_KEY_PEOPLE = 3;
                            pencet = true;
                            b3.setBackgroundResource(R.drawable.button_delete_pressed);
                            Toast.makeText(HobbySelected.this, "Long click for cancel", Toast.LENGTH_SHORT).show();
                        }
                    });
                    b3.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            WEIGHT_KEY_PEOPLE = 0;
                            b3.setBackgroundResource(R.drawable.button_delete);
                            Toast.makeText(HobbySelected.this, "Cancelled!", Toast.LENGTH_SHORT).show();
                            return true;
                        }
                    });
                    b4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            WEIGHT_KEY_ACTIVE = 4;
                            pencet = true;
                            b4.setBackgroundResource(R.drawable.button_delete_pressed);
                            Toast.makeText(HobbySelected.this, "Long click for cancel", Toast.LENGTH_SHORT).show();
                        }
                    });
                    b4.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            WEIGHT_KEY_ACTIVE = 0;
                            b4.setBackgroundResource(R.drawable.button_delete);
                            Toast.makeText(HobbySelected.this, "Cancelled!", Toast.LENGTH_SHORT).show();
                            return true;
                        }
                    });
                    b5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            load.setVisibility(View.VISIBLE);
                            animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(HobbySelected.this, R.animator.loading_anim);
                            animatorSet.setTarget(load);
                            animatorSet.start();
                            rv.setVisibility(View.GONE);
                            getRat();
                        }
                    });
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }

    void getRat(){
        communityListHobbiesPresenter = new CommunityListHobbiesPresenter(this);
        communityListHobbiesPresenter.getRating();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onGetResult(List<KomunitasHobby> komunitasList) {
        if (komunitasList.isEmpty()){
            rv.setVisibility(View.GONE);
            kosong.setVisibility(View.VISIBLE);
        }else {
            rv.setVisibility(View.VISIBLE);
            communityListByHobbies = new CommunityListByHobbies(this, komunitasList, this);
            communityListByHobbies.notifyDataSetChanged();
            rv.setAdapter(communityListByHobbies);
        }


        this.komunitasList = komunitasList;
    }

    @Override
    public void onGetResultRating(List<Komunitas> komunitas) {
        rv.setVisibility(View.VISIBLE);
        listRating = new ListRating(this, komunitas, this);
        communityListByHobbies.notifyDataSetChanged();
        rv.setAdapter(communityListByHobbies);

        list = komunitas;
    }

    @Override
    public void onErrorLoading(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClickView(int position) {
        //hobi
        try {
            komunitasList.get(position);
            Intent intent = new Intent(HobbySelected.this, CommunitySelected.class);
            intent.putExtra("judul", komunitasList.get(position).getNamaKomunitas());
            intent.putExtra("alamat", komunitasList.get(position).getAlamat());
            intent.putExtra("gambar", komunitasList.get(position).getGambar());
            startActivity(intent);
        }catch (Exception e){

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onItemClick(int position) {
        //rating
        list.get(position);
        Intent intent = new Intent(HobbySelected.this, CommunitySelected.class);
        intent.putExtra("judul", list.get(position).getNamaKomunitas());
        intent.putExtra("alamat", list.get(position).getAlamat());
        intent.putExtra("gambar", list.get(position).getGambar());
        startActivity(intent);
    }
}
