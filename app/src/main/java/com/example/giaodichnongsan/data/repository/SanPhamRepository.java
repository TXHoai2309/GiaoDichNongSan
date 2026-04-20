package com.example.giaodichnongsan.data.repository;

import com.example.giaodichnongsan.data.fake.FakeDataSanPham;
import com.example.giaodichnongsan.model.SanPham;

import java.util.List;

public class SanPhamRepository {

    public SanPham getSanPhamById(int id) {

        List<SanPham> list = FakeDataSanPham.getAll(); // hoặc gộp list nếu bạn đang tách

        for (SanPham sp : list) {
            if (sp.getId() == id) {
                return sp;
            }
        }
        return null;
    }
}