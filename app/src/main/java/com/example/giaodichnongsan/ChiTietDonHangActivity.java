package com.example.giaodichnongsan;

import static android.content.Intent.getIntent;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ChiTietDonHangActivity extends AppCompatActivity {

    TextView tvTen, tvSoLuong, tvTien, tvTrangThai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiet_donhang);

        tvTen = findViewById(R.id.tvTen);
        tvSoLuong = findViewById(R.id.tvSoLuong);
        tvTien = findViewById(R.id.tvTien);
        tvTrangThai = findViewById(R.id.tvTrangThai);

        DonHang don = (DonHang) getIntent().getSerializableExtra("don");

        tvTen.setText(don.getTenSP());
        tvSoLuong.setText("Số lượng: " + don.getSoLuong());
        tvTien.setText("Tổng: " + don.getTongTien() + "đ");
        tvTrangThai.setText(don.getTrangThai());
    }
}