package com.example.giaodichnongsan.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giaodichnongsan.R;
import com.example.giaodichnongsan.adapter.GioHangAdapter;
import com.example.giaodichnongsan.model.GioHangItem;
import com.example.giaodichnongsan.utils.AuthHelper;
import com.example.giaodichnongsan.viewmodel.GioHangViewModel;

import java.util.ArrayList;

public class GioHangFragment extends Fragment {

    // ===== VIEW =====
    private RecyclerView recyclerView;
    private TextView tvTongTien;
    private Button btnMua;
    private CheckBox cbSelectAll;

    // ===== ADAPTER =====
    private GioHangAdapter adapter;

    // ===== VIEWMODEL =====
    private GioHangViewModel viewModel;

    public GioHangFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_giohang, container, false);

        initView(view);
        initViewModel();
        setupRecyclerView();
        setupObserver();
        setupEvents();
        viewModel.loadGioHang();

        return view;

    }

    // ===== INIT VIEW =====
    private void initView(View view) {
        recyclerView = view.findViewById(R.id.rvGioHang);
        tvTongTien = view.findViewById(R.id.tvTongTien);
        btnMua = view.findViewById(R.id.btnMua);
        cbSelectAll = view.findViewById(R.id.cbAll);
    }

    // ===== INIT VIEWMODEL =====
    private void initViewModel() {
        viewModel = new ViewModelProvider(requireActivity()).get(GioHangViewModel.class);
    }

    // ===== RECYCLER =====
    private void setupRecyclerView() {
        adapter = new GioHangAdapter(new ArrayList<>(), listener);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    // ===== OBSERVER =====
    private void setupObserver() {

        // cập nhật list
        viewModel.getGioHangList().observe(getViewLifecycleOwner(), list -> {
            adapter.setData(list);
        });

        // cập nhật tổng tiền (🔥 chuẩn MVVM)
        viewModel.getTongTien().observe(getViewLifecycleOwner(), total -> {
            tvTongTien.setText("Tổng tiền: " + total + "đ");
        });

        // trạng thái select all (optional)
        viewModel.getIsAllSelected().observe(getViewLifecycleOwner(), isAll -> {
            if (cbSelectAll.isChecked() != isAll) {
                cbSelectAll.setChecked(isAll);
            }
        });
    }

    // ===== EVENTS =====
    private void setupEvents() {

        // mua hàng
        btnMua.setOnClickListener(v -> {
            AuthHelper.requireLogin(getContext(), () -> {
                ArrayList<GioHangItem> selectedList = viewModel.getSelectedItems();
                if (selectedList == null || selectedList.isEmpty()) {
                    Toast.makeText(getContext(), "Chưa chọn sản phẩm", Toast.LENGTH_SHORT).show();
                    return;
                }
                ThanhToanFragment fragment = ThanhToanFragment.newInstance(new ArrayList<>(selectedList));
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameLayout, fragment)
                        .addToBackStack(null)
                        .commit();
            });
        });

        // chọn tất cả
        cbSelectAll.setOnCheckedChangeListener((buttonView, isChecked) -> {
            viewModel.selectAll(isChecked);
        });
    }


    // ===== CALLBACK =====
    private final GioHangAdapter.OnItemClickListener listener = new GioHangAdapter.OnItemClickListener() {

        @Override
        public void onIncrease(GioHangItem item) {
            viewModel.increaseQuantity(item);
        }

        @Override
        public void onDecrease(GioHangItem item) {
            viewModel.decreaseQuantity(item);
        }

        @Override
        public void onRemove(GioHangItem item) {
            viewModel.removeItem(item);
        }

        @Override
        public void onCheckChanged() {
            viewModel.updateSelection(); // 🔥 tính lại total trong VM
        }

    };
    // Thêm onResume() vào cuối class:
    @Override
    public void onResume() {
        super.onResume();
        viewModel.loadGioHang(); // reload khi quay lại tab giỏ hàng
    }
}