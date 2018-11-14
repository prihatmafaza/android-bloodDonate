package com.example.faza.skripsidonordarah;

import java.util.Date;

public class Transaksi {
    private String nama;
    private String lok;
    private String golcari;
    private String deskripsi;
    private String key;
    private String Uid;
    private String status;
    private double latitude;
    private double longitude;
    private long time;
    private String penolong;

    public Transaksi(){

    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getLok() {
        return lok;
    }

    public void setLok(String lok) {
        this.lok = lok;
    }

    public String getGolcari() {
        return golcari;
    }

    public void setGolcari(String golcari) {
        this.golcari = golcari;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPenolong() {
        return penolong;
    }

    public void setPenolong(String penolong) {
        this.penolong = penolong;
    }

    @Override
    public String toString() {
        return "Transaksi{" +
                "nama='" + nama + '\'' +
                ", lok='" + lok + '\'' +
                ", golcari='" + golcari + '\'' +
                ", deskripsi='" + deskripsi + '\'' +
                '}';
    }

    public Transaksi(String Uid ,String nama, String lok, String golcari, String deskripsi, double latitude , double longitude, String status, String penolong) {
        this.Uid = Uid;
        this.nama = nama;
        this.lok = lok;
        this.golcari = golcari;
        this.deskripsi = deskripsi;
        this.latitude = latitude;
        this.longitude = longitude;
        this.time = new Date().getTime();
        this.status = status;
        this.penolong = penolong;
    }
}
