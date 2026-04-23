package com.example.giaodichnongsan;

import java.util.ArrayList;

public class SanPhamRepository {

    public ArrayList<SanPham> getSanPhamNoiBat() {
        return FakeDataSanPham.getSanPhamNoiBat();
    }

    public ArrayList<SanPham> getSanPhamMoi() {
        return FakeDataSanPham.getSanPhamMoi();
    }
}