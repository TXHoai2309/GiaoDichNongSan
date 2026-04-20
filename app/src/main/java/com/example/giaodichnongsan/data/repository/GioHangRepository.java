package com.example.giaodichnongsan.data.repository;

import com.example.giaodichnongsan.model.GioHangItem;
import com.example.giaodichnongsan.model.SanPham;

import java.util.ArrayList;

public class GioHangRepository {

    // giả lập database (tạm thời)
    private static ArrayList<GioHangItem> gioHangList = new ArrayList<>();

    // ===== LẤY DANH SÁCH =====
    public ArrayList<GioHangItem> getGioHang() {
        return gioHangList;
    }

    // ===== THÊM SẢN PHẨM =====
    public void addToCart(SanPham sanPham) {

        for (GioHangItem item : gioHangList) {
            if (item.getSanPham().getId() == sanPham.getId()) {
                item.setSoLuong(item.getSoLuong() + 1);
                return;
            }
        }

        gioHangList.add(new GioHangItem(sanPham, 1));
    }

    // ===== XOÁ SẢN PHẨM =====
    public void removeItem(GioHangItem item) {
        gioHangList.remove(item);
    }

    // ===== TĂNG SỐ LƯỢNG =====
    public void increaseQuantity(GioHangItem item) {
        item.setSoLuong(item.getSoLuong() + 1);
    }

    // ===== GIẢM SỐ LƯỢNG =====
    public void decreaseQuantity(GioHangItem item) {
        if (item.getSoLuong() > 1) {
            item.setSoLuong(item.getSoLuong() - 1);
        } else {
            gioHangList.remove(item);
        }
    }

    // ===== TÍNH TỔNG TIỀN =====
    public int getTotalPrice() {
        int total = 0;

        for (GioHangItem item : gioHangList) {
            total += item.getSanPham().getGia() * item.getSoLuong();
        }

        return total;
    }

    // ===== CLEAR GIỎ HÀNG =====
    public void clearCart() {
        gioHangList.clear();
    }
}