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
import com.example.giaodichnongsan.ui.activity.DoiMatKhauActivity;
import com.example.giaodichnongsan.ui.activity.MainActivity;
import com.example.giaodichnongsan.utils.AuthHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class TaiKhoanFragment extends Fragment {

    ImageView btnBack;
    LinearLayout itemDoiMatKhau, itemDangKyBanHang,
            itemGioiThieu, itemDieuKhoan, itemTroGiup, layoutUser,
            itemQuanLyCuaHang;

    TextView tvUserName, tvPhone, tvDangNhapTaiKhoan, tvDangKyTaiKhoan;

    Switch switchCamUng, switchDoSang;
    SharedPreferences prefs;

    Button btnLogoutUser;

    public TaiKhoanFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_taikhoan, container, false);

        // ===== ÁNH XẠ =====
        btnBack = view.findViewById(R.id.btnBack);
        layoutUser = view.findViewById(R.id.layoutUser);

        tvUserName = view.findViewById(R.id.tvUserName);
        tvPhone = view.findViewById(R.id.tvPhone);
        tvDangNhapTaiKhoan = view.findViewById(R.id.tvDangNhapTaiKhoan);
        tvDangKyTaiKhoan = view.findViewById(R.id.tvDangKyTaiKhoan);

        btnLogoutUser = view.findViewById(R.id.btnLogoutUser);

        itemDoiMatKhau = view.findViewById(R.id.itemDoiMatKhau);
        itemDangKyBanHang = view.findViewById(R.id.itemDangKyBanHang);
        itemGioiThieu = view.findViewById(R.id.itemGioiThieu);
        itemDieuKhoan = view.findViewById(R.id.itemDieuKhoan);
        itemTroGiup = view.findViewById(R.id.itemTroGiup);
        itemQuanLyCuaHang = view.findViewById(R.id.itemQuanLyCuaHang);
        switchCamUng = view.findViewById(R.id.switchCamUng);
        switchDoSang = view.findViewById(R.id.switchDoSang);

        prefs = requireActivity().getSharedPreferences("settings", MODE_PRIVATE);

        capNhatTrangThaiDangNhap();
        capNhatTrangThaiSeller();
        capNhatTrangThaiSwitch();

        // ===== BACK =====
        btnBack.setOnClickListener(v ->
                requireActivity().getSupportFragmentManager().popBackStack()
        );

        // ===== LOGIN / REGISTER =====
        tvDangNhapTaiKhoan.setOnClickListener(v ->
                startActivity(new Intent(requireContext(), DangNhap.class))
        );

        tvDangKyTaiKhoan.setOnClickListener(v ->
                startActivity(new Intent(requireContext(), DangKy.class))
        );

        // ===== CLICK CÁC ITEM (🔥 FIX LỖI CHÍNH) =====

        layoutUser.setOnClickListener(v -> {
            AuthHelper.requireLogin(getContext(), () -> {
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameLayout, new ThongTinCaNhanFragment())
                        .addToBackStack(null)
                        .commit();
            });
        });

        itemDoiMatKhau.setOnClickListener(v ->
                AuthHelper.requireLogin(getContext(), () ->
                        startActivity(new Intent(requireContext(), DoiMatKhauActivity.class))
                )
        );

        itemDangKyBanHang.setOnClickListener(v -> {
            AuthHelper.requireLogin(getContext(), () -> {
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameLayout, new DangKiBanHangFragment())
                        .addToBackStack(null)
                        .commit();
            });
        });

        itemQuanLyCuaHang.setOnClickListener(v -> {
            AuthHelper.requireLogin(getContext(), () -> {
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameLayout, new QuanLyCuaHangFragment())
                        .addToBackStack(null)
                        .commit();
            });
        });

        itemGioiThieu.setOnClickListener(v ->
                Toast.makeText(getContext(), "Ứng dụng giao dịch nông sản", Toast.LENGTH_SHORT).show()
        );

        itemDieuKhoan.setOnClickListener(v ->
                Toast.makeText(getContext(), "Điều khoản & chính sách", Toast.LENGTH_SHORT).show()
        );

        itemTroGiup.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameLayout, new HelpCenterFragment())
                    .addToBackStack(null)
                    .commit();
        });

        switchCamUng.setOnCheckedChangeListener((buttonView, isChecked) -> {
            prefs.edit().putBoolean("shake_enabled", !isChecked).apply();
            capNhatCamBien();
        });

        switchDoSang.setOnCheckedChangeListener((buttonView, isChecked) -> {
            prefs.edit().putBoolean("light_enabled", !isChecked).apply();
            capNhatCamBien();
        });

        // ===== LOGOUT =====
        btnLogoutUser.setOnClickListener(v -> {

            SharedPreferences prefs = requireActivity()
                    .getSharedPreferences("USER", MODE_PRIVATE);

            prefs.edit().clear().apply();

            Toast.makeText(getContext(), "Đã đăng xuất", Toast.LENGTH_SHORT).show();

            // 👉 KHÔNG reload fragment kiểu cũ (bug)
            startActivity(new Intent(getContext(), DangNhap.class));
            requireActivity().finish();
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
            loadTenNguoiDung();

            tvDangNhapTaiKhoan.setVisibility(View.GONE);
            tvDangKyTaiKhoan.setVisibility(View.GONE);

            btnLogoutUser.setVisibility(View.VISIBLE);
        } else {
            tvUserName.setText("Khách");
            tvPhone.setText("Bạn chưa đăng nhập");

            tvDangNhapTaiKhoan.setVisibility(View.VISIBLE);
            tvDangKyTaiKhoan.setVisibility(View.VISIBLE);

            btnLogoutUser.setVisibility(View.GONE);
        }
    }

    private void loadTenNguoiDung() {
        if (requireActivity()
                .getSharedPreferences("USER", MODE_PRIVATE)
                .getBoolean("isAdmin", false)) {
            tvUserName.setText("Admin");
            return;
        }

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser == null) return;

        FirebaseFirestore.getInstance()
                .collection("users")
                .document(firebaseUser.getUid())
                .get()
                .addOnSuccessListener(snapshot -> {
                    if (!isAdded()) return;

                    String hoTen = snapshot.getString("hoTen");
                    if (hoTen != null && !hoTen.trim().isEmpty()) {
                        tvUserName.setText(hoTen.trim());
                        return;
                    }

                    String email = firebaseUser.getEmail();
                    if (email != null && !email.trim().isEmpty()) {
                        tvUserName.setText(email.split("@")[0]);
                    }
                });
    }

    private void capNhatTrangThaiSeller() {
        boolean isSeller = requireActivity()
                .getSharedPreferences("USER", MODE_PRIVATE)
                .getBoolean("isSeller", false);

        hienThiTrangThaiSeller(isSeller);
        loadTrangThaiSellerTuFirestore();
    }

    private void loadTrangThaiSellerTuFirestore() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser == null) {
            hienThiTrangThaiSeller(false);
            return;
        }

        FirebaseFirestore.getInstance()
                .collection("users")
                .document(firebaseUser.getUid())
                .get()
                .addOnSuccessListener(snapshot -> {
                    if (!isAdded()) return;

                    Boolean isSellerValue = snapshot.getBoolean("isSeller");
                    Boolean sellerValue = snapshot.getBoolean("seller");
                    boolean isSeller = Boolean.TRUE.equals(isSellerValue) || Boolean.TRUE.equals(sellerValue);

                    requireActivity()
                            .getSharedPreferences("USER", MODE_PRIVATE)
                            .edit()
                            .putBoolean("isSeller", isSeller)
                            .apply();

                    hienThiTrangThaiSeller(isSeller);
                })
                .addOnFailureListener(e -> {
                    if (!isAdded()) return;
                    hienThiTrangThaiSeller(false);
                });
    }

    private void hienThiTrangThaiSeller(boolean isSeller) {
        if (isSeller) {
            itemDangKyBanHang.setVisibility(View.GONE);
            itemQuanLyCuaHang.setVisibility(View.VISIBLE);
        } else {
            itemDangKyBanHang.setVisibility(View.VISIBLE);
            itemQuanLyCuaHang.setVisibility(View.GONE);
        }
    }

    private void capNhatTrangThaiSwitch() {
        boolean shakeEnabled = prefs.getBoolean("shake_enabled", true);
        boolean lightEnabled = prefs.getBoolean("light_enabled", true);

        switchCamUng.setChecked(!shakeEnabled);
        switchDoSang.setChecked(!lightEnabled);
    }

    private void capNhatCamBien() {
        if (requireActivity() instanceof MainActivity) {
            ((MainActivity) requireActivity()).updateSensorsState();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        capNhatTrangThaiDangNhap();
        capNhatTrangThaiSeller();
    }
}
