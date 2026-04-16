package com.example.giaodichnongsan;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class AdQuanLyCuaHangFragment extends Fragment {

    private ImageView btnBack;
    private EditText edtSearchShop;
    private Button btnLocShop, btnThemShop, btnSuaShop, btnXoaShop;
    private LinearLayout layoutListShop;

    private final List<CuaHang> danhSachCuaHang = new ArrayList<>();
    private final List<CuaHang> danhSachDangHienThi = new ArrayList<>();

    public AdQuanLyCuaHangFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.quan_ly_cua_hang_fragment, container, false);

        anhXa(view);
        taoDuLieuMau();
        hienThiDanhSach(danhSachCuaHang);
        suKien();

        return view;
    }

    private void anhXa(View view) {
        btnBack = view.findViewById(R.id.btnBack);
        edtSearchShop = view.findViewById(R.id.edtSearchShop);
        btnLocShop = view.findViewById(R.id.btnLocShop);
        btnThemShop = view.findViewById(R.id.btnThemShop);
        btnSuaShop = view.findViewById(R.id.btnSuaShop);
        btnXoaShop = view.findViewById(R.id.btnXoaShop);
        layoutListShop = view.findViewById(R.id.layoutListShop);
    }

    private void suKien() {
        btnBack.setOnClickListener(v -> requireActivity().onBackPressed());

        btnLocShop.setOnClickListener(v ->
                Toast.makeText(getContext(), "Chức năng lọc cửa hàng", Toast.LENGTH_SHORT).show()
        );

        btnThemShop.setOnClickListener(v ->
                Toast.makeText(getContext(), "Thêm cửa hàng mới", Toast.LENGTH_SHORT).show()
        );

        btnSuaShop.setOnClickListener(v ->
                Toast.makeText(getContext(), "Chọn cửa hàng để sửa", Toast.LENGTH_SHORT).show()
        );

        btnXoaShop.setOnClickListener(v ->
                Toast.makeText(getContext(), "Chọn cửa hàng để xóa", Toast.LENGTH_SHORT).show()
        );

        edtSearchShop.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                timKiemCuaHang(s.toString().trim());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void taoDuLieuMau() {
        danhSachCuaHang.clear();

        danhSachCuaHang.add(new CuaHang("001", "Cửa hàng A", "Nguyễn Văn A", "Hoạt động"));
        danhSachCuaHang.add(new CuaHang("002", "Cửa hàng B", "Trần Thị B", "Hoạt động"));
        danhSachCuaHang.add(new CuaHang("003", "Nông sản C", "Lê Văn C", "Hoạt động"));
        danhSachCuaHang.add(new CuaHang("004", "Nông trại D", "Nguyễn Văn A", "Đã khóa"));
    }

    private void timKiemCuaHang(String tuKhoa) {
        danhSachDangHienThi.clear();

        if (tuKhoa.isEmpty()) {
            danhSachDangHienThi.addAll(danhSachCuaHang);
        } else {
            for (CuaHang cuaHang : danhSachCuaHang) {
                if (cuaHang.getId().toLowerCase().contains(tuKhoa.toLowerCase())
                        || cuaHang.getTen().toLowerCase().contains(tuKhoa.toLowerCase())
                        || cuaHang.getChuSoHuu().toLowerCase().contains(tuKhoa.toLowerCase())
                        || cuaHang.getTrangThai().toLowerCase().contains(tuKhoa.toLowerCase())) {
                    danhSachDangHienThi.add(cuaHang);
                }
            }
        }

        hienThiDanhSach(danhSachDangHienThi);
    }

    private void hienThiDanhSach(List<CuaHang> ds) {
        layoutListShop.removeAllViews();

        for (CuaHang cuaHang : ds) {
            layoutListShop.addView(taoDongCuaHang(cuaHang));
        }
    }

    private View taoDongCuaHang(CuaHang cuaHang) {
        LinearLayout dong = new LinearLayout(getContext());
        dong.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        dong.setOrientation(LinearLayout.HORIZONTAL);
        dong.setPadding(6, 14, 6, 14);
        dong.setBackgroundColor(0xFFFFFFFF);
        dong.setGravity(Gravity.CENTER_VERTICAL);

        LinearLayout.LayoutParams idParams = new LinearLayout.LayoutParams(dpToPx(40), ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView tvId = new TextView(getContext());
        tvId.setLayoutParams(idParams);
        tvId.setText(cuaHang.getId());
        tvId.setTextSize(12);
        tvId.setTextColor(0xFF2F2F2F);

        LinearLayout.LayoutParams tenParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.2f);
        TextView tvTen = new TextView(getContext());
        tvTen.setLayoutParams(tenParams);
        tvTen.setText(cuaHang.getTen());
        tvTen.setTextSize(12);
        tvTen.setTextColor(0xFF2F2F2F);
        tvTen.setMaxLines(1);

        LinearLayout.LayoutParams chuShopParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
        TextView tvChuSoHuu = new TextView(getContext());
        tvChuSoHuu.setLayoutParams(chuShopParams);
        tvChuSoHuu.setText(cuaHang.getChuSoHuu());
        tvChuSoHuu.setTextSize(12);
        tvChuSoHuu.setTextColor(0xFF555555);
        tvChuSoHuu.setMaxLines(1);

        LinearLayout.LayoutParams trangThaiParams = new LinearLayout.LayoutParams(dpToPx(72), ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView tvTrangThai = new TextView(getContext());
        tvTrangThai.setLayoutParams(trangThaiParams);
        tvTrangThai.setText(cuaHang.getTrangThai());
        tvTrangThai.setTextSize(12);
        tvTrangThai.setGravity(Gravity.CENTER);
        tvTrangThai.setPadding(8, 6, 8, 6);

        if ("Hoạt động".equals(cuaHang.getTrangThai())) {
            tvTrangThai.setBackgroundColor(0xFFE9F4E4);
            tvTrangThai.setTextColor(0xFF4E8A3F);
        } else {
            tvTrangThai.setBackgroundColor(0xFFF8E3DE);
            tvTrangThai.setTextColor(0xFFC05A4A);
        }

        dong.addView(tvId);
        dong.addView(tvTen);
        dong.addView(tvChuSoHuu);
        dong.addView(tvTrangThai);

        dong.setOnClickListener(v ->
                Toast.makeText(getContext(), "Đã chọn: " + cuaHang.getTen(), Toast.LENGTH_SHORT).show()
        );

        dong.setOnLongClickListener(v -> {
            hienThiHopThoaiCuaHang(cuaHang);
            return true;
        });

        return dong;
    }

    private void hienThiHopThoaiCuaHang(CuaHang cuaHang) {
        String[] luaChon = {"Xem thông tin", "Sửa", "Xóa"};

        new AlertDialog.Builder(getContext())
                .setTitle(cuaHang.getTen())
                .setItems(luaChon, (dialog, which) -> {
                    if (which == 0) {
                        Toast.makeText(getContext(),
                                "Chủ shop: " + cuaHang.getChuSoHuu(),
                                Toast.LENGTH_SHORT).show();
                    } else if (which == 1) {
                        Toast.makeText(getContext(),
                                "Sửa cửa hàng: " + cuaHang.getTen(),
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(),
                                "Xóa cửa hàng: " + cuaHang.getTen(),
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }

    private int dpToPx(int dp) {
        float density = requireContext().getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    public static class CuaHang {
        private final String id;
        private final String ten;
        private final String chuSoHuu;
        private final String trangThai;

        public CuaHang(String id, String ten, String chuSoHuu, String trangThai) {
            this.id = id;
            this.ten = ten;
            this.chuSoHuu = chuSoHuu;
            this.trangThai = trangThai;
        }

        public String getId() {
            return id;
        }

        public String getTen() {
            return ten;
        }

        public String getChuSoHuu() {
            return chuSoHuu;
        }

        public String getTrangThai() {
            return trangThai;
        }
    }
}