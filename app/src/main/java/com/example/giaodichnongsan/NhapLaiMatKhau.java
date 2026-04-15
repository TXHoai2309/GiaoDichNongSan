package com.example.giaodichnongsan;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class NhapLaiMatKhau extends AppCompatActivity {

    EditText edtMatKhauMoi, edtNhapLai;
    Button btnXacNhan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhap_lai_mat_khau);

        edtMatKhauMoi = findViewById(R.id.edtMatKhauMoi);
        edtNhapLai = findViewById(R.id.edtNhapLai);
        btnXacNhan = findViewById(R.id.btnXacNhan);

        btnXacNhan.setOnClickListener(v -> {
            String pass1 = edtMatKhauMoi.getText().toString();
            String pass2 = edtNhapLai.getText().toString();

            if (pass1.isEmpty() || pass2.isEmpty()) {
                Toast.makeText(this, "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            } else if (!pass1.equals(pass2)) {
                Toast.makeText(this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();

                // Chuyển về màn đăng nhập
                Intent intent = new Intent(NhapLaiMatKhau.this, DangNhap.class);
                startActivity(intent);

                // Xóa stack (không quay lại được nữa)
                finishAffinity();
            }
        });
    }
}