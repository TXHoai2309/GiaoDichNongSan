package com.example.giaodichnongsan.model;

import java.io.Serializable;

public class User implements Serializable {

    private String uid;
    private String hoTen;
    private String soDienThoai;
    private String email;
    private String avatarUrl;
    private String diaChi;
    private boolean isSeller;
    private String shopId;
    private long ngayTao;
    private boolean biKhoa;

    public User() {}

    public User(String uid, String hoTen, String soDienThoai, String email) {
        this.uid = uid;
        this.hoTen = hoTen;
        this.soDienThoai = soDienThoai;
        this.email = email;
        this.isSeller = false;
        this.biKhoa = false;
        this.ngayTao = System.currentTimeMillis();
    }

    public String getUid() { return uid; }
    public String getHoTen() { return hoTen; }
    public String getSoDienThoai() { return soDienThoai; }
    public String getEmail() { return email; }
    public String getAvatarUrl() { return avatarUrl; }
    public String getDiaChi() { return diaChi; }
    public boolean isSeller() { return isSeller; }
    public String getShopId() { return shopId; }
    public long getNgayTao() { return ngayTao; }
    public boolean isBiKhoa() { return biKhoa; }

    public void setUid(String uid) { this.uid = uid; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }
    public void setSoDienThoai(String soDienThoai) { this.soDienThoai = soDienThoai; }
    public void setEmail(String email) { this.email = email; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }
    public void setSeller(boolean seller) { isSeller = seller; }
    public void setShopId(String shopId) { this.shopId = shopId; }
    public void setNgayTao(long ngayTao) { this.ngayTao = ngayTao; }
    public void setBiKhoa(boolean biKhoa) { this.biKhoa = biKhoa; }
}
