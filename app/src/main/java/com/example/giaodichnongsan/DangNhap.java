package com.example.giaodichnongsan;

import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DangNhap extends AppCompatActivity {

    TextView tvForgotPassword, tvRegister; //  (1) THÊM tvRegister

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        // Ánh xạ view
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        tvRegister = findViewById(R.id.tvRegister); //  (2) THÊM dòng này

        // Bấm vào "Quên mật khẩu?"
        tvForgotPassword.setOnClickListener(v -> {
            Intent intent = new Intent(DangNhap.this, QuenMatKhau.class);
            startActivity(intent);
        });

        //  (3) THÊM xử lý chuyển sang Đăng ký
        tvRegister.setOnClickListener(v -> {
            Intent intent = new Intent(DangNhap.this, DangKy.class);
            startActivity(intent);
        });

        Button btnLogin;
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(DangNhap.this, TrangChu.class);
            startActivity(intent);
        });
    }
}