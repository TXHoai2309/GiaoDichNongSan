package com.example.giaodichnongsan.ui.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.giaodichnongsan.R;
import com.example.giaodichnongsan.ui.activity.DangNhap;

public class AdminFragment extends Fragment {

    private ImageView btnBack, btnMenu;
    private LinearLayout layoutQuanLyNguoiDung, layoutDuyetTaiKhoan, layoutQuanLyCuaHang, layoutBaoCaoViPham;
    private Button btnXemInfoA, btnXemInfoB, btnXemInfoC;
    private Button btnXemShop1, btnLoaiBoShop1, btnXemShop2, btnLoaiBoShop2;
    private TextView tvXemTatCaTaiKhoan;

    private Button btnLogoutAdmin;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_admin, container, false);

        anhXa(view);
        suKien();

        return view;
    }

    private void anhXa(View view) {
        btnBack = view.findViewById(R.id.btnBack);
        btnMenu = view.findViewById(R.id.btnMenu);

        layoutQuanLyNguoiDung = view.findViewById(R.id.layoutQuanLyNguoiDung);
        layoutDuyetTaiKhoan = view.findViewById(R.id.layoutDuyetTaiKhoan);
        layoutQuanLyCuaHang = view.findViewById(R.id.layoutQuanLyCuaHang);
        layoutBaoCaoViPham = view.findViewById(R.id.layoutBaoCaoViPham);

        btnXemInfoA = view.findViewById(R.id.btnXemInfoA);
        btnXemInfoB = view.findViewById(R.id.btnXemInfoB);
        btnXemInfoC = view.findViewById(R.id.btnXemInfoC);

        btnXemShop1 = view.findViewById(R.id.btnXemShop1);
        btnLoaiBoShop1 = view.findViewById(R.id.btnLoaiBoShop1);
        btnXemShop2 = view.findViewById(R.id.btnXemShop2);
        btnLoaiBoShop2 = view.findViewById(R.id.btnLoaiBoShop2);

        tvXemTatCaTaiKhoan = view.findViewById(R.id.tvXemTatCaTaiKhoan);

        btnLogoutAdmin = view.findViewById(R.id.btnLogoutAdmin);
    }

    private void suKien() {

        btnBack.setOnClickListener(v -> requireActivity().onBackPressed());

        layoutQuanLyNguoiDung.setOnClickListener(v ->
                openFragment(new QuanLyNguoiDungFragment())
        );

        layoutDuyetTaiKhoan.setOnClickListener(v ->
                openFragment(new QuanLyDuyetTaiKhoanFragment())
        );

        layoutQuanLyCuaHang.setOnClickListener(v ->
                openFragment(new AdQuanLyCuaHangFragment())
        );

        layoutBaoCaoViPham.setOnClickListener(v ->
                openFragment(new QuanLyBaoCaoFragment())
        );

        // 🔥 LOGOUT ADMIN (QUAN TRỌNG NHẤT)
        btnLogoutAdmin.setOnClickListener(v -> {

            SharedPreferences prefs = requireActivity()
                    .getSharedPreferences("USER", requireContext().MODE_PRIVATE);

            prefs.edit().clear().apply();

            Toast.makeText(getContext(), "Đã đăng xuất admin", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(getContext(), DangNhap.class));
            requireActivity().finish();
        });
    }

    private void openFragment(Fragment fragment) {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .addToBackStack(null)
                .commit();
    }
}