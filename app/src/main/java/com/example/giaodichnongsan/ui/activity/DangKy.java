package com.example.giaodichnongsan.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.giaodichnongsan.R;
import com.example.giaodichnongsan.viewmodel.AuthViewModel;

public class DangKy extends AppCompatActivity {

    private EditText edtHoTen, edtSoDienThoai, edtEmail, edtMatKhau, edtNhapLai;
    private Button btnDangKy;
    private TextView tvDangNhap;

    private AuthViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);

        initView();
        initViewModel();
        setupEvent();
    }

    private void initView() {
        edtHoTen       = findViewById(R.id.edtHoTen);
        edtSoDienThoai = findViewById(R.id.edtSoDienThoai);
        edtEmail       = findViewById(R.id.edtEmail);
        edtMatKhau     = findViewById(R.id.edtMatKhau);
        edtNhapLai     = findViewById(R.id.edtNhapLai);
        btnDangKy      = findViewById(R.id.btnDangKy);
        tvDangNhap     = findViewById(R.id.tvDangNhap);
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        viewModel.getIsLoading().observe(this, isLoading ->
                btnDangKy.setEnabled(!isLoading)
        );

        viewModel.getIsSuccess().observe(this, success -> {
            if (success == null || !success) return;
            showToast("Đăng ký thành công!");
            finish(); // quay về DangNhap
        });

        viewModel.getErrorMessage().observe(this, error -> {
            if (error != null) showToast("Đăng ký thất bại: " + error);
        });
    }

    private void setupEvent() {
        btnDangKy.setOnClickListener(v -> xuLyDangKy());
        tvDangNhap.setOnClickListener(v -> finish());
    }

    private void xuLyDangKy() {
        String hoTen   = edtHoTen.getText().toString().trim();
        String sdt     = edtSoDienThoai.getText().toString().trim();
        String email   = edtEmail.getText().toString().trim();
        String pass    = edtMatKhau.getText().toString().trim();
        String confirm = edtNhapLai.getText().toString().trim();

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

        viewModel.register(hoTen, sdt, email, pass);
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}