package com.example.giaodichnongsan;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AdminFragment extends Fragment {

    private ImageView btnBack, btnMenu;
    private LinearLayout layoutQuanLyNguoiDung, layoutDuyetTaiKhoan, layoutQuanLyCuaHang, layoutBaoCaoViPham;
    private Button btnXemInfoA, btnXemInfoB, btnXemInfoC;
    private Button btnXemShop1, btnLoaiBoShop1, btnXemShop2, btnLoaiBoShop2;
    private TextView tvXemTatCaTaiKhoan;

    public AdminFragment() {
    }

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
    }

    private void suKien() {

        btnBack.setOnClickListener(v -> requireActivity().onBackPressed());

        btnMenu.setOnClickListener(v -> {
            String[] chucNang = {
                    "Quản lý người dùng",
                    "Quản lý cửa hàng",
                    "Quản lý duyệt cửa hàng",
                    "Quản lý báo cáo"
            };

            new AlertDialog.Builder(requireContext())
                    .setTitle("Chọn nhanh")
                    .setItems(chucNang, (dialog, which) -> {
                        switch (which) {
                            case 0:
                                getParentFragmentManager().beginTransaction()
                                        .replace(R.id.frameLayout, new QuanLyNguoiDungFragment())
                                        .addToBackStack(null)
                                        .commit();
                                break;

                            case 1:
                                getParentFragmentManager().beginTransaction()
                                        .replace(R.id.frameLayout, new QuanLyCuaHangFragment())
                                        .addToBackStack(null)
                                        .commit();
                                break;

                            case 2:
                                getParentFragmentManager().beginTransaction()
                                        .replace(R.id.frameLayout, new QuanLyDuyetTaiKhoanFragment())
                                        .addToBackStack(null)
                                        .commit();
                                break;

                            case 3:
                                getParentFragmentManager().beginTransaction()
                                        .replace(R.id.frameLayout, new QuanLyBaoCaoFragment())
                                        .addToBackStack(null)
                                        .commit();
                                break;
                        }
                    })
                    .show();
        });

        layoutQuanLyNguoiDung.setOnClickListener(v ->
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout, new QuanLyNguoiDungFragment())
                        .addToBackStack(null)
                        .commit()
        );

        layoutDuyetTaiKhoan.setOnClickListener(v ->
                getParentFragmentManager().beginTransaction()
                            .replace(R.id.frameLayout, new QuanLyDuyetTaiKhoanFragment())
                        .addToBackStack(null)
                        .commit()
        );

        layoutQuanLyCuaHang.setOnClickListener(v ->
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout, new AdQuanLyCuaHangFragment())
                        .addToBackStack(null)
                        .commit()
        );

        layoutBaoCaoViPham.setOnClickListener(v ->
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout, new QuanLyBaoCaoFragment())
                        .addToBackStack(null)
                        .commit()
        );

        btnXemInfoA.setOnClickListener(v -> xemThongTinNguoiDung("Nguyễn Văn A"));
        btnXemInfoB.setOnClickListener(v -> xemThongTinNguoiDung("Trần Thị B"));
        btnXemInfoC.setOnClickListener(v -> xemThongTinNguoiDung("Lê Văn C"));

        tvXemTatCaTaiKhoan.setOnClickListener(v ->
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout, new QuanLyDuyetTaiKhoanFragment())
                        .addToBackStack(null)
                        .commit()
        );

        btnXemShop1.setOnClickListener(v ->
                Toast.makeText(getContext(), "Xem shop Nông sản xanh", Toast.LENGTH_SHORT).show()
        );

        btnXemShop2.setOnClickListener(v ->
                Toast.makeText(getContext(), "Xem shop Nông sản sạch", Toast.LENGTH_SHORT).show()
        );

        btnLoaiBoShop1.setOnClickListener(v -> hienThiDialogLoaiBo("Nông sản xanh"));
        btnLoaiBoShop2.setOnClickListener(v -> hienThiDialogLoaiBo("Nông sản sạch"));
    }

    private void xemThongTinNguoiDung(String ten) {
        Toast.makeText(getContext(), "Xem info: " + ten, Toast.LENGTH_SHORT).show();
    }

    private void hienThiDialogLoaiBo(String tenShop) {
        new AlertDialog.Builder(getContext())
                .setTitle("Xác nhận")
                .setMessage("Bạn có chắc muốn loại bỏ shop " + tenShop + " không?")
                .setPositiveButton("Có", (dialog, which) ->
                        Toast.makeText(getContext(), "Đã loại bỏ " + tenShop, Toast.LENGTH_SHORT).show()
                )
                .setNegativeButton("Không", null)
                .show();
    }
}