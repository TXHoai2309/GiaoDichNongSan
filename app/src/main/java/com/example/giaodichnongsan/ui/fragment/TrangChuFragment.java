package com.example.giaodichnongsan.ui.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giaodichnongsan.R;
import com.example.giaodichnongsan.adapter.DanhMucAdapter;
import com.example.giaodichnongsan.adapter.SanPhamAdapter;
import com.example.giaodichnongsan.adapter.SanPhamMoiAdapter;
import com.example.giaodichnongsan.model.DanhMuc;
import com.example.giaodichnongsan.model.SanPham;
import com.example.giaodichnongsan.viewmodel.TrangChuViewModel;

import java.util.ArrayList;

public class TrangChuFragment extends Fragment {

    // ===== VIEW =====
    private RecyclerView rvNoiBat, rvSanPhamMoi, rvDanhMuc;

    // ===== ADAPTER =====
    private SanPhamAdapter adapterNoiBat;
    private SanPhamMoiAdapter adapterMoi;
    private DanhMucAdapter danhMucAdapter;

    // ===== VIEWMODEL =====
    private TrangChuViewModel viewModel;

    public TrangChuFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_trangchu, container, false);

        initView(view);
        setupRecyclerView();
        setupViewModel();
        setupObserver();

        return view;
    }

    private void initView(View view) {
        rvNoiBat = view.findViewById(R.id.rvNoiBat);
        rvSanPhamMoi = view.findViewById(R.id.rvSanPhamMoi);
        rvDanhMuc = view.findViewById(R.id.rvDanhMuc);
    }

    private void setupRecyclerView() {
        rvNoiBat.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false)
        );

        rvSanPhamMoi.setLayoutManager(new GridLayoutManager(getContext(), 2));

        rvDanhMuc.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false)
        );

        if (rvSanPhamMoi.getItemDecorationCount() == 0) {
            rvSanPhamMoi.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    outRect.set(8, 8, 8, 8);
                }
            });
        }
    }


    // ===== KẾT NỐI VIEWMODEL =====
    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(TrangChuViewModel.class);
    }

    // ===== OBSERVE DATA =====
    private void setupObserver() {

        viewModel.getSanPhamNoiBat().observe(getViewLifecycleOwner(), list -> {
            adapterNoiBat = new SanPhamAdapter(getContext(), new ArrayList<>(list));
            rvNoiBat.setAdapter(adapterNoiBat);
        });

        viewModel.getSanPhamMoi().observe(getViewLifecycleOwner(), list -> {
            adapterMoi = new SanPhamMoiAdapter(getContext(), new ArrayList<>(list));
            rvSanPhamMoi.setAdapter(adapterMoi);
        });

        viewModel.getDanhMuc().observe(getViewLifecycleOwner(), list -> {
            // 🔥 truyền thêm listener vào adapter
            danhMucAdapter = new DanhMucAdapter(getContext(), new ArrayList<>(list), danhMuc -> {
                viewModel.filterByDanhMuc(danhMuc.getId());
            });
            rvDanhMuc.setAdapter(danhMucAdapter);
        });

        viewModel.loadData();
    }
}