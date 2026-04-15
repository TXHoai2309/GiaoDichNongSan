package com.example.giaodichnongsan;

public class SanPham {
    int id;
    int hinh;
    String ten;
    int gia;
    int daBan;

    public SanPham( int hinh, int id,String ten, int gia, int daBan) {
        this.id = id;
        this.hinh = hinh;
        this.ten = ten;
        this.gia = gia;
        this.daBan = daBan;
    }

    public int getId() {
        return id;
    }

    public int getHinh() {
        return hinh;
    }

    public String getTen() {
        return ten;
    }

    public int getGia() {
        return gia;
    }

    public int getDaBan() {
        return daBan;
    }
}