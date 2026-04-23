package com.example.giaodichnongsan;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ChiTietDonHangActivity extends AppCompatActivity {

    private TextView tvTen, tvSoLuong, tvTien, tvTrangThai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiet_donhang);

        initView();
        loadData();
    }

    // ===== ÁNH XẠ VIEW =====
    private void initView() {
        tvTen = findViewById(R.id.tvTen);
        tvSoLuong = findViewById(R.id.tvSoLuong);
        tvTien = findViewById(R.id.tvTien);
        tvTrangThai = findViewById(R.id.tvTrangThai);
    }

    // ===== LOAD DATA =====
    private void loadData() {

        DonHang don = (DonHang) getIntent().getSerializableExtra("don");

        if (don == null) return;

        // ===== HIỂN THỊ DANH SÁCH SẢN PHẨM =====
        StringBuilder tenSP = new StringBuilder();
        int tongSoLuong = 0;

        if (don.getListSanPham() != null) {
            for (GioHangItem item : don.getListSanPham()) {
                tenSP.append(item.getSanPham().getTen())
                        .append(" x")
                        .append(item.getSoLuong())
                        .append("\n");

                tongSoLuong += item.getSoLuong();
            }
        }

        tvTen.setText(tenSP.toString().trim());
        tvSoLuong.setText("Tổng SL: " + tongSoLuong);
        tvTien.setText("Tổng: " + don.getTongTien() + "đ");
        tvTrangThai.setText(don.getTrangThai());
    }
}