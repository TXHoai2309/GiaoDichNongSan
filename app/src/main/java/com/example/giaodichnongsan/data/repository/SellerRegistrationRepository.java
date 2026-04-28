package com.example.giaodichnongsan.data.repository;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.giaodichnongsan.model.SellerRegistrationRequest;

public class SellerRegistrationRepository {

    public void submitSellerRequest(Context context, SellerRegistrationRequest request) {
        SharedPreferences.Editor editor = context
                .getSharedPreferences("SELLER_REGISTER", Context.MODE_PRIVATE)
                .edit();

        editor.putString("hoTen", request.getHoTen());
        editor.putString("soDienThoai", request.getSoDienThoai());
        editor.putString("email", request.getEmail());
        editor.putString("cccd", request.getCccd());
        editor.putString("diaChiCuTru", request.getDiaChiCuTru());

        editor.putString("tenGianHang", request.getTenGianHang());
        editor.putString("moTaGianHang", request.getMoTaGianHang());
        editor.putString("diaChiKinhDoanh", request.getDiaChiKinhDoanh());
        editor.putString("loaiNongSan", request.getLoaiNongSan());
        editor.putString("nguonGocSanPham", request.getNguonGocSanPham());
        editor.putString("giayChungNhanATTP", request.getGiayChungNhanATTP());
        editor.putString("giayChungNhanVietGap", request.getGiayChungNhanVietGap());

        editor.putString("soTaiKhoan", request.getSoTaiKhoan());
        editor.putString("tenChuTaiKhoan", request.getTenChuTaiKhoan());
        editor.putString("trangThai", request.getTrangThai());

        editor.apply();

        // Sau này thay phần SharedPreferences này bằng Firebase:
        // FirebaseFirestore.getInstance()
        //      .collection("seller_requests")
        //      .add(request);
    }
}