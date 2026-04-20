package com.example.giaodichnongsan.data.repository;

import com.example.giaodichnongsan.model.DonHang;
import com.example.giaodichnongsan.model.GioHangItem;

import java.util.ArrayList;

public class DonHangRepository {

    private static ArrayList<DonHang> donHangList = new ArrayList<>();
    private static int idCounter = 1;

    public void addDonHang(ArrayList<GioHangItem> list, int tongTien) {

        DonHang donHang = new DonHang(
                idCounter++,
                new ArrayList<>(list), // 🔥 copy list
                tongTien,
                DonHang.DANG_GIAO,
                java.text.DateFormat.getDateTimeInstance().format(new java.util.Date())
        );

        donHangList.add(donHang);
    }

    public ArrayList<DonHang> getAllDonHang() {
        return donHangList;
    }
}