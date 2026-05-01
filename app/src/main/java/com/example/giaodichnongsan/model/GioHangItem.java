package com.example.giaodichnongsan.model;

import java.io.Serializable;

public class GioHangItem implements Serializable {

    private SanPham sanPham;
    private int soLuong;
    private boolean selected;

    public GioHangItem() {} // bắt buộc cho Firestore

    public GioHangItem(SanPham sanPham, int soLuong) {
        this.sanPham  = sanPham;
        this.soLuong  = soLuong;
        this.selected = false;
    }

    public SanPham getSanPham()             { return sanPham; }
    public int getSoLuong()                 { return soLuong; }
    public boolean isSelected()             { return selected; }

    public void setSanPham(SanPham sanPham) { this.sanPham = sanPham; }
    public void setSoLuong(int soLuong)     { this.soLuong = soLuong; }
    public void setSelected(boolean selected) { this.selected = selected; }
}