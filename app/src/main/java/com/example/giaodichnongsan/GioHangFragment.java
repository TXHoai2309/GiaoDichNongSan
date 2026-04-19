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
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
        setupViewModel();
        setupRecyclerView();
        observeData();
        setupEvent();

        return view;
    }

    // ===== INIT VIEW =====
    private void initView(View view) {
        recyclerView = view.findViewById(R.id.rvGioHang);
        tvTongTien = view.findViewById(R.id.tvTongTien);
        btnMua = view.findViewById(R.id.btnMua);
        cbSelectAll = view.findViewById(R.id.cbAll);
    }

    // ===== VIEWMODEL =====
    private void setupViewModel() {
        viewModel = new ViewModelProvider(requireActivity()).get(GioHangViewModel.class);
    }

    // ===== RECYCLER =====
    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new GioHangAdapter(new ArrayList<>(), listener);
        recyclerView.setAdapter(adapter);
    }

    // ===== OBSERVE =====
    private void observeData() {
        viewModel.getGioHangList().observe(getViewLifecycleOwner(), list -> {
            adapter.setData(list);
            updateTongTien(); // 🔥 realtime update
        });
    }

    // ===== UPDATE TIỀN =====
    private void updateTongTien() {
        tvTongTien.setText("Tổng tiền: " + viewModel.getSelectedTotalPrice() + "đ");
    }

    // ===== EVENT =====
    private void setupEvent() {

        // ===== MUA HÀNG =====
        btnMua.setOnClickListener(v -> {

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

        // ===== CHỌN TẤT CẢ =====
        cbSelectAll.setOnCheckedChangeListener((buttonView, isChecked) -> {
            viewModel.selectAll(isChecked);
            updateTongTien();
        });
    }

    // ===== CALLBACK ADAPTER =====
    private final GioHangAdapter.OnItemClickListener listener = new GioHangAdapter.OnItemClickListener() {

        @Override
        public void onIncrease(GioHangItem item) {
            viewModel.increaseQuantity(item);
            updateTongTien();
        }

        @Override
        public void onDecrease(GioHangItem item) {
            viewModel.decreaseQuantity(item);
            updateTongTien();
        }

        @Override
        public void onRemove(GioHangItem item) {
            viewModel.removeItem(item);
            updateTongTien();
        }

        @Override
        public void onCheckChanged() {
            updateTongTien(); // 🔥 cực quan trọng
        }
    };
}