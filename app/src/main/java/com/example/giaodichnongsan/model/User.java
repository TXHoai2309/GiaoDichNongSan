package com.example.giaodichnongsan.model;

import java.io.Serializable;

public class User implements Serializable {

    private String uid;
    private String hoTen;
    private String soDienThoai;
    private String email;

    public User() {
        // cần cho Firebase
    }

    public User(String uid, String hoTen, String soDienThoai, String email) {
        this.uid = uid;
        this.hoTen = hoTen;
        this.soDienThoai = soDienThoai;
        this.email = email;
    }

    public String getUid() { return uid; }
    public String getHoTen() { return hoTen; }
    public String getSoDienThoai() { return soDienThoai; }
    public String getEmail() { return email; }

    public void setUid(String uid) { this.uid = uid; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }
    public void setSoDienThoai(String soDienThoai) { this.soDienThoai = soDienThoai; }
    public void setEmail(String email) { this.email = email; }
}