package com.example.giaodichnongsan.data.repository;

import com.example.giaodichnongsan.data.fake.FakeDataSanPham;
import com.example.giaodichnongsan.model.DanhMuc;
import com.example.giaodichnongsan.model.SanPham;

import java.util.List;

public class TrangChuRepository {

    // ===== LẤY SẢN PHẨM NỔI BẬT =====
    public List<SanPham> getSanPhamNoiBat() {
        return FakeDataSanPham.getSanPhamNoiBat();
    }

    // ===== LẤY SẢN PHẨM MỚI =====
    public List<SanPham> getSanPhamMoi() {
        return FakeDataSanPham.getSanPhamMoi();
    }

    // ===== LẤY DANH MỤC =====
    public List<DanhMuc> getDanhMuc() {
        return FakeDataSanPham.getDanhMuc();
    }
}