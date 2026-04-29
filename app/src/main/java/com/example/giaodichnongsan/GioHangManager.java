package com.example.giaodichnongsan;

import java.util.ArrayList;

public class GioHangManager {

    public static ArrayList<GioHangItem> gioHang = new ArrayList<>();

    // thêm sản phẩm
    public static void themSanPham(SanPham sp, int soLuong, String tenShop) {

        for (GioHangItem item : gioHang) {
            if (item.getSanPham().getId() == sp.getId()) {
                item.setSoLuong(item.getSoLuong() + soLuong);
                return;
            }
        }

        gioHang.add(new GioHangItem(sp, soLuong, tenShop));
    }

    // tổng tiền
    public static int getTongTien() {
        int tong = 0;

        for (GioHangItem item : gioHang) {
            if (item.isChecked()) { // 🔥 chỉ tính item được chọn
                tong += item.getSanPham().getGia() * item.getSoLuong();
            }
        }
        return tong;
    }
}