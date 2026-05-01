package com.example.giaodichnongsan.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.giaodichnongsan.R;

public class DatHangThanhCongActivity extends AppCompatActivity {

    Button btnVeTrangChu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dat_hang_thanh_cong);

        btnVeTrangChu = findViewById(R.id.btnVeTrangChu);

        btnVeTrangChu.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });
    }
}