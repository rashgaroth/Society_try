package com.example.model;

public class User {
    private String email, nama_depan, nama_belakang;
    private int id;

    public User(String email, String nama_depan, String nama_belakang, int id) {
        this.email = email;
        this.nama_depan = nama_depan;
        this.nama_belakang = nama_belakang;
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public String getNama_depan() {
        return nama_depan;
    }

    public String getNama_belakang() {
        return nama_belakang;
    }

    public int getId() {
        return id;
    }
}
