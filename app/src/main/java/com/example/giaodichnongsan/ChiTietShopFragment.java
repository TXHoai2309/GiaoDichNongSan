package com.example.giaodichnongsan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class ChiTietShopFragment extends Fragment {

    ImageView imgShop;
    TextView tvTenShop, tvDanhGia, tvSanPham, tvFollower, tvThamGia, tvDiaChi, tvMoTa, tvPhone;
    Button btnFollow, btnChat;

    public static ChiTietShopFragment newInstance(Shop shop) {
        ChiTietShopFragment fragment = new ChiTietShopFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("shop", shop);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chi_tiet_shop, container, false);

        // ánh xạ
        imgShop = view.findViewById(R.id.imgShop);
        tvTenShop = view.findViewById(R.id.tvTenShop);
        tvDanhGia = view.findViewById(R.id.tvDanhGia);
        tvSanPham = view.findViewById(R.id.tvSanPham);
        tvFollower = view.findViewById(R.id.tvFollower);
        tvThamGia = view.findViewById(R.id.tvThamGia);
        tvDiaChi = view.findViewById(R.id.tvDiaChi);
        tvMoTa = view.findViewById(R.id.tvMoTa);
        tvPhone = view.findViewById(R.id.tvPhone);

        btnFollow = view.findViewById(R.id.btnFollow);
        btnChat = view.findViewById(R.id.btnChat);

        // nhận dữ liệu
        if (getArguments() != null) {
            Shop shop = (Shop) getArguments().getSerializable("shop");

            if (shop != null) {
                imgShop.setImageResource(shop.getAvatar());
                tvTenShop.setText(shop.getTenShop());
                tvDanhGia.setText("⭐ " + shop.getDanhGia());
                tvSanPham.setText(shop.getSoSanPham() + "\nSản phẩm");
                tvFollower.setText(shop.getNguoiTheoDoi() + "\nTheo dõi");
                tvThamGia.setText(shop.getThoiGianThamGia() + "\nTham gia");
                tvDiaChi.setText("📍 " + shop.getDiaChi());
                tvMoTa.setText(shop.getMoTa());
                tvPhone.setText("📞 " + shop.getSoDienThoai());
            }
        }

        // ===== BUTTON =====

        btnFollow.setOnClickListener(v -> {
            btnFollow.setText("Đã theo dõi");
        });

        btnChat.setOnClickListener(v -> {
            // demo thôi
            tvMoTa.setText("👉 Chat với shop (demo)");
        });

        return view;
    }
}