package com.example.giaodichnongsan.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giaodichnongsan.R;
import com.example.giaodichnongsan.adapter.ThanhToanAdapter;
import com.example.giaodichnongsan.model.GioHangItem;
import com.example.giaodichnongsan.ui.activity.DatHangThanhCongActivity;
import com.example.giaodichnongsan.viewmodel.DonHangViewModel;
import com.example.giaodichnongsan.viewmodel.GioHangViewModel;

import java.util.ArrayList;

public class ThanhToanFragment extends Fragment {

    // ===== VIEW =====
    private RecyclerView rvThanhToan;
    private TextView tvTongTien;
    private Button btnDatHang;

    // ===== ADAPTER =====
    private ThanhToanAdapter adapter;

    // ===== VIEWMODEL =====
    private GioHangViewModel gioHangViewModel;
    private DonHangViewModel donHangViewModel;

    // ===== DATA =====
    private ArrayList<GioHangItem> listMua;

    public ThanhToanFragment() {}

    public static ThanhToanFragment newInstance(ArrayList<GioHangItem> list) {
        ThanhToanFragment fragment = new ThanhToanFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("list", list);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_thanhtoan, container, false);

        initView(view);
        initData();
        initViewModel();
        setupRecyclerView();
        setupUI();
        setupEvent();

        return view;
    }

    // ===== INIT VIEW =====
    private void initView(View view) {
        rvThanhToan = view.findViewById(R.id.rvThanhToan);
        tvTongTien = view.findViewById(R.id.tvTong); // ⚠️ nhớ đúng ID XML
        btnDatHang = view.findViewById(R.id.btnDatHang);
    }

    // ===== INIT DATA =====
    private void initData() {
        if (getArguments() != null) {
            listMua = (ArrayList<GioHangItem>) getArguments().getSerializable("list");
        }

        if (listMua == null) {
            listMua = new ArrayList<>();
        }
    }

    // ===== INIT VIEWMODEL =====
    private void initViewModel() {
        gioHangViewModel = new ViewModelProvider(requireActivity()).get(GioHangViewModel.class);
        donHangViewModel = new ViewModelProvider(requireActivity()).get(DonHangViewModel.class); // 🔥 FIX CRASH
    }

    // ===== SETUP RECYCLER =====
    private void setupRecyclerView() {
        rvThanhToan.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ThanhToanAdapter(listMua);
        rvThanhToan.setAdapter(adapter);
    }

    // ===== UI =====
    private void setupUI() {
        int tongTien = tinhTongTien(listMua);
        tvTongTien.setText("Tổng tiền: " + tongTien + "đ");
    }

    // ===== EVENT =====
    private void setupEvent() {

        btnDatHang.setOnClickListener(v -> {
            if (listMua.isEmpty()) {
                Toast.makeText(getContext(), "Giỏ hàng trống", Toast.LENGTH_SHORT).show();
                return;
            }

            int tongTien = tinhTongTien(listMua);
            donHangViewModel.addDonHang(new ArrayList<>(listMua), tongTien);
            gioHangViewModel.clearCart();

            // ✅ Bỏ requireActivity().finish()
            // Chỉ start activity mới, không finish MainActivity
            startActivity(new Intent(getActivity(), DatHangThanhCongActivity.class));

            // Quay về TrangChu
            requireActivity().getSupportFragmentManager()
                    .popBackStack(null, androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE);
        });
    }

    // ===== TÍNH TIỀN =====
    private int tinhTongTien(ArrayList<GioHangItem> list) {
        int total = 0;

        for (GioHangItem item : list) {
            total += item.getSanPham().getGia() * item.getSoLuong();
        }

        return total;
    }
}