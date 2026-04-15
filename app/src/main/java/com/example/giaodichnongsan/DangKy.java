package com.example.giaodichnongsan;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DangKy extends AppCompatActivity {

    TextView tvDangNhap;
    Button btnDangKy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);

        tvDangNhap = findViewById(R.id.tvDangNhap);
        btnDangKy = findViewById(R.id.btnDangKy);

        btnDangKy.setOnClickListener(v -> {
            Intent intent = new Intent(DangKy.this, DangNhap.class);
            startActivity(intent);
            finish();
        });

        tvDangNhap.setOnClickListener(v -> {
            Intent intent = new Intent(DangKy.this, DangNhap.class);
            startActivity(intent);
            finish();
        });
    }
}