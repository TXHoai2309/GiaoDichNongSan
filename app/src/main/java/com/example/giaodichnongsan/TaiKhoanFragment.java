package com.example.giaodichnongsan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class TaiKhoanFragment extends Fragment {

    ImageView btnBack;
    LinearLayout itemThongTinCaNhan, itemDoiMatKhau, itemDangKyBanHang,
            itemVoucher, itemGioiThieu, itemDieuKhoan, itemTroGiup, layoutUser,
            itemQuanLyCuaHang;

    public TaiKhoanFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_taikhoan, container, false);

        btnBack = view.findViewById(R.id.btnBack);
        layoutUser = view.findViewById(R.id.layoutUser);
        itemThongTinCaNhan = view.findViewById(R.id.itemThongTinCaNhan);
        itemDoiMatKhau = view.findViewById(R.id.itemDoiMatKhau);
        itemDangKyBanHang = view.findViewById(R.id.itemDangKyBanHang);
        itemGioiThieu = view.findViewById(R.id.itemGioiThieu);
        itemDieuKhoan = view.findViewById(R.id.itemDieuKhoan);
        itemTroGiup = view.findViewById(R.id.itemTroGiup);
        itemQuanLyCuaHang = view.findViewById(R.id.itemQuanLyCuaHang);

        boolean isSeller = requireActivity()
                .getSharedPreferences("USER", requireActivity().MODE_PRIVATE)
                .getBoolean("isSeller", false);

        if (isSeller) {
            itemDangKyBanHang.setVisibility(View.GONE);
            itemQuanLyCuaHang.setVisibility(View.VISIBLE);
        } else {
            itemDangKyBanHang.setVisibility(View.VISIBLE);
            itemQuanLyCuaHang.setVisibility(View.GONE);
        }

        btnBack.setOnClickListener(v ->
                requireActivity().getSupportFragmentManager().popBackStack()
        );

        layoutUser.setOnClickListener(v ->
                Toast.makeText(getContext(), "Mở hồ sơ cá nhân", Toast.LENGTH_SHORT).show());

        itemThongTinCaNhan.setOnClickListener(v ->
                Toast.makeText(getContext(), "Thông tin cá nhân", Toast.LENGTH_SHORT).show());

        itemDoiMatKhau.setOnClickListener(v ->
                Toast.makeText(getContext(), "Đổi mật khẩu", Toast.LENGTH_SHORT).show());

        itemDangKyBanHang.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameLayout, new DangKiBanHangFragment())
                    .addToBackStack(null)
                    .commit();
        });

        itemQuanLyCuaHang.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameLayout, new QuanLyCuaHangFragment())
                    .addToBackStack(null)
                    .commit();
        });

        itemVoucher.setOnClickListener(v ->
                Toast.makeText(getContext(), "Voucher của tôi", Toast.LENGTH_SHORT).show());

        itemGioiThieu.setOnClickListener(v ->
                Toast.makeText(getContext(), "Giới thiệu", Toast.LENGTH_SHORT).show());

        itemDieuKhoan.setOnClickListener(v ->
                Toast.makeText(getContext(), "Điều khoản", Toast.LENGTH_SHORT).show());

        itemTroGiup.setOnClickListener(v ->
                Toast.makeText(getContext(), "Trợ giúp", Toast.LENGTH_SHORT).show());

        return view;
    }
}