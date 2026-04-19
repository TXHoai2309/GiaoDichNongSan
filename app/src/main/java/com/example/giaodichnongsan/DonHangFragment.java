package com.example.giaodichnongsan;

import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.*;
import java.util.ArrayList;

public class DonHangFragment extends Fragment {

    RecyclerView rvDonHang;
    DonHangAdapter adapter;
    ArrayList<DonHang> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_donhang, container, false);

        rvDonHang = view.findViewById(R.id.rvDonHang);
        rvDonHang.setLayoutManager(new LinearLayoutManager(getContext()));

        list = DonHangManager.getDanhSach();
        rvDonHang.setAdapter(adapter);

        adapter = new DonHangAdapter(list, don -> {
            Intent intent = new Intent(getContext(), ChiTietDonHangActivity.class);
            intent.putExtra("don", don);
            startActivity(intent);
        });
        rvDonHang.setAdapter(adapter);

        return view;
    }
}