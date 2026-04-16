package com.example.giaodichnongsan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class QuanLyCuaHangFragment extends Fragment {

    ImageView btnBack, btnMenu;

    LinearLayout itemTenGianHang, itemQuanLyDonHang1, itemQuanLyKhuyenMai;
    LinearLayout itemThongTinSanPham, itemQuanLyDonHang2, itemXuLyYeuCau;
    LinearLayout itemDanhGiaPhanHoi, itemThongKeDoanhThu;
    LinearLayout itemCaiDatShop, itemTrungTamTroGiup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_quan_ly_cua_hang, container, false);

        btnBack = view.findViewById(R.id.btnBackQuanLyShop);
        btnMenu = view.findViewById(R.id.btnMenuQuanLyShop);

        itemTenGianHang = view.findViewById(R.id.itemTenGianHang);
        itemQuanLyDonHang1 = view.findViewById(R.id.itemQuanLyDonHang1);
        itemQuanLyKhuyenMai = view.findViewById(R.id.itemQuanLyKhuyenMai);

        itemThongTinSanPham = view.findViewById(R.id.itemThongTinSanPham);
        itemQuanLyDonHang2 = view.findViewById(R.id.itemQuanLyDonHang2);
        itemXuLyYeuCau = view.findViewById(R.id.itemXuLyYeuCau);
        itemDanhGiaPhanHoi = view.findViewById(R.id.itemDanhGiaPhanHoi);
        itemThongKeDoanhThu = view.findViewById(R.id.itemThongKeDoanhThu);

        itemCaiDatShop = view.findViewById(R.id.itemCaiDatShop);
        itemTrungTamTroGiup = view.findViewById(R.id.itemTrungTamTroGiup);

        btnBack.setOnClickListener(v ->
                requireActivity().getSupportFragmentManager().popBackStack()
        );

        btnMenu.setOnClickListener(v ->
                Toast.makeText(getContext(), "Menu", Toast.LENGTH_SHORT).show()
        );

        itemTenGianHang.setOnClickListener(v ->
                Toast.makeText(getContext(), "Tên gian hàng", Toast.LENGTH_SHORT).show()
        );

        itemQuanLyDonHang1.setOnClickListener(v ->
                Toast.makeText(getContext(), "Quản lý đơn hàng", Toast.LENGTH_SHORT).show()
        );

        itemQuanLyKhuyenMai.setOnClickListener(v ->
                Toast.makeText(getContext(), "Quản lý khuyến mãi", Toast.LENGTH_SHORT).show()
        );

        itemThongTinSanPham.setOnClickListener(v ->
                Toast.makeText(getContext(), "Thông tin gian hàng", Toast.LENGTH_SHORT).show()
        );

        itemQuanLyDonHang2.setOnClickListener(v ->
                Toast.makeText(getContext(), "Quản lý đơn hàng", Toast.LENGTH_SHORT).show()
        );

        itemXuLyYeuCau.setOnClickListener(v ->
                Toast.makeText(getContext(), "Xử lý yêu cầu đăng ký", Toast.LENGTH_SHORT).show()
        );

        itemDanhGiaPhanHoi.setOnClickListener(v ->
                Toast.makeText(getContext(), "Đánh giá & phản hồi", Toast.LENGTH_SHORT).show()
        );

        itemThongKeDoanhThu.setOnClickListener(v ->
                Toast.makeText(getContext(), "Thống kê & doanh thu", Toast.LENGTH_SHORT).show()
        );

        itemCaiDatShop.setOnClickListener(v ->
                Toast.makeText(getContext(), "Cài đặt shop", Toast.LENGTH_SHORT).show()
        );

        itemTrungTamTroGiup.setOnClickListener(v ->
                Toast.makeText(getContext(), "Trung tâm trợ giúp", Toast.LENGTH_SHORT).show()
        );

        return view;
    }
}