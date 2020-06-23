package com.example.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Komunitas {
    @Expose
    @SerializedName("id_komunitas") String idKomunitas;
    @Expose
    @SerializedName("nama_komunitas") String namaKomunitas;
    @Expose
    @SerializedName("alamat") String alamat;
    @Expose
    @SerializedName("hobi_id") String hobiId;
    @Expose
    @SerializedName("succes") boolean Succes;
    @Expose
    @SerializedName("rating") String rating;

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getIdKomunitas() {
        return idKomunitas;
    }

    public void setIdKomunitas(String idKomunitas) {
        this.idKomunitas = idKomunitas;
    }

    public String getNamaKomunitas() {
        return namaKomunitas;
    }

    public void setNamaKomunitas(String namaKomunitas) {
        this.namaKomunitas = namaKomunitas;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getHobiId() {
        return hobiId;
    }

    public void setHobiId(String hobiId) {
        this.hobiId = hobiId;
    }

    public boolean isSucces() {
        return Succes;
    }

    public void setSucces(boolean succes) {
        Succes = succes;
    }
}
