package com.example.giaodichnongsan;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class TaiKhoan extends AppCompatActivity {

    ImageView btnBack;
    LinearLayout itemThongTinCaNhan, itemDoiMatKhau, itemDangKyBanHang,
            itemVoucher, itemGioiThieu, itemDieuKhoan, itemTroGiup, layoutUser;
    BottomNavigationView bottomNavTaiKhoan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tai_khoan);

        btnBack = findViewById(R.id.btnBack);
        layoutUser = findViewById(R.id.layoutUser);
        itemThongTinCaNhan = findViewById(R.id.itemThongTinCaNhan);
        itemDoiMatKhau = findViewById(R.id.itemDoiMatKhau);
        itemDangKyBanHang = findViewById(R.id.itemDangKyBanHang);
        itemVoucher = findViewById(R.id.itemVoucher);
        itemGioiThieu = findViewById(R.id.itemGioiThieu);
        itemDieuKhoan = findViewById(R.id.itemDieuKhoan);
        itemTroGiup = findViewById(R.id.itemTroGiup);
        bottomNavTaiKhoan = findViewById(R.id.bottomNavTaiKhoan);

        btnBack.setOnClickListener(v -> finish());

        layoutUser.setOnClickListener(v ->
                Toast.makeText(this, "Mở hồ sơ cá nhân", Toast.LENGTH_SHORT).show());

        itemThongTinCaNhan.setOnClickListener(v ->
                Toast.makeText(this, "Thông tin cá nhân", Toast.LENGTH_SHORT).show());

        itemDoiMatKhau.setOnClickListener(v ->
                Toast.makeText(this, "Đổi mật khẩu", Toast.LENGTH_SHORT).show());

        itemDangKyBanHang.setOnClickListener(v -> {
            Intent intent = new Intent(TaiKhoan.this, DangKiBanHang.class);
            startActivity(intent);
        });

        itemVoucher.setOnClickListener(v ->
                Toast.makeText(this, "Voucher của tôi", Toast.LENGTH_SHORT).show());

        itemGioiThieu.setOnClickListener(v ->
                Toast.makeText(this, "Giới thiệu về chúng tôi", Toast.LENGTH_SHORT).show());

        itemDieuKhoan.setOnClickListener(v ->
                Toast.makeText(this, "Điều khoản và chính sách", Toast.LENGTH_SHORT).show());

        itemTroGiup.setOnClickListener(v ->
                Toast.makeText(this, "Trung tâm trợ giúp", Toast.LENGTH_SHORT).show());

        bottomNavTaiKhoan.setSelectedItemId(R.id.nav_account);

        bottomNavTaiKhoan.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                startActivity(new Intent(TaiKhoan.this, TrangChu.class));
                finish();
                return true;
            } else if (id == R.id.nav_product) {
                Toast.makeText(this, "Mở trang Sản phẩm", Toast.LENGTH_SHORT).show();
                return true;
            } else if (id == R.id.nav_order) {
                Toast.makeText(this, "Mở trang Đơn hàng", Toast.LENGTH_SHORT).show();
                return true;
            } else if (id == R.id.nav_account) {
                return true;
            }

            return false;
        });
    }
}