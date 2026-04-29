package com.example.giaodichnongsan;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class ADQuanLyBaoCaoFragment extends Fragment {

    private ImageView btnBack, btnMenu;
    private EditText edtSearchReport;
    private Button btnSanPham, btnCuaHang, btnChiTiet1, btnChiTiet2;
    private LinearLayout layoutBaoCaoSanPham, layoutBaoCaoCuaHang;
    private View shop1, shop2;

    public ADQuanLyBaoCaoFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ad_quan_ly_bao_cao_fragment, container, false);

        anhXa(view);
        suKien();
        hienTabSanPham();

        return view;
    }

    private void anhXa(View view) {
        btnBack = view.findViewById(R.id.btnBack);
        btnMenu = view.findViewById(R.id.btnMenu);
        edtSearchReport = view.findViewById(R.id.edtSearchReport);

        btnSanPham = view.findViewById(R.id.btnSanPham);
        btnCuaHang = view.findViewById(R.id.btnCuaHang);

        btnChiTiet1 = view.findViewById(R.id.btnChiTiet1);
        btnChiTiet2 = view.findViewById(R.id.btnChiTiet2);

        layoutBaoCaoSanPham = view.findViewById(R.id.layoutBaoCaoSanPham);
        layoutBaoCaoCuaHang = view.findViewById(R.id.layoutBaoCaoCuaHang);

        shop1 = view.findViewById(R.id.shop1);
        shop2 = view.findViewById(R.id.shop2);
    }

    private void suKien() {
        btnBack.setOnClickListener(v -> requireActivity().onBackPressed());

        btnMenu.setOnClickListener(v ->
                Toast.makeText(getContext(), "Mở menu báo cáo", Toast.LENGTH_SHORT).show()
        );

        btnSanPham.setOnClickListener(v -> hienTabSanPham());

        btnCuaHang.setOnClickListener(v -> hienTabCuaHang());

        btnChiTiet1.setOnClickListener(v ->
                Toast.makeText(getContext(), "Xem chi tiết: Cà chua sạch Đà Lạt", Toast.LENGTH_SHORT).show()
        );

        btnChiTiet2.setOnClickListener(v ->
                Toast.makeText(getContext(), "Xem chi tiết: Cam sành miền Tây", Toast.LENGTH_SHORT).show()
        );

        shop1.setOnClickListener(v ->
                Toast.makeText(getContext(), "Xem báo cáo cửa hàng Hoa quả tươi", Toast.LENGTH_SHORT).show()
        );

        shop2.setOnClickListener(v ->
                Toast.makeText(getContext(), "Xem báo cáo cửa hàng Organic Farm", Toast.LENGTH_SHORT).show()
        );
    }

    private void hienTabSanPham() {
        layoutBaoCaoSanPham.setVisibility(View.VISIBLE);
        layoutBaoCaoCuaHang.setVisibility(View.GONE);

        btnSanPham.setBackgroundTintList(ColorStateList.valueOf(0xFF6AA857));
        btnSanPham.setTextColor(0xFFFFFFFF);

        btnCuaHang.setBackgroundTintList(ColorStateList.valueOf(0xFF7E68C6));
        btnCuaHang.setTextColor(0xFFFFFFFF);
    }

    private void hienTabCuaHang() {
        layoutBaoCaoSanPham.setVisibility(View.GONE);
        layoutBaoCaoCuaHang.setVisibility(View.VISIBLE);

        btnCuaHang.setBackgroundTintList(ColorStateList.valueOf(0xFF6AA857));
        btnCuaHang.setTextColor(0xFFFFFFFF);

        btnSanPham.setBackgroundTintList(ColorStateList.valueOf(0xFF7E68C6));
        btnSanPham.setTextColor(0xFFFFFFFF);
    }
}