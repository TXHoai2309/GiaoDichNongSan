package com.example.giaodichnongsan.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.giaodichnongsan.R;

public class HelpCenterFragment extends Fragment {

    private TextView tvPhone, tvEmail, btnSend;

    public HelpCenterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_help_center, container, false);

        tvPhone = view.findViewById(R.id.tvPhone);
        tvEmail = view.findViewById(R.id.tvEmail);
        btnSend = view.findViewById(R.id.btnSend);

        setupEvent();

        return view;
    }

    private void setupEvent() {

        // 📞 Gọi điện
        tvPhone.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:0987654321"));
            startActivity(intent);
        });

        // 📩 Gửi email
        tvEmail.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("message/rfc822");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"support@giaodichnongsan.com"});
            intent.putExtra(Intent.EXTRA_SUBJECT, "Hỗ trợ ứng dụng");

            try {
                startActivity(Intent.createChooser(intent, "Chọn ứng dụng gửi mail"));
            } catch (Exception e) {
                Toast.makeText(getContext(), "Không có ứng dụng email", Toast.LENGTH_SHORT).show();
            }
        });

        // 💬 Gửi feedback
        btnSend.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Đã gửi phản hồi!", Toast.LENGTH_SHORT).show();
        });
    }
}