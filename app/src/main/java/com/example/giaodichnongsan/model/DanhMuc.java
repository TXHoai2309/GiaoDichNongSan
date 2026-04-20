package com.example.giaodichnongsan.model;

public class DanhMuc {
    int hinh;
    String ten;

    public DanhMuc(int hinh, String ten) {
        this.hinh = hinh;
        this.ten = ten;
    }

    public int getHinh() {
        return hinh;
    }

    public String getTen() {
        return ten;
    }
}