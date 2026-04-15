package com.example.giaodichnongsan;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class QuenMatKhau extends AppCompatActivity {

    EditText edtEmail;
    Button btnGui;
    TextView tvQuayLai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quen_mat_khau);

        edtEmail = findViewById(R.id.edtEmail);
        btnGui = findViewById(R.id.btnGui);
        tvQuayLai = findViewById(R.id.tvQuayLai);

        // Xử lý nút gửi
        btnGui.setOnClickListener(v -> {
            String email = edtEmail.getText().toString().trim();

            if (email.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập email", Toast.LENGTH_SHORT).show();
            } else {
                // chuyển sang màn nhập OTP
                Intent intent = new Intent(QuenMatKhau.this, NhapOTP.class);
                startActivity(intent);
            }
        });

        // Quay lại
        tvQuayLai.setOnClickListener(v -> finish());
    }
}