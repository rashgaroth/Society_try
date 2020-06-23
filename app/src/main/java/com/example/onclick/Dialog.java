package com.example.onclick;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.onclick.myartikelselected.ArtikelPresenter;
import com.example.society_try.R;

public class Dialog extends AppCompatDialogFragment {
    private EditText judul, deskripsi;
    private String judulArtikel, deskripsiArtikel;
    ArtikelPresenter artikelPresenter;
    private int id;
    KirimData kirimData;

    public Dialog(String judulArtikel, String deskripsiArtikel, int id) {
        this.judulArtikel = judulArtikel;
        this.deskripsiArtikel = deskripsiArtikel;
        this.id = id;
    }

    @NonNull
    @Override
    public android.app.Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.layout_dialog, null);
        judul = view.findViewById(R.id.judul_update);
        deskripsi = view.findViewById(R.id.deskripsi_update);

        judul.setText(judulArtikel);
        deskripsi.setText(deskripsiArtikel);


        builder.setView(view)
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), "Canceled", Toast.LENGTH_SHORT).show();
                    }
                })
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        kirimData.kirim(judul.getText().toString(), deskripsi.getText().toString());
                        Toast.makeText(getContext(), "Review your Update!", Toast.LENGTH_SHORT).show();
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            kirimData = (KirimData) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Gagal");
        }
    }

    public interface KirimData{
        void kirim(String j, String d);
    }

}
