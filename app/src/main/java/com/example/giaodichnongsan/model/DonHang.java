package com.example.giaodichnongsan.model;

import java.io.Serializable;
import java.util.ArrayList;

public class DonHang implements Serializable {

    private int id;
    private ArrayList<GioHangItem> listSanPham;
    private int tongTien;
    private String trangThai;
    private String ngayDat;

    public static final String DANG_GIAO = "Đang giao";
    public static final String DA_GIAO = "Đã giao";
    public static final String DA_HUY = "Đã huỷ";

    public DonHang(int id,
                   ArrayList<GioHangItem> listSanPham,
                   int tongTien,
                   String trangThai,
                   String ngayDat) {

        this.id = id;
        this.listSanPham = listSanPham;
        this.tongTien = tongTien;
        this.trangThai = trangThai;
        this.ngayDat = ngayDat;
    }

    public int getId() { return id; }
    public ArrayList<GioHangItem> getListSanPham() { return listSanPham; }
    public int getTongTien() { return tongTien; }
    public String getTrangThai() { return trangThai; }
    public String getNgayDat() { return ngayDat; }
}