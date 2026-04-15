package com.example.giaodichnongsan;

import android.os.Bundle;
import android.content.Intent;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

public class DangNhap extends AppCompatActivity {
    TextView tvForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        // Ánh xạ view
        tvForgotPassword = findViewById(R.id.tvForgotPassword);

        // Bấm vào "Quên mật khẩu?"
        tvForgotPassword.setOnClickListener(v -> {
            Intent intent = new Intent(DangNhap.this, QuenMatKhau.class);
            startActivity(intent);
        });
    }
}