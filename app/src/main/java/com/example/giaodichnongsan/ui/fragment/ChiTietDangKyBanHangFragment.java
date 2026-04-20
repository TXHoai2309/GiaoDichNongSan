package com.example.giaodichnongsan.ui.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.giaodichnongsan.R;

public class ChiTietDangKyBanHangFragment extends Fragment {

    private static final String KEY_HO_TEN = "ho_ten";
    private static final String KEY_SO_DIEN_THOAI = "so_dien_thoai";

    private String hoTen;
    private String soDienThoai;

    private ImageView btnBack, btnMenu;
    private TextView tvHoTen, tvSoDienThoai;

    public ChiTietDangKyBanHangFragment() {
    }

    public static ChiTietDangKyBanHangFragment newInstance(String hoTen, String soDienThoai) {
        ChiTietDangKyBanHangFragment fragment = new ChiTietDangKyBanHangFragment();
        Bundle args = new Bundle();
        args.putString(KEY_HO_TEN, hoTen);
        args.putString(KEY_SO_DIEN_THOAI, soDienThoai);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            hoTen = getArguments().getString(KEY_HO_TEN, "");
            soDienThoai = getArguments().getString(KEY_SO_DIEN_THOAI, "");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chi_tiet_quan_ly_dky_ban_hang, container, false);

        anhXa(view);
        hienThiDuLieu();
        suKien();

        return view;
    }

    private void anhXa(View view) {
        btnBack = view.findViewById(R.id.btnBack);
        btnMenu = view.findViewById(R.id.btnMenu);

        tvHoTen = view.findViewById(R.id.tvHoTen);
        tvSoDienThoai = view.findViewById(R.id.tvSoDienThoai);
    }

    private void hienThiDuLieu() {
        tvHoTen.setText("Họ và tên: " + hoTen);
        tvSoDienThoai.setText("Số điện thoại: " + soDienThoai);
    }

    private void suKien() {
        btnBack.setOnClickListener(v -> requireActivity().onBackPressed());

        btnMenu.setOnClickListener(v -> {
            String[] chucNang = {
                    "Phê duyệt tài khoản",
                    "Từ chối tài khoản",
                    "Quay lại danh sách"
            };

            new AlertDialog.Builder(requireContext())
                    .setTitle("Chọn thao tác")
                    .setItems(chucNang, (dialog, which) -> {
                        switch (which) {
                            case 0:
                                Toast.makeText(getContext(),
                                        "Đã phê duyệt: " + hoTen,
                                        Toast.LENGTH_SHORT).show();
                                break;

                            case 1:
                                Toast.makeText(getContext(),
                                        "Đã từ chối: " + hoTen,
                                        Toast.LENGTH_SHORT).show();
                                break;

                            case 2:
                                requireActivity().onBackPressed();
                                break;
                        }
                    })
                    .show();
        });
    }
}