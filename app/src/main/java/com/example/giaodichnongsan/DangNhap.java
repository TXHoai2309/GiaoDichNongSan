package com.example.giaodichnongsan;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

public class DangNhap extends AppCompatActivity {

    private EditText edtEmail, edtMatKhau;
    private Button btnDangNhap;
    private TextView tvDangKy, tvQuenMatKhau;

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

        tvDangKy.setOnClickListener(v -> {
            startActivity(new Intent(this, DangKy.class));
        });

        tvQuenMatKhau.setOnClickListener(v -> {
            startActivity(new Intent(this, QuenMatKhau.class));
        });
    }

    private void xuLyDangNhap() {

        String email = edtEmail.getText().toString().trim();
        String pass = edtMatKhau.getText().toString().trim();

        // ===== VALIDATE =====

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

        // ===== FAKE LOGIN (TẠM) =====
        showToast("Đăng nhập thành công");

        // 👉 chuyển màn
        startActivity(new Intent(this, MainActivity.class));
        finish();

        // ===== SAU NÀY (FIREBASE) =====
        /*
        FirebaseAuth.getInstance()
            .signInWithEmailAndPassword(email, pass)
            .addOnSuccessListener(authResult -> {
                startActivity(new Intent(this, MainActivity.class));
                finish();
            })
            .addOnFailureListener(e -> {
                showToast("Sai tài khoản hoặc mật khẩu");
            });
        */
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}