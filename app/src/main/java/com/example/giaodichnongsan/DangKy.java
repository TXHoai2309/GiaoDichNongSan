package com.example.giaodichnongsan;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

public class DangKy extends AppCompatActivity {

    private EditText edtHoTen, edtSoDienThoai, edtEmail, edtMatKhau, edtNhapLai;
    private Button btnDangKy;
    private TextView tvDangNhap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);

        initView();
        setupEvent();
    }

    private void initView() {
        edtHoTen = findViewById(R.id.edtHoTen);
        edtSoDienThoai = findViewById(R.id.edtSoDienThoai);
        edtEmail = findViewById(R.id.edtEmail);
        edtMatKhau = findViewById(R.id.edtMatKhau);
        edtNhapLai = findViewById(R.id.edtNhapLai);

        btnDangKy = findViewById(R.id.btnDangKy);
        tvDangNhap = findViewById(R.id.tvDangNhap);
    }

    private void setupEvent() {

        btnDangKy.setOnClickListener(v -> xuLyDangKy());

        tvDangNhap.setOnClickListener(v -> finish());
    }

    private void xuLyDangKy() {

        String hoTen = edtHoTen.getText().toString().trim();
        String sdt = edtSoDienThoai.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String pass = edtMatKhau.getText().toString().trim();
        String confirm = edtNhapLai.getText().toString().trim();

        // ===== VALIDATE =====

        if (TextUtils.isEmpty(hoTen) || TextUtils.isEmpty(sdt)
                || TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)
                || TextUtils.isEmpty(confirm)) {
            showToast("Vui lòng nhập đầy đủ thông tin");
            return;
        }

        if (sdt.length() < 9) {
            showToast("Số điện thoại không hợp lệ");
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showToast("Email không hợp lệ");
            return;
        }

        if (pass.length() < 6) {
            showToast("Mật khẩu phải >= 6 ký tự");
            return;
        }

        if (!pass.equals(confirm)) {
            showToast("Mật khẩu không khớp");
            return;
        }

        // ===== FAKE ĐĂNG KÝ (TẠM) =====
        showToast("Đăng ký thành công");

        // 👉 Sau này thay bằng Firebase:
        // 1. createUser(email, pass)
        // 2. lưu hoTen + sdt lên Firestore

        finish();
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}