package com.example.faza.skripsidonordarah;

public class userModel {
    private String email;
    private String nama;
    private String no_telepon;
    private String goldar;
    private String jenisKelamin;
    private String umur;
    private String rhesus;
    private String Key;
    private String status;

    public userModel(){

    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getRhesus() {
        return rhesus;
    }

    public void setRhesus(String rhesus) {
        this.rhesus = rhesus;
    }

    public String getNo_telepon() {
        return no_telepon;
    }

    public void setNo_telepon(String no_telepon) {
        this.no_telepon = no_telepon;
    }

    public String getGoldar() {
        return goldar;
    }

    public void setGoldar(String goldar) {
        this.goldar = goldar;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getUmur() {
        return umur;
    }

    public void setUmur(String umur) {
        this.umur = umur;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "userModel{" +
                "email='" + email + '\'' +
                ", nama='" + nama + '\'' +
                ", no_telepon='" + no_telepon + '\'' +
                ", goldar='" + goldar + '\'' +
                ", jenisKelamin='" + jenisKelamin + '\'' +
                ", umur='" + umur + '\'' +
                '}';
    }


    public userModel(String email, String nama, String no_telepon,String goldar ,String jenisKelamin,  String umur, String status) {
        this.email = email;
        this.nama = nama;
        this.no_telepon = no_telepon;
        this.goldar = goldar;
        this.jenisKelamin = jenisKelamin;
        this.umur = umur;
        this.status= status;
        //this.rhesus =rhesus;
    }

//    public userModel(String email, String nama, String jenisKelamin) {
//        this.email = email;
//        this.nama = nama;
//        this.jenisKelamin = jenisKelamin;
//    }
}
