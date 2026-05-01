package com.example.giaodichnongsan.ui.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.example.giaodichnongsan.R;

public class NhapOTP extends AppCompatActivity {

    EditText edtOTP;
    Button btnXacNhan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhap_otp);

        edtOTP = findViewById(R.id.edtOTP);
        btnXacNhan = findViewById(R.id.btnXacNhan);

        btnXacNhan.setOnClickListener(v -> {
            String otp = edtOTP.getText().toString().trim();

            if (otp.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập OTP", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(NhapOTP.this, NhapLaiMatKhau.class);
                startActivity(intent);
            }
        });
    }
}