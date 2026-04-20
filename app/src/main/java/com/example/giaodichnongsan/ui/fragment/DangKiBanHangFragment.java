package com.example.giaodichnongsan.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.giaodichnongsan.R;

public class DangKiBanHangFragment extends Fragment {

    ImageView btnBack, btnMenu;
    LinearLayout btnGuiYeuCau;

    public DangKiBanHangFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dang_ky_ban_hang, container, false);

        btnBack = view.findViewById(R.id.btnBackDangKyBanHang);
        btnMenu = view.findViewById(R.id.btnMenuDangKyBanHang);
        btnGuiYeuCau = view.findViewById(R.id.btnGuiYeuCau);

        btnBack.setOnClickListener(v ->
                requireActivity().getSupportFragmentManager().popBackStack()
        );

        btnMenu.setOnClickListener(v ->
                Toast.makeText(getContext(), "Menu", Toast.LENGTH_SHORT).show()
        );

        btnGuiYeuCau.setOnClickListener(v -> {
            requireActivity()
                    .getSharedPreferences("USER", requireActivity().MODE_PRIVATE)
                    .edit()
                    .putBoolean("isSeller", true)
                    .apply();

            Toast.makeText(getContext(), "Gửi yêu cầu thành công", Toast.LENGTH_SHORT).show();

            requireActivity().getSupportFragmentManager().popBackStack();
        });

        return view;
    }
}