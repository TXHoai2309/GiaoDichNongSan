package com.example.giaodichnongsan;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DangKiBanHang extends AppCompatActivity {

    ImageView btnBack, btnMenu;
    LinearLayout btnGuiYeuCau;
    BottomNavigationView bottomNavDangKyBanHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ki_ban_hang);

        btnBack = findViewById(R.id.btnBackDangKyBanHang);
        btnMenu = findViewById(R.id.btnMenuDangKyBanHang);
        btnGuiYeuCau = findViewById(R.id.btnGuiYeuCau);
        bottomNavDangKyBanHang = findViewById(R.id.bottomNavDangKyBanHang);

        btnBack.setOnClickListener(v -> finish());

        btnMenu.setOnClickListener(v ->
                Toast.makeText(this, "Menu", Toast.LENGTH_SHORT).show()
        );

        btnGuiYeuCau.setOnClickListener(v ->
                Toast.makeText(this, "Gửi yêu cầu thành công", Toast.LENGTH_SHORT).show()
        );

        bottomNavDangKyBanHang.setSelectedItemId(R.id.nav_account);

        bottomNavDangKyBanHang.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                startActivity(new Intent(DangKiBanHang.this, TrangChu.class));
                finish();
                return true;
            } else if (id == R.id.nav_product) {
                return true;
            } else if (id == R.id.nav_order) {
                return true;
            } else if (id == R.id.nav_account) {
                startActivity(new Intent(DangKiBanHang.this, TaiKhoan.class));
                finish();
                return true;
            }

            return false;
        });
    }
}