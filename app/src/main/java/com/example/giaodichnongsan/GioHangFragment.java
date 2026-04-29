package com.example.giaodichnongsan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GioHangFragment extends Fragment {

    RecyclerView rvGioHang;
    TextView tvTongTien;
    Button btnMua;

    GioHangAdapter adapter;

    public GioHangFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_giohang, container, false);

        rvGioHang = view.findViewById(R.id.rvGioHang);
        tvTongTien = view.findViewById(R.id.tvTongTien);
        btnMua = view.findViewById(R.id.btnMua);
        CheckBox cbAll = view.findViewById(R.id.cbAll);

        // RecyclerView
        rvGioHang.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new GioHangAdapter(GioHangManager.gioHang, this::capNhatTongTien);
        rvGioHang.setAdapter(adapter);

        // cập nhật tổng tiền
        capNhatTongTien();

        // nút mua
        btnMua.setOnClickListener(v -> {

            ArrayList<GioHangItem> listMua = getDanhSachDaChon();

            if (listMua.isEmpty()) {
                Toast.makeText(getContext(), "Vui lòng chọn sản phẩm", Toast.LENGTH_SHORT).show();
                return;
            }

            ThanhToanFragment fragment = ThanhToanFragment.newInstance(listMua);

            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameLayout, fragment)
                    .addToBackStack(null)
                    .commit();
        });

        cbAll.setOnCheckedChangeListener((buttonView, isChecked) -> {

            for (GioHangItem item : GioHangManager.gioHang) {
                item.setChecked(isChecked);
            }

            adapter.notifyDataSetChanged();
            capNhatTongTien();
        });
        return view;
    }

    // ===== TÍNH TỔNG =====
    private void capNhatTongTien() {
        int tong = GioHangManager.getTongTien();
        tvTongTien.setText(tong + "đ");
    }
    private ArrayList<GioHangItem> getDanhSachDaChon() {

        ArrayList<GioHangItem> list = new ArrayList<>();

        for (GioHangItem item : GioHangManager.gioHang) {
            if (item.isChecked()) {
                list.add(item);
            }
        }

        return list;
    }
}