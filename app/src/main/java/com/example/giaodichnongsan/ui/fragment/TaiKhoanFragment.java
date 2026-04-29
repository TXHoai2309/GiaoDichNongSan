package com.example.giaodichnongsan.ui.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.fragment.app.Fragment;

import com.example.giaodichnongsan.R;
import com.example.giaodichnongsan.ui.activity.DangKy;
import com.example.giaodichnongsan.ui.activity.DangNhap;
import com.example.giaodichnongsan.ui.activity.MainActivity;
import com.example.giaodichnongsan.utils.AuthHelper;

public class TaiKhoanFragment extends Fragment {

    ImageView btnBack;
    LinearLayout itemThongTinCaNhan, itemDoiMatKhau, itemDangKyBanHang,
            itemVoucher, itemGioiThieu, itemDieuKhoan, itemTroGiup, layoutUser,
            itemQuanLyCuaHang;

    TextView tvUserName, tvPhone, tvDangNhapTaiKhoan, tvDangKyTaiKhoan;

    Switch switchCamUng, switchDoSang;
    SharedPreferences prefs;

    // 🔥 THÊM
    Button btnLogoutUser;

    public TaiKhoanFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_taikhoan, container, false);

        btnBack = view.findViewById(R.id.btnBack);
        layoutUser = view.findViewById(R.id.layoutUser);

        tvUserName = view.findViewById(R.id.tvUserName);
        tvPhone = view.findViewById(R.id.tvPhone);
        tvDangNhapTaiKhoan = view.findViewById(R.id.tvDangNhapTaiKhoan);
        tvDangKyTaiKhoan = view.findViewById(R.id.tvDangKyTaiKhoan);

        // 🔥 ÁNH XẠ
        btnLogoutUser = view.findViewById(R.id.btnLogoutUser);

        itemThongTinCaNhan = view.findViewById(R.id.itemThongTinCaNhan);
        itemDoiMatKhau = view.findViewById(R.id.itemDoiMatKhau);
        itemDangKyBanHang = view.findViewById(R.id.itemDangKyBanHang);
        itemGioiThieu = view.findViewById(R.id.itemGioiThieu);
        itemDieuKhoan = view.findViewById(R.id.itemDieuKhoan);
        itemTroGiup = view.findViewById(R.id.itemTroGiup);
        itemQuanLyCuaHang = view.findViewById(R.id.itemQuanLyCuaHang);

        prefs = requireActivity().getSharedPreferences("settings", MODE_PRIVATE);

        capNhatTrangThaiDangNhap();
        capNhatTrangThaiSeller();

        btnBack.setOnClickListener(v ->
                requireActivity().getSupportFragmentManager().popBackStack()
        );

        tvDangNhapTaiKhoan.setOnClickListener(v ->
                startActivity(new Intent(requireContext(), DangNhap.class))
        );

        tvDangKyTaiKhoan.setOnClickListener(v ->
                startActivity(new Intent(requireContext(), DangKy.class))
        );

        // 🔥 LOGOUT USER
        btnLogoutUser.setOnClickListener(v -> {

            SharedPreferences prefs = requireActivity()
                    .getSharedPreferences("USER", MODE_PRIVATE);

            prefs.edit().clear().apply();

            Toast.makeText(getContext(), "Đã đăng xuất", Toast.LENGTH_SHORT).show();

            // reload lại fragment
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameLayout, new TaiKhoanFragment())
                    .commit();
        });

        return view;
    }

    private boolean daDangNhap() {
        return requireActivity()
                .getSharedPreferences("USER", MODE_PRIVATE)
                .getBoolean("isLoggedIn", false);
    }

    private void capNhatTrangThaiDangNhap() {

        if (daDangNhap()) {
            tvUserName.setText("Người dùng");
            tvPhone.setText("Đã đăng nhập");

            tvDangNhapTaiKhoan.setVisibility(View.GONE);
            tvDangKyTaiKhoan.setVisibility(View.GONE);

            btnLogoutUser.setVisibility(View.VISIBLE); // 🔥 HIỆN
        } else {
            tvUserName.setText("Khách");
            tvPhone.setText("Bạn chưa đăng nhập");

            tvDangNhapTaiKhoan.setVisibility(View.VISIBLE);
            tvDangKyTaiKhoan.setVisibility(View.VISIBLE);

            btnLogoutUser.setVisibility(View.GONE); // 🔥 ẨN
        }
    }

    private void capNhatTrangThaiSeller() {
        boolean isSeller = requireActivity()
                .getSharedPreferences("USER", MODE_PRIVATE)
                .getBoolean("isSeller", false);

        if (isSeller) {
            itemDangKyBanHang.setVisibility(View.GONE);
            itemQuanLyCuaHang.setVisibility(View.VISIBLE);
        } else {
            itemDangKyBanHang.setVisibility(View.VISIBLE);
            itemQuanLyCuaHang.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        capNhatTrangThaiDangNhap();
        capNhatTrangThaiSeller();
    }
}