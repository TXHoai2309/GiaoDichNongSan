package com.example.giaodichnongsan.data.repository;

import com.example.giaodichnongsan.data.fake.FakeDataSanPham;
import com.example.giaodichnongsan.model.SanPham;

import java.util.ArrayList;

public class SanPhamRepository {

    public ArrayList<SanPham> getSanPhamNoiBat() {
        return FakeDataSanPham.getSanPhamNoiBat();
    }

    public ArrayList<SanPham> getSanPhamMoi() {
        return FakeDataSanPham.getSanPhamMoi();
    }
}