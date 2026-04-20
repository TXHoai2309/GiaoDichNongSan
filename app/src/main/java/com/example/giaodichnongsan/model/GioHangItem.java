package com.example.giaodichnongsan.model;

import java.io.Serializable;

public class GioHangItem implements Serializable {

    private SanPham sanPham;
    private int soLuong;
    private boolean selected; // 🔥 THÊM DÒNG NÀY

    public GioHangItem(SanPham sanPham, int soLuong) {
        this.sanPham = sanPham;
        this.soLuong = soLuong;
        this.selected = false; // mặc định chưa chọn
    }

    // ===== GETTER =====
    public SanPham getSanPham() {
        return sanPham;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public boolean isSelected() { // 🔥 THÊM
        return selected;
    }

    // ===== SETTER =====
    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public void setSelected(boolean selected) { // 🔥 THÊM
        this.selected = selected;
    }
}