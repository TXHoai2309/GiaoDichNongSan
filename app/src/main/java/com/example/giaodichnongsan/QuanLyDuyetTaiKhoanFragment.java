package com.example.giaodichnongsan;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class QuanLyDuyetTaiKhoanFragment extends Fragment {

    private ImageView btnBack;
    private EditText edtSearchDuyetTaiKhoan;

    private RelativeLayout itemTaiKhoanA, itemTaiKhoanB, itemTaiKhoanC;
    private Button btnXemInfoA, btnXemInfoB, btnXemInfoC;

    public QuanLyDuyetTaiKhoanFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quan_ly_duyet_tai_khoan, container, false);

        anhXa(view);
        suKien();

        return view;
    }

    private void anhXa(View view) {
        btnBack = view.findViewById(R.id.btnBack);
        edtSearchDuyetTaiKhoan = view.findViewById(R.id.edtSearchDuyetTaiKhoan);

        itemTaiKhoanA = view.findViewById(R.id.itemTaiKhoanA);
        itemTaiKhoanB = view.findViewById(R.id.itemTaiKhoanB);
        itemTaiKhoanC = view.findViewById(R.id.itemTaiKhoanC);

        btnXemInfoA = view.findViewById(R.id.btnXemInfoA);
        btnXemInfoB = view.findViewById(R.id.btnXemInfoB);
        btnXemInfoC = view.findViewById(R.id.btnXemInfoC);

    }

    private void suKien() {
        btnBack.setOnClickListener(v -> requireActivity().onBackPressed());

        itemTaiKhoanA.setOnClickListener(v -> moChiTiet("Nguyễn Văn A", "0912 345 567"));
        itemTaiKhoanB.setOnClickListener(v -> moChiTiet("Trần Thị B", "0987 654 567"));
        itemTaiKhoanC.setOnClickListener(v -> moChiTiet("Lê Văn C", "0901 234 678"));

        btnXemInfoA.setOnClickListener(v -> moChiTiet("Nguyễn Văn A", "0912 345 567"));
        btnXemInfoB.setOnClickListener(v -> moChiTiet("Trần Thị B", "0987 654 567"));
        btnXemInfoC.setOnClickListener(v -> moChiTiet("Lê Văn C", "0901 234 678"));

        edtSearchDuyetTaiKhoan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String tuKhoa = s.toString().trim().toLowerCase();

                locItem(itemTaiKhoanA, "nguyễn văn a", "0912 345 567", tuKhoa);
                locItem(itemTaiKhoanB, "trần thị b", "0987 654 567", tuKhoa);
                locItem(itemTaiKhoanC, "lê văn c", "0901 234 678", tuKhoa);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void locItem(View itemView, String ten, String sdt, String tuKhoa) {
        if (tuKhoa.isEmpty() || ten.contains(tuKhoa) || sdt.contains(tuKhoa)) {
            itemView.setVisibility(View.VISIBLE);
        } else {
            itemView.setVisibility(View.GONE);
        }
    }
    private void moChiTiet(String hoTen, String soDienThoai) {
        ChiTietDangKyBanHangFragment fragment =
                ChiTietDangKyBanHangFragment.newInstance(hoTen, soDienThoai);

        getParentFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .addToBackStack(null)
                .commit();
    }
}