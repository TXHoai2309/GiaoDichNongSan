package com.example.giaodichnongsan;

import java.util.ArrayList;

public class FakeDataSanPham {

    public static ArrayList<SanPham> getSanPhamNoiBat() {
        ArrayList<SanPham> list = new ArrayList<>();

        list.add(new SanPham(1, R.drawable.ic_sup_lo, "Súp lơ nhà trồng", 15000, 120,
                "Súp lơ sạch", "Đà Lạt", 4.5f, "Shop A"));

        list.add(new SanPham(2, R.drawable.ic_ca_chua, "Cà chua tươi", 25000, 98,
                "Cà chua sạch", "Lâm Đồng", 4.2f, "Shop B"));

        list.add(new SanPham(3, R.drawable.ic_ngo, "Ngô ngọt", 20000, 200,
                "Ngô sạch", "Hà Nội", 4.7f, "Shop C"));

        return list;
    }

    public static ArrayList<SanPham> getSanPhamMoi() {
        ArrayList<SanPham> list = new ArrayList<>();

        list.add(new SanPham(4, R.drawable.ic_ca_rot, "Cà rốt", 18000, 75,
                "Cà rốt sạch", "Đà Lạt", 4.3f, "Shop D"));

        list.add(new SanPham(5, R.drawable.ic_dau_ha_lan, "Đậu hà lan", 30000, 60,
                "Đậu sạch", "Sapa", 4.6f, "Shop E"));

        list.add(new SanPham(6, R.drawable.ic_khoai_tay, "Khoai tây", 22000, 150,
                "Khoai sạch", "Đà Lạt", 4.8f, "Shop F"));

        return list;
    }

    public static ArrayList<DanhMuc> getDanhMuc() {
        ArrayList<DanhMuc> list = new ArrayList<>();

        list.add(new DanhMuc(R.drawable.ic_rau, "Rau củ"));
        list.add(new DanhMuc(R.drawable.ic_traicay, "Trái cây"));
        list.add(new DanhMuc(R.drawable.ic_gao, "Gạo"));
        list.add(new DanhMuc(R.drawable.ic_more, "Thêm"));

        return list;
    }
}