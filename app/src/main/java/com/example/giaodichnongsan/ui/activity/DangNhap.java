package com.example.giaodichnongsan.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.example.giaodichnongsan.R;

public class DangNhap extends AppCompatActivity {

    private EditText edtEmail, edtMatKhau;
    private Button btnDangNhap;
    private TextView tvDangKy, tvQuenMatKhau;

    private static final String ADMIN_EMAIL = "admin@gmail.com";
    private static final String ADMIN_PASSWORD = "123456";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        initView();
        setupEvent();
    }

    private void initView() {
        edtEmail = findViewById(R.id.edtEmail);
        edtMatKhau = findViewById(R.id.edtMatKhau);
        btnDangNhap = findViewById(R.id.btnDangNhap);
        tvDangKy = findViewById(R.id.tvDangKy);
        tvQuenMatKhau = findViewById(R.id.tvQuenMatKhau);
    }

    private void setupEvent() {
        btnDangNhap.setOnClickListener(v -> xuLyDangNhap());

        tvDangKy.setOnClickListener(v ->
                startActivity(new Intent(this, DangKy.class))
        );

        tvQuenMatKhau.setOnClickListener(v ->
                startActivity(new Intent(this, QuenMatKhau.class))
        );
    }

    private void xuLyDangNhap() {
        String email = edtEmail.getText().toString().trim();
        String pass = edtMatKhau.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)) {
            showToast("Vui lòng nhập đầy đủ");
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showToast("Email không hợp lệ");
            return;
        }

        if (pass.length() < 6) {
            showToast("Mật khẩu không hợp lệ");
            return;
        }

        SharedPreferences.Editor editor = getSharedPreferences("USER", MODE_PRIVATE).edit();

        if (email.equals(ADMIN_EMAIL) && pass.equals(ADMIN_PASSWORD)) {
            editor.putBoolean("isLoggedIn", true);
            editor.putBoolean("isAdmin", true);
            editor.apply();

            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("openAdmin", true);
            startActivity(intent);
            finish();
            return;
        }

        editor.putBoolean("isLoggedIn", true);
        editor.putBoolean("isAdmin", false);
        editor.apply();

        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}