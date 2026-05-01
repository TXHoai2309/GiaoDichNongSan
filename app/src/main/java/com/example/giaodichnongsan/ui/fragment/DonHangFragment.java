package com.example.giaodichnongsan.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giaodichnongsan.R;
import com.example.giaodichnongsan.adapter.DonHangAdapter;
import com.example.giaodichnongsan.model.DonHang;
import com.example.giaodichnongsan.ui.activity.ChiTietDonHangActivity;
import com.example.giaodichnongsan.viewmodel.DonHangViewModel;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;


public class DonHangFragment extends Fragment {

    private RecyclerView recyclerView;
    private DonHangAdapter adapter;
    private DonHangViewModel viewModel;
    private TabLayout tabLayout;

    // 4 tab tương ứng 4 trạng thái
    private static final String[] TAB_LABELS = {
            "Tất cả", DonHang.DANG_GIAO, DonHang.DA_GIAO, DonHang.DA_HUY
    };

    public DonHangFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_donhang, container, false);

        // ===== INIT VIEW =====
        tabLayout   = view.findViewById(R.id.tabLayout);
        recyclerView = view.findViewById(R.id.rvDonHang);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel = new ViewModelProvider(requireActivity()).get(DonHangViewModel.class);

        adapter = new DonHangAdapter(new ArrayList<>(), donHang -> {
            Intent intent = new Intent(getActivity(), ChiTietDonHangActivity.class);
            intent.putExtra("don", donHang);
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);

        // ===== TẠO TABS =====
        for (String label : TAB_LABELS) {
            tabLayout.addTab(tabLayout.newTab().setText(label));
        }

        // ===== OBSERVE DATA =====
        viewModel.getDonHangList().observe(getViewLifecycleOwner(), list -> {
            // Khi data thay đổi → lọc lại theo tab đang chọn
            int selectedTab = tabLayout.getSelectedTabPosition();
            String trangThai = TAB_LABELS[selectedTab < 0 ? 0 : selectedTab];
            adapter.setData(viewModel.locTheotrangThai(trangThai));
            capNhatSoBadge(list); // cập nhật số lượng trên tab
        });

        // ===== XỬ LÝ CHỌN TAB =====
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String trangThai = TAB_LABELS[tab.getPosition()];
                adapter.setData(viewModel.locTheotrangThai(trangThai));
            }
            @Override public void onTabUnselected(TabLayout.Tab tab) {}
            @Override public void onTabReselected(TabLayout.Tab tab) {}
        });

        // ===== LOAD DỮ LIỆU =====
        viewModel.loadDonHang();

        return view;
    }

    // ===== HIỂN THỊ SỐ LƯỢNG TRÊN MỖI TAB =====
    private void capNhatSoBadge(ArrayList<DonHang> allList) {
        if (allList == null) return;

        int dangGiao = 0, daGiao = 0, daHuy = 0;
        for (DonHang dh : allList) {
            if (DonHang.DANG_GIAO.equals(dh.getTrangThai())) dangGiao++;
            else if (DonHang.DA_GIAO.equals(dh.getTrangThai())) daGiao++;
            else if (DonHang.DA_HUY.equals(dh.getTrangThai())) daHuy++;
        }

        // Tab 0: Tất cả (tổng)
        setTabText(0, "Tất cả", allList.size());
        setTabText(1, "Đang giao", dangGiao);
        setTabText(2, "Đã giao", daGiao);
        setTabText(3, "Đã hủy", daHuy);
    }

    private void setTabText(int index, String label, int count) {
        TabLayout.Tab tab = tabLayout.getTabAt(index);
        if (tab != null) {
            tab.setText(count > 0 ? label + " (" + count + ")" : label);
        }
    }

    // ===== GỌI KHI QUAY LẠI FRAGMENT (sau khi huỷ đơn) =====
    @Override
    public void onResume() {
        super.onResume();
        viewModel.loadDonHang(); // reload để cập nhật trạng thái mới nhất
    }
}