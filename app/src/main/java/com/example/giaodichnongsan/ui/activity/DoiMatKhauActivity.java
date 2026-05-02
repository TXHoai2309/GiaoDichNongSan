package com.example.giaodichnongsan.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.giaodichnongsan.R;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DoiMatKhauActivity extends AppCompatActivity {

    private ImageView btnBack;
    private EditText edtMatKhauCu, edtMatKhauMoi, edtNhapLaiMatKhau;
    private Button btnCapNhat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doi_mat_khau);

        initView();
        setupEvent();
    }

    private void initView() {
        btnBack = findViewById(R.id.btnBack);
        edtMatKhauCu = findViewById(R.id.edtMatKhauCu);
        edtMatKhauMoi = findViewById(R.id.edtMatKhauMoi);
        edtNhapLaiMatKhau = findViewById(R.id.edtNhapLaiMatKhau);
        btnCapNhat = findViewById(R.id.btnCapNhat);
    }

    private void setupEvent() {
        btnBack.setOnClickListener(v -> finish());
        btnCapNhat.setOnClickListener(v -> xuLyDoiMatKhau());
    }

    private void xuLyDoiMatKhau() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null || user.getEmail() == null) {
            Toast.makeText(this, "Vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        String matKhauCu = edtMatKhauCu.getText().toString().trim();
        String matKhauMoi = edtMatKhauMoi.getText().toString().trim();
        String nhapLai = edtNhapLaiMatKhau.getText().toString().trim();

        if (TextUtils.isEmpty(matKhauCu)
                || TextUtils.isEmpty(matKhauMoi)
                || TextUtils.isEmpty(nhapLai)) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        if (matKhauMoi.length() < 6) {
            Toast.makeText(this, "Mật khẩu mới phải có ít nhất 6 ký tự", Toast.LENGTH_SHORT).show();
            return;
        }

        if (matKhauCu.equals(matKhauMoi)) {
            Toast.makeText(this, "Mật khẩu mới không được trùng mật khẩu cũ", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!matKhauMoi.equals(nhapLai)) {
            Toast.makeText(this, "Mật khẩu mới không khớp", Toast.LENGTH_SHORT).show();
            return;
        }

        setLoading(true);
        user.reauthenticate(EmailAuthProvider.getCredential(user.getEmail(), matKhauCu))
                .addOnSuccessListener(authResult ->
                        user.updatePassword(matKhauMoi)
                                .addOnSuccessListener(unused -> {
                                    setLoading(false);
                                    Toast.makeText(this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                    finish();
                                })
                                .addOnFailureListener(e -> {
                                    setLoading(false);
                                    Toast.makeText(this, "Đổi mật khẩu thất bại: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                })
                )
                .addOnFailureListener(e -> {
                    setLoading(false);
                    Toast.makeText(this, "Mật khẩu hiện tại không đúng", Toast.LENGTH_SHORT).show();
                });
    }

    private void setLoading(boolean loading) {
        btnCapNhat.setEnabled(!loading);
        btnCapNhat.setText(loading ? "Đang cập nhật..." : "Cập nhật mật khẩu");
    }
}
