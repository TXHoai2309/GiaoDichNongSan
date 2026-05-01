package com.example.giaodichnongsan.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.giaodichnongsan.R;
import com.example.giaodichnongsan.viewmodel.AuthViewModel;

public class DangNhap extends AppCompatActivity {

    private EditText edtEmail, edtMatKhau;
    private Button btnDangNhap;
    private TextView tvDangKy, tvQuenMatKhau;
    private ProgressBar progressBar; // thêm vào layout nếu chưa có

    private AuthViewModel viewModel;

    private static final String ADMIN_EMAIL = "admin@gmail.com";
    private static final String ADMIN_PASSWORD = "123456";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        initView();
        initViewModel();
        setupEvent();
    }

    private void initView() {
        edtEmail    = findViewById(R.id.edtEmail);
        edtMatKhau  = findViewById(R.id.edtMatKhau);
        btnDangNhap = findViewById(R.id.btnDangNhap);
        tvDangKy    = findViewById(R.id.tvDangKy);
        tvQuenMatKhau = findViewById(R.id.tvQuenMatKhau);
        // progressBar = findViewById(R.id.progressBar);
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        // Đang loading
        viewModel.getIsLoading().observe(this, isLoading -> {
            btnDangNhap.setEnabled(!isLoading);
            // progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });

        // Đăng nhập thành công
        viewModel.getIsSuccess().observe(this, success -> {
            if (success == null || !success) return;

            // Lưu trạng thái login
            getSharedPreferences("USER", MODE_PRIVATE)
                    .edit()
                    .putBoolean("isLoggedIn", true)
                    .apply();

            startActivity(new Intent(this, MainActivity.class));
            finish();
        });

        // Lỗi
        viewModel.getErrorMessage().observe(this, error -> {
            if (error != null) showToast("Đăng nhập thất bại: " + error);
        });
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
        String pass  = edtMatKhau.getText().toString().trim();

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

        // Admin vẫn giữ fake như cũ
        if (email.equals(ADMIN_EMAIL) && pass.equals(ADMIN_PASSWORD)) {
            getSharedPreferences("USER", MODE_PRIVATE).edit()
                    .putBoolean("isLoggedIn", true)
                    .putBoolean("isAdmin", true)
                    .apply();
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("openAdmin", true);
            startActivity(intent);
            finish();
            return;
        }

        // User thường → Firebase
        viewModel.login(email, pass);
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}