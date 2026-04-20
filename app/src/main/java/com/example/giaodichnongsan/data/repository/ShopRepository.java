package com.example.giaodichnongsan.data.repository;

import com.example.giaodichnongsan.R;
import com.example.giaodichnongsan.data.fake.FakeDataSanPham;
import com.example.giaodichnongsan.model.SanPham;
import com.example.giaodichnongsan.model.Shop;

import java.util.ArrayList;
import java.util.List;

public class ShopRepository {

    // ===== LẤY SHOP =====
    public Shop getShopById(int shopId) {

        // Fake tạm
        return new Shop(
                shopId,
                "Shop " + shopId,
                R.drawable.ic_shop,
                4.5f,
                10,
                100,
                "2 năm",
                "Hà Nội",
                "0123456789",
                "Shop nông sản sạch"
        );
    }

    // ===== LẤY SẢN PHẨM THEO SHOP =====
    public List<SanPham> getSanPhamByShop(int shopId) {

        List<SanPham> result = new ArrayList<>();

        for (SanPham sp : FakeDataSanPham.getAll()) {
            if (sp.getShopId() == shopId) {
                result.add(sp);
            }
        }

        return result;
    }
}