package com.example.giaodichnongsan;

import java.io.Serializable;

public class DonHang implements Serializable {
    private String tenSP;
    private int soLuong;
    private int tongTien;
    private String trangThai;

    public static final String DANG_GIAO = "Đang giao";
    public static final String DA_GIAO = "Đã giao";
    public static final String DA_HUY = "Đã huỷ";

    public DonHang(String tenSP, int soLuong, int tongTien, String trangThai) {
        this.tenSP = tenSP;
        this.soLuong = soLuong;
        this.tongTien = tongTien;
        this.trangThai = trangThai;
    }

    public String getTenSP() { return tenSP; }
    public int getSoLuong() { return soLuong; }
    public int getTongTien() { return tongTien; }
    public String getTrangThai() { return trangThai; }
}