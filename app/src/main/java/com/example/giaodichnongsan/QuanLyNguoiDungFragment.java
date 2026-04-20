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
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class QuanLyNguoiDungFragment extends Fragment {

    private ImageView btnBack;
    private EditText edtSearch;
    private Button btnLoc, btnSua, btnXoa;
    private LinearLayout layoutListUser;

    private final List<NguoiDung> danhSachNguoiDung = new ArrayList<>();
    private final List<NguoiDung> danhSachDangHienThi = new ArrayList<>();

    public QuanLyNguoiDungFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.quan_ly_nguoi_dung_fragment, container, false);

        anhXa(view);
        taoDuLieuMau();
        hienThiDanhSach(danhSachNguoiDung);
        suKien();

        return view;
    }

    private void anhXa(View view) {
        btnBack = view.findViewById(R.id.btnBack);
        edtSearch = view.findViewById(R.id.edtSearch);
        btnLoc = view.findViewById(R.id.btnLoc);
        btnSua = view.findViewById(R.id.btnSua);
        btnXoa = view.findViewById(R.id.btnXoa);
        layoutListUser = view.findViewById(R.id.layoutListUser);
    }

    private void suKien() {
        btnBack.setOnClickListener(v -> requireActivity().onBackPressed());

        btnLoc.setOnClickListener(v ->
                Toast.makeText(getContext(), "Chức năng lọc đang được xử lý", Toast.LENGTH_SHORT).show()
        );

        btnSua.setOnClickListener(v ->
                Toast.makeText(getContext(), "Chọn người dùng để sửa", Toast.LENGTH_SHORT).show()
        );

        btnXoa.setOnClickListener(v ->
                Toast.makeText(getContext(), "Chọn người dùng để xóa", Toast.LENGTH_SHORT).show()
        );

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                timKiemNguoiDung(s.toString().trim());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void taoDuLieuMau() {
        danhSachNguoiDung.clear();

        danhSachNguoiDung.add(new NguoiDung("001", "Nguyễn Văn A", "nguyenvana@example.com", "Hoạt động"));
        danhSachNguoiDung.add(new NguoiDung("002", "Trần Thị B", "tranthib@example.com", "Hoạt động"));
        danhSachNguoiDung.add(new NguoiDung("003", "Lê Văn C", "levanc@example.com", "Hoạt động"));
        danhSachNguoiDung.add(new NguoiDung("004", "Phạm Thị D", "phamthid@example.com", "Đã khóa"));
        danhSachNguoiDung.add(new NguoiDung("005", "Cải ngọt", "caingot@example.com", "Hoạt động"));
        danhSachNguoiDung.add(new NguoiDung("006", "Phạm Thị E", "phamthie@example.com", "Đã khóa"));
    }

    private void timKiemNguoiDung(String tuKhoa) {
        danhSachDangHienThi.clear();

        if (tuKhoa.isEmpty()) {
            danhSachDangHienThi.addAll(danhSachNguoiDung);
        } else {
            for (NguoiDung nguoiDung : danhSachNguoiDung) {
                if (nguoiDung.getId().toLowerCase().contains(tuKhoa.toLowerCase())
                        || nguoiDung.getTen().toLowerCase().contains(tuKhoa.toLowerCase())
                        || nguoiDung.getEmail().toLowerCase().contains(tuKhoa.toLowerCase())
                        || nguoiDung.getTrangThai().toLowerCase().contains(tuKhoa.toLowerCase())) {
                    danhSachDangHienThi.add(nguoiDung);
                }
            }
        }

        hienThiDanhSach(danhSachDangHienThi);
    }

    private void hienThiDanhSach(List<NguoiDung> ds) {
        layoutListUser.removeAllViews();

        for (NguoiDung nguoiDung : ds) {
            layoutListUser.addView(taoDongNguoiDung(nguoiDung));
        }
    }

    private View taoDongNguoiDung(NguoiDung nguoiDung) {
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
        tvId.setText(nguoiDung.getId());
        tvId.setTextSize(12);
        tvId.setTextColor(0xFF2F2F2F);

        LinearLayout.LayoutParams tenParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        TextView tvTen = new TextView(getContext());
        tvTen.setLayoutParams(tenParams);
        tvTen.setText(nguoiDung.getTen());
        tvTen.setTextSize(12);
        tvTen.setTextColor(0xFF2F2F2F);
        tvTen.setMaxLines(1);

        LinearLayout.LayoutParams emailParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        TextView tvEmail = new TextView(getContext());
        tvEmail.setLayoutParams(emailParams);
        tvEmail.setText(nguoiDung.getEmail());
        tvEmail.setTextSize(12);
        tvEmail.setTextColor(0xFF555555);
        tvEmail.setMaxLines(1);

        LinearLayout.LayoutParams trangThaiParams = new LinearLayout.LayoutParams(dpToPx(70), ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView tvTrangThai = new TextView(getContext());
        tvTrangThai.setLayoutParams(trangThaiParams);
        tvTrangThai.setText(nguoiDung.getTrangThai());
        tvTrangThai.setTextSize(12);
        tvTrangThai.setGravity(Gravity.CENTER);
        tvTrangThai.setPadding(8, 6, 8, 6);

        if ("Hoạt động".equals(nguoiDung.getTrangThai())) {
            tvTrangThai.setBackgroundColor(0xFFE9F4E4);
            tvTrangThai.setTextColor(0xFF4E8A3F);
        } else {
            tvTrangThai.setBackgroundColor(0xFFF8E3DE);
            tvTrangThai.setTextColor(0xFFC05A4A);
        }

        dong.addView(tvId);
        dong.addView(tvTen);
        dong.addView(tvEmail);
        dong.addView(tvTrangThai);

        dong.setOnClickListener(v ->
                Toast.makeText(getContext(), "Đã chọn: " + nguoiDung.getTen(), Toast.LENGTH_SHORT).show()
        );

        dong.setOnLongClickListener(v -> {
            hienThiHopThoaiNguoiDung(nguoiDung);
            return true;
        });

        return dong;
    }

    private void hienThiHopThoaiNguoiDung(NguoiDung nguoiDung) {
        String[] luaChon = {"Xem thông tin", "Sửa", "Xóa"};

        new AlertDialog.Builder(getContext())
                .setTitle(nguoiDung.getTen())
                .setItems(luaChon, (dialog, which) -> {
                    if (which == 0) {
                        Toast.makeText(getContext(),
                                "Email: " + nguoiDung.getEmail(),
                                Toast.LENGTH_SHORT).show();
                    } else if (which == 1) {
                        Toast.makeText(getContext(),
                                "Sửa người dùng: " + nguoiDung.getTen(),
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(),
                                "Xóa người dùng: " + nguoiDung.getTen(),
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }

    private int dpToPx(int dp) {
        float density = requireContext().getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    public static class NguoiDung {
        private final String id;
        private final String ten;
        private final String email;
        private final String trangThai;

        public NguoiDung(String id, String ten, String email, String trangThai) {
            this.id = id;
            this.ten = ten;
            this.email = email;
            this.trangThai = trangThai;
        }

        public String getId() {
            return id;
        }

        public String getTen() {
            return ten;
        }

        public String getEmail() {
            return email;
        }

        public String getTrangThai() {
            return trangThai;
        }
    }
}