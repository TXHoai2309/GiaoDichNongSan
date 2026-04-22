package com.example.giaodichnongsan.ui.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.giaodichnongsan.R;
import com.example.giaodichnongsan.ui.activity.MainActivity;

public class TaiKhoanFragment extends Fragment {

    ImageView btnBack;
    LinearLayout itemThongTinCaNhan, itemDoiMatKhau, itemDangKyBanHang,
            itemVoucher, itemGioiThieu, itemDieuKhoan, itemTroGiup, layoutUser,
            itemQuanLyCuaHang;
    Switch switchCamUng, switchDoSang;
    SharedPreferences prefs;

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

        itemThongTinCaNhan.setOnClickListener(v -> {

            AdminFragment fragment = new AdminFragment();

            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameLayout, fragment)
                    .addToBackStack(null)
                    .commit();
        });

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



        itemGioiThieu.setOnClickListener(v ->
                Toast.makeText(getContext(), "Giới thiệu", Toast.LENGTH_SHORT).show());

        itemDieuKhoan.setOnClickListener(v ->
                Toast.makeText(getContext(), "Điều khoản", Toast.LENGTH_SHORT).show());

        itemTroGiup.setOnClickListener(v -> {
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameLayout, new HelpCenterFragment())
                    .addToBackStack(null)
                    .commit();
        });

        // sensor rung lắc
        switchCamUng = view.findViewById(R.id.switchCamUng);

        prefs = requireActivity().getSharedPreferences("settings", MODE_PRIVATE);

        boolean isShakeEnabled = prefs.getBoolean("shake_enabled", true);
        switchCamUng.setChecked(isShakeEnabled);

        switchCamUng.setOnCheckedChangeListener((buttonView, isChecked) -> {
            prefs.edit().putBoolean("shake_enabled", isChecked).apply();

            Toast.makeText(getContext(),
                    isChecked ? "Đã bật rung lắc" : "Đã tắt rung lắc",
                    Toast.LENGTH_SHORT).show();

            // 🔥 cập nhật sensor ngay lập tức
            if (getActivity() instanceof com.example.giaodichnongsan.ui.activity.MainActivity) {
                ((com.example.giaodichnongsan.ui.activity.MainActivity) getActivity()).updateSensorsState();
            }
        });

        switchDoSang = view.findViewById(R.id.switchDoSang);

        boolean isLightEnabled = prefs.getBoolean("light_enabled", true);
        switchDoSang.setChecked(isLightEnabled);

        switchDoSang.setOnCheckedChangeListener((buttonView, isChecked) -> {
            prefs.edit().putBoolean("light_enabled", isChecked).apply();

            Toast.makeText(getContext(),
                    isChecked ? "Đã bật ánh sáng tự động" : "Đã tắt ánh sáng",
                    Toast.LENGTH_SHORT).show();

            ((MainActivity) requireActivity()).updateSensorsState();
        });
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();

        boolean isShakeEnabled = prefs.getBoolean("shake_enabled", true);
        boolean isLightEnabled = prefs.getBoolean("light_enabled", true);

        switchCamUng.setChecked(isShakeEnabled);
        switchDoSang.setChecked(isLightEnabled);
    }
}