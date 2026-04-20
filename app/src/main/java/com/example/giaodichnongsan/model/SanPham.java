package com.example.giaodichnongsan.model;

import java.io.Serializable;

public class SanPham implements Serializable {

    // ====== THÔNG TIN CƠ BẢN ======
    private int id;
    private int hinh;
    private String ten;
    private int gia;
    private int daBan;

    // ====== THÔNG TIN CHI TIẾT ======
    private String moTa;
    private String nguonGoc;
    private float danhGia;
    private String tenShop; // 🔥 THÊM DÒNG NÀY

    // ====== CONSTRUCTOR ĐẦY ĐỦ ======
    public SanPham(int id, int hinh, String ten, int gia, int daBan,
                   String moTa, String nguonGoc, float danhGia, String tenShop) {
        this.id = id;
        this.hinh = hinh;
        this.ten = ten;
        this.gia = gia;
        this.daBan = daBan;
        this.moTa = moTa;
        this.nguonGoc = nguonGoc;
        this.danhGia = danhGia;
        this.tenShop = tenShop;
    }

    // ====== CONSTRUCTOR CHO LIST ======
    public SanPham(int id, int hinh, String ten, int gia, int daBan) {
        this.id = id;
        this.hinh = hinh;
        this.ten = ten;
        this.gia = gia;
        this.daBan = daBan;
    }

    // ====== GETTER ======
    public int getId() { return id; }
    public int getHinh() { return hinh; }
    public String getTen() { return ten; }
    public int getGia() { return gia; }
    public int getDaBan() { return daBan; }
    public String getMoTa() { return moTa; }
    public String getNguonGoc() { return nguonGoc; }
    public float getDanhGia() { return danhGia; }
    public String getTenShop() { return tenShop; }

    // ====== SETTER ======
    public void setMoTa(String moTa) { this.moTa = moTa; }
    public void setNguonGoc(String nguonGoc) { this.nguonGoc = nguonGoc; }
    public void setDanhGia(float danhGia) { this.danhGia = danhGia; }
    public void setTenShop(String tenShop) { this.tenShop = tenShop; }
}