package com.example.ulangansqllite;

public class Content {
    private int id_;
    private String judul;
    private String deskripsi;
    private String tgl;

    public void setId(int id) {
        this.id_ = id;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public void setDesk(String desk) {
        this.deskripsi = desk;
    }

    public void setTgl(String tgl) {
        this.tgl = tgl;
    }

    public int getId() {
        return id_;
    }

    public String getJudul() {
        return judul;
    }

    public String getDesk() {
        return deskripsi;
    }

    public String getTgl() {
        return tgl;
    }
}
