package com.example.giaodichnongsan.data.fake;

import com.example.giaodichnongsan.R;
import com.example.giaodichnongsan.model.DanhMuc;
import com.example.giaodichnongsan.model.SanPham;

import java.util.ArrayList;

public class FakeDataSanPham {

    // ===== SẢN PHẨM NỔI BẬT =====
    public static ArrayList<SanPham> getSanPhamNoiBat() {
        ArrayList<SanPham> list = new ArrayList<>();

        list.add(new SanPham(1, R.drawable.ic_sup_lo, "Súp lơ nhà trồng", 15000, 120,
                "Súp lơ sạch", "Đà Lạt", 4.5f, "Shop A", 101));

        list.add(new SanPham(2, R.drawable.ic_ca_chua, "Cà chua tươi", 25000, 98,
                "Cà chua sạch", "Lâm Đồng", 4.2f, "Shop B", 102));

        list.add(new SanPham(3, R.drawable.ic_ngo, "Ngô ngọt", 20000, 200,
                "Ngô sạch", "Hà Nội", 4.7f, "Shop C", 103));

        return list;
    }

    // ===== SẢN PHẨM MỚI =====
    public static ArrayList<SanPham> getSanPhamMoi() {
        ArrayList<SanPham> list = new ArrayList<>();

        list.add(new SanPham(4, R.drawable.ic_ca_rot, "Cà rốt", 18000, 75,
                "Cà rốt sạch", "Đà Lạt", 4.3f, "Shop D", 104));

        list.add(new SanPham(5, R.drawable.ic_dau_ha_lan, "Đậu hà lan", 30000, 60,
                "Đậu sạch", "Sapa", 4.6f, "Shop E", 105));

        list.add(new SanPham(6, R.drawable.ic_khoai_tay, "Khoai tây", 22000, 150,
                "Khoai sạch", "Đà Lạt", 4.8f, "Shop F", 106));

        return list;
    }

    // ===== DANH MỤC =====
    public static ArrayList<DanhMuc> getDanhMuc() {
        ArrayList<DanhMuc> list = new ArrayList<>();

        list.add(new DanhMuc(R.drawable.ic_rau, "Rau củ"));
        list.add(new DanhMuc(R.drawable.ic_traicay, "Trái cây"));
        list.add(new DanhMuc(R.drawable.ic_gao, "Gạo"));
        list.add(new DanhMuc(R.drawable.ic_more, "Thêm"));

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