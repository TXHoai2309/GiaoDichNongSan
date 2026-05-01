package com.example.giaodichnongsan.data.fake;

import com.example.giaodichnongsan.R;
import com.example.giaodichnongsan.model.DanhMuc;
import com.example.giaodichnongsan.model.SanPham;

import java.util.ArrayList;

public class FakeDataSanPham {

    // ===== SẢN PHẨM NỔI BẬT =====
    // Thay toàn bộ data trong FakeDataSanPham.java
// Constructor mới không có hinh, dùng imageUrl = ""

    public static ArrayList<SanPham> getSanPhamNoiBat() {
        ArrayList<SanPham> list = new ArrayList<>();

        SanPham sp1 = new SanPham();
        sp1.setId("fake_1");
        sp1.setTen("Súp lơ nhà trồng");
        sp1.setGia(15000); sp1.setDaBan(120);
        sp1.setMoTa("Súp lơ sạch"); sp1.setNguonGoc("Đà Lạt");
        sp1.setDanhGia(4.5f); sp1.setDanhMuc("Rau củ");
        sp1.setTenShop("Shop A"); sp1.setShopId("shop_101");
        sp1.setImageUrl(""); sp1.setNoiBat(true);
        list.add(sp1);

        SanPham sp2 = new SanPham();
        sp2.setId("fake_2");
        sp2.setTen("Cà chua tươi");
        sp2.setGia(25000); sp2.setDaBan(98);
        sp2.setMoTa("Cà chua sạch"); sp2.setNguonGoc("Lâm Đồng");
        sp2.setDanhGia(4.2f); sp2.setDanhMuc("Rau củ");
        sp2.setTenShop("Shop B"); sp2.setShopId("shop_102");
        sp2.setImageUrl(""); sp2.setNoiBat(true);
        list.add(sp2);

        return list;
    }

    public static ArrayList<SanPham> getSanPhamMoi() {
        ArrayList<SanPham> list = new ArrayList<>();

        SanPham sp1 = new SanPham();
        sp1.setId("fake_4");
        sp1.setTen("Cà rốt");
        sp1.setGia(18000); sp1.setDaBan(75);
        sp1.setMoTa("Cà rốt sạch"); sp1.setNguonGoc("Đà Lạt");
        sp1.setDanhGia(4.3f); sp1.setDanhMuc("Rau củ");
        sp1.setTenShop("Shop D"); sp1.setShopId("shop_104");
        sp1.setImageUrl(""); sp1.setNoiBat(false);
        list.add(sp1);

        return list;
    }

    // ===== DANH MỤC =====
    public static ArrayList<DanhMuc> getDanhMuc() {
        ArrayList<DanhMuc> list = new ArrayList<>();

        list.add(new DanhMuc(R.drawable.ic_rau,     "Rau củ",  "Rau củ"));
        list.add(new DanhMuc(R.drawable.ic_traicay, "Trái cây","Trái cây"));
        list.add(new DanhMuc(R.drawable.ic_gao,     "Gạo",     "Gạo"));
        list.add(new DanhMuc(R.drawable.ic_more,    "Tất cả",  "all")); // nút reset filter

        return list;
    }

    // ===== LẤY TẤT CẢ SẢN PHẨM =====
    public static ArrayList<SanPham> getAll() {
        ArrayList<SanPham> list = new ArrayList<>();
        list.addAll(getSanPhamNoiBat());
        list.addAll(getSanPhamMoi());
        return list;
    }
}