package com.example.giaodichnongsan;

import java.util.ArrayList;

public class DonHangManager {

    public static ArrayList<DonHang> danhSachDon = new ArrayList<>();

    public static void themDon(DonHang don) {
        danhSachDon.add(0, don); // thêm lên đầu
    }

    public static ArrayList<DonHang> getDanhSach() {
        return danhSachDon;
    }
}