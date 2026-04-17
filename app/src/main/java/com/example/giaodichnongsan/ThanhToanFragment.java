package com.example.giaodichnongsan;

import android.os.Bundle;
import android.view.*;
import android.widget.*;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ThanhToanFragment extends Fragment {

    RecyclerView rvThanhToan;
    TextView tvTienHang, tvTong, tvPhiShip;
    Button btnDatHang;

    ArrayList<GioHangItem> listMua;
    GioHangAdapter adapter;

    int phiShip = 30000;

    public static ThanhToanFragment newInstance(ArrayList<GioHangItem> list) {
        ThanhToanFragment fragment = new ThanhToanFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable("data", list);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_thanhtoan, container, false);

        rvThanhToan = view.findViewById(R.id.rvThanhToan);
        tvTienHang = view.findViewById(R.id.tvTienHang);
        tvTong = view.findViewById(R.id.tvTong);
        tvPhiShip = view.findViewById(R.id.tvPhiShip);
        btnDatHang = view.findViewById(R.id.btnDatHang);

        // nhận data
        if (getArguments() != null) {
            listMua = (ArrayList<GioHangItem>) getArguments().getSerializable("data");
        }

        // recycler
        rvThanhToan.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new GioHangAdapter(listMua, this::tinhTien);
        rvThanhToan.setAdapter(adapter);

        tinhTien();

        btnDatHang.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Đặt hàng thành công!", Toast.LENGTH_SHORT).show();
        });

        return view;
    }

    private void tinhTien() {
        int tienHang = 0;

        for (GioHangItem item : listMua) {
            tienHang += item.getSanPham().getGia() * item.getSoLuong();
        }

        int tong = tienHang + phiShip;

        tvTienHang.setText("Tiền hàng: " + String.format("%,dđ", tienHang));
        tvTong.setText("Tổng: " + String.format("%,dđ", tong));
    }
}