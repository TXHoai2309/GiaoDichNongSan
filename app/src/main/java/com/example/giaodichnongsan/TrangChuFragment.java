package com.example.giaodichnongsan;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TrangChuFragment extends Fragment {

    // ===== VIEW =====
    private RecyclerView rvNoiBat, rvSanPhamMoi, rvDanhMuc;

    // ===== DATA =====
    private ArrayList<SanPham> listNoiBat, listMoi;
    private ArrayList<DanhMuc> listDanhMuc;

    // ===== ADAPTER =====
    private SanPhamAdapter adapterNoiBat;
    private SanPhamMoiAdapter adapterMoi;
    private DanhMucAdapter danhMucAdapter;

    public TrangChuFragment() {
        // Required empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_trangchu, container, false);

        initView(view);
        setupRecyclerView();
        loadData();
        setupAdapter();

        return view;
    }

    // ===== ÁNH XẠ VIEW =====
    private void initView(View view) {
        rvNoiBat = view.findViewById(R.id.rvNoiBat);
        rvSanPhamMoi = view.findViewById(R.id.rvSanPhamMoi);
        rvDanhMuc = view.findViewById(R.id.rvDanhMuc);
    }

    // ===== SETUP LAYOUT =====
    private void setupRecyclerView() {

        rvNoiBat.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false)
        );

        rvSanPhamMoi.setLayoutManager(new GridLayoutManager(getContext(), 3));

        rvDanhMuc.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false)
        );

        // tránh bị add spacing nhiều lần
        if (rvSanPhamMoi.getItemDecorationCount() == 0) {
            rvSanPhamMoi.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    outRect.set(8, 8, 8, 8);
                }
            });
        }
    }

    // ===== LOAD DATA (FAKE) =====
    private void loadData() {
        listNoiBat = FakeDataSanPham.getSanPhamNoiBat();
        listMoi = FakeDataSanPham.getSanPhamMoi();
        listDanhMuc = FakeDataSanPham.getDanhMuc();
    }

    // ===== SETUP ADAPTER =====
    private void setupAdapter() {

        adapterNoiBat = new SanPhamAdapter(getContext(), listNoiBat);
        adapterMoi = new SanPhamMoiAdapter(getContext(), listMoi);
        danhMucAdapter = new DanhMucAdapter(getContext(), listDanhMuc);

        rvNoiBat.setAdapter(adapterNoiBat);
        rvSanPhamMoi.setAdapter(adapterMoi);
        rvDanhMuc.setAdapter(danhMucAdapter);
    }
}