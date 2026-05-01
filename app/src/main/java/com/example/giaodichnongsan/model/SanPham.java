package com.example.giaodichnongsan.model;

import java.io.Serializable;

public class SanPham implements Serializable {

    private String id;          // document ID từ Firestore
    private String imageUrl;    // URL ảnh từ Firebase Storage (thay int hinh)
    private String ten;
    private int gia;
    private int daBan;
    private String moTa;
    private String nguonGoc;
    private float danhGia;
    private String danhMuc;
    private String tenShop;
    private String shopId;
    private boolean noiBat;     // true = hiện ở mục nổi bật

    public SanPham() {} // bắt buộc cho Firestore

    // Getter
    public String getId()       { return id; }
    public String getImageUrl() { return imageUrl; }
    public String getTen()      { return ten; }
    public int getGia()         { return gia; }
    public int getDaBan()       { return daBan; }
    public String getMoTa()     { return moTa; }
    public String getNguonGoc() { return nguonGoc; }
    public float getDanhGia()   { return danhGia; }
    public String getDanhMuc()  { return danhMuc; }
    public String getTenShop()  { return tenShop; }
    public String getShopId()   { return shopId; }
    public boolean isNoiBat()   { return noiBat; }

    // Setter
    public void setId(String id)             { this.id = id; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public void setTen(String ten)           { this.ten = ten; }
    public void setGia(int gia)              { this.gia = gia; }
    public void setDaBan(int daBan)          { this.daBan = daBan; }
    public void setMoTa(String moTa)         { this.moTa = moTa; }
    public void setNguonGoc(String nguonGoc) { this.nguonGoc = nguonGoc; }
    public void setDanhGia(float danhGia)    { this.danhGia = danhGia; }
    public void setDanhMuc(String danhMuc)   { this.danhMuc = danhMuc; }
    public void setTenShop(String tenShop)   { this.tenShop = tenShop; }
    public void setShopId(String shopId)     { this.shopId = shopId; }
    public void setNoiBat(boolean noiBat)    { this.noiBat = noiBat; }
}