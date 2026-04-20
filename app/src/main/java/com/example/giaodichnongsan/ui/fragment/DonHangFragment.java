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
import com.example.giaodichnongsan.ui.activity.ChiTietDonHangActivity;
import com.example.giaodichnongsan.viewmodel.DonHangViewModel;

import java.util.ArrayList;

public class DonHangFragment extends Fragment {

    private RecyclerView recyclerView;
    private DonHangAdapter adapter;
    private DonHangViewModel viewModel;

    public DonHangFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_donhang, container, false);

        recyclerView = view.findViewById(R.id.rvDonHang);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel = new ViewModelProvider(requireActivity()).get(DonHangViewModel.class);

        adapter = new DonHangAdapter(new ArrayList<>(), donHang -> {
            Intent intent = new Intent(getActivity(), ChiTietDonHangActivity.class);
            intent.putExtra("don", donHang);
            startActivity(intent);
        });

        recyclerView.setAdapter(adapter);

        // observe dữ liệu
        viewModel.getDonHangList().observe(getViewLifecycleOwner(), list -> {
            adapter.setData(list);
        });

        return view;
    }
}