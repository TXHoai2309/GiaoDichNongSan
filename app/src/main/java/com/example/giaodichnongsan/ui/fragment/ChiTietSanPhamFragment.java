package com.example.giaodichnongsan.ui.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.giaodichnongsan.R;
import com.example.giaodichnongsan.model.GioHangItem;
import com.example.giaodichnongsan.model.SanPham;
import com.example.giaodichnongsan.model.Shop;
import com.example.giaodichnongsan.viewmodel.GioHangViewModel;

import java.util.ArrayList;

public class ChiTietSanPhamFragment extends Fragment {

    private ImageView imgSP;
    private TextView tvTen, tvGia, tvDanhGia, tvMoTa, tvNguonGoc, tvShop;
    private Button btnGioHang, btnMua;

    private SanPham sp;
    private GioHangViewModel viewModel;

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

        initView(view);
        initData();
        initViewModel();

        if (sp != null) {
            bindData();
            setupEvents();
        }

        return view;
    }

    // ===== INIT =====

    private void initView(View view) {
        imgSP = view.findViewById(R.id.imgSP);
        tvTen = view.findViewById(R.id.tvTen);
        tvGia = view.findViewById(R.id.tvGia);
        tvDanhGia = view.findViewById(R.id.tvDanhGia);
        tvMoTa = view.findViewById(R.id.tvMoTa);
        tvNguonGoc = view.findViewById(R.id.tvNguonGoc);
        tvShop = view.findViewById(R.id.tvShop);
        btnGioHang = view.findViewById(R.id.btnGioHang);
        btnMua = view.findViewById(R.id.btnMua);
    }

    private void initData() {
        if (getArguments() != null) {
            sp = (SanPham) getArguments().getSerializable("sanpham");
        }
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(requireActivity()).get(GioHangViewModel.class);
    }

    // ===== HIỂN THỊ =====

    private void bindData() {
        imgSP.setImageResource(sp.getHinh());
        tvTen.setText(sp.getTen());
        tvGia.setText(sp.getGia() + "đ/kg");
        tvDanhGia.setText("⭐ " + sp.getDanhGia() + " | " + sp.getDaBan() + " đã bán");

        tvMoTa.setText(sp.getMoTa() != null ? sp.getMoTa() : "Chưa có mô tả");
        tvNguonGoc.setText(sp.getNguonGoc() != null ? sp.getNguonGoc() : "Đang cập nhật");
        tvShop.setText(sp.getTenShop() != null ? sp.getTenShop() : "Đang cập nhật");
    }

    // ===== EVENTS =====

    private void setupEvents() {

        tvShop.setOnClickListener(v -> openShop());

        btnGioHang.setOnClickListener(v -> showQuantityDialog(false));

        btnMua.setOnClickListener(v -> showQuantityDialog(true));
    }

    // ===== SHOP =====

    private void openShop() {
        Shop shop = new Shop(
                1,
                sp.getTenShop(),
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
    }

    // ===== DIALOG CHUNG =====

    private void showQuantityDialog(boolean isBuyNow) {

        View dialogView = LayoutInflater.from(getContext())
                .inflate(R.layout.dialog_soluong, null);

        ImageView img = dialogView.findViewById(R.id.imgSP);
        TextView tvTenSP = dialogView.findViewById(R.id.tvTen);
        TextView tvGiaSP = dialogView.findViewById(R.id.tvGia);
        TextView tvTong = dialogView.findViewById(R.id.tvTongGia);

        TextView tvSoLuong = dialogView.findViewById(R.id.tvSoLuong);
        Button btnPlus = dialogView.findViewById(R.id.btnPlus);
        Button btnMinus = dialogView.findViewById(R.id.btnMinus);

        img.setImageResource(sp.getHinh());
        tvTenSP.setText(sp.getTen());
        tvGiaSP.setText(sp.getGia() + "đ");

        final int[] soLuong = {1};
        int gia = sp.getGia();

        tvTong.setText("Tổng: " + gia + "đ");

        btnPlus.setOnClickListener(v -> {
            soLuong[0]++;
            tvSoLuong.setText(String.valueOf(soLuong[0]));
            tvTong.setText("Tổng: " + (gia * soLuong[0]) + "đ");
        });

        btnMinus.setOnClickListener(v -> {
            if (soLuong[0] > 1) {
                soLuong[0]--;
                tvSoLuong.setText(String.valueOf(soLuong[0]));
                tvTong.setText("Tổng: " + (gia * soLuong[0]) + "đ");
            }
        });

        new AlertDialog.Builder(getContext())
                .setView(dialogView)
                .setPositiveButton(isBuyNow ? "Mua ngay" : "Thêm vào giỏ", (dialog, which) -> {

                    if (isBuyNow) {
                        handleBuyNow(soLuong[0]);
                    } else {
                        handleAddToCart(soLuong[0]);
                    }

                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    // ===== ACTION =====

    private void handleAddToCart(int soLuong) {
        for (int i = 0; i < soLuong; i++) {
            viewModel.addToCart(sp);
        }

        Toast.makeText(getContext(),
                "Đã thêm " + soLuong + " sản phẩm",
                Toast.LENGTH_SHORT).show();
    }

    private void handleBuyNow(int soLuong) {

        ArrayList<GioHangItem> listMua = new ArrayList<>();
        listMua.add(new GioHangItem(sp, soLuong));

        ThanhToanFragment fragment = ThanhToanFragment.newInstance(listMua);

        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .addToBackStack(null)
                .commit();
    }
}