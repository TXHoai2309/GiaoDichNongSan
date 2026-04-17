package com.example.giaodichnongsan;

public class GioHangItem {

    private SanPham sanPham;
    private int soLuong;
    private String tenShop; // 🔥 thêm shop
    private boolean isChecked = true; // mặc định chọn

    public GioHangItem(SanPham sanPham, int soLuong, String tenShop) {
        this.sanPham = sanPham;
        this.soLuong = soLuong;
        this.tenShop = tenShop;
    }
    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
    public SanPham getSanPham() {
        return sanPham;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getTenShop() {
        return tenShop;
    }

    public void setTenShop(String tenShop) {
        this.tenShop = tenShop;
    }
}