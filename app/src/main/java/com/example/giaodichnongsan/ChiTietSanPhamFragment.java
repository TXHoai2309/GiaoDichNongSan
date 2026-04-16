package com.example.giaodichnongsan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class ChiTietSanPhamFragment extends Fragment {

    ImageView imgSP;
    TextView tvTen, tvGia, tvDanhGia, tvMoTa, tvNguonGoc, tvShop;

    public ChiTietSanPhamFragment() {}

    public static ChiTietSanPhamFragment newInstance(SanPham sp) {
        ChiTietSanPhamFragment fragment = new ChiTietSanPhamFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("sanpham", sp);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chitiet_sanpham, container, false);

        // ánh xạ
        imgSP = view.findViewById(R.id.imgSP);
        tvTen = view.findViewById(R.id.tvTen);
        tvGia = view.findViewById(R.id.tvGia);
        tvDanhGia = view.findViewById(R.id.tvDanhGia);
        tvMoTa = view.findViewById(R.id.tvMoTa);
        tvNguonGoc = view.findViewById(R.id.tvNguonGoc);
        tvShop = view.findViewById(R.id.tvShop);

        // nhận dữ liệu
        if (getArguments() != null) {
            SanPham sp = (SanPham) getArguments().getSerializable("sanpham");

            if (sp != null) {
                imgSP.setImageResource(sp.getHinh());
                tvTen.setText(sp.getTen());
                tvGia.setText(sp.getGia() + "đ/kg");
                tvDanhGia.setText("⭐ " + sp.getDanhGia() + " | " + sp.getDaBan() + " đã bán");

                tvMoTa.setText(sp.getMoTa() != null ? sp.getMoTa() : "Chưa có mô tả");
                tvNguonGoc.setText(sp.getNguonGoc() != null ? sp.getNguonGoc() : "Đang cập nhật");
                tvShop.setText(sp.getTenShop() != null ? sp.getTenShop() : "Đang cập nhật");
            }
        }
        SanPham sp = null;

        if (getArguments() != null) {
            sp = (SanPham) getArguments().getSerializable("sanpham");

            if (sp != null) {
                imgSP.setImageResource(sp.getHinh());
                tvTen.setText(sp.getTen());
                tvGia.setText(sp.getGia() + "đ/kg");
                tvDanhGia.setText("⭐ " + sp.getDanhGia() + " | " + sp.getDaBan() + " đã bán");

                tvMoTa.setText(sp.getMoTa() != null ? sp.getMoTa() : "Chưa có mô tả");
                tvNguonGoc.setText(sp.getNguonGoc() != null ? sp.getNguonGoc() : "Đang cập nhật");
                tvShop.setText(sp.getTenShop() != null ? sp.getTenShop() : "Đang cập nhật");
            }
        }

// 🔥 CLICK SHOP
        if (sp != null) {
            SanPham finalSp = sp;

            tvShop.setOnClickListener(v -> {

                Shop shop = new Shop(
                        1,
                        finalSp.getTenShop(),
                        R.drawable.ic_shop,
                        4.8f,
                        256,
                        1200,
                        "2 năm",
                        "Hà Nội",
                        "0987 654 321",
                        "Chuyên cung cấp nông sản sạch"
                );

                ChiTietShopFragment fragment = ChiTietShopFragment.newInstance(shop);

                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameLayout, fragment)
                        .addToBackStack(null)
                        .commit();
            });
        }

        return view;
    }


}