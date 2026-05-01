package com.example.giaodichnongsan.model;

public class DanhMuc {
    private int icon;
    private String ten;
    private String id; // 🔥 THÊM — khớp với danhMuc trong SanPham

    public DanhMuc(int icon, String ten, String id) {
        this.icon = icon;
        this.ten = ten;
        this.id = id;
    }

    public int getIcon() { return icon; }
    public String getTen() { return ten; }
    public String getId() { return id; }
}