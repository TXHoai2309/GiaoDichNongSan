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

    RecyclerView rvNoiBat, rvSanPhamMoi, rvDanhMuc;

    ArrayList<SanPham> listNoiBat, listMoi;
    ArrayList<DanhMuc> listDanhMuc;

    SanPhamAdapter adapterNoiBat;
    SanPhamMoiAdapter adapterMoi;
    DanhMucAdapter danhMucAdapter;

    public TrangChuFragment() {
        // Required empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_trangchu, container, false);

        // ánh xạ view
        rvNoiBat = view.findViewById(R.id.rvNoiBat);
        rvSanPhamMoi = view.findViewById(R.id.rvSanPhamMoi);
        rvDanhMuc = view.findViewById(R.id.rvDanhMuc);

        // ===== LAYOUT =====

        rvNoiBat.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false)
        );

        rvSanPhamMoi.setLayoutManager(new GridLayoutManager(getContext(), 3));

        rvDanhMuc.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false)
        );

        // spacing
        rvSanPhamMoi.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(8, 8, 8, 8);
            }
        });

        // ===== DATA =====

        listNoiBat = new ArrayList<>();
        listNoiBat.add(new SanPham(R.drawable.ic_sup_lo, 1, "Súp lơ nhà trồng", 15000, 120));
        listNoiBat.add(new SanPham(R.drawable.ic_ca_chua, 2, "Cà chua tươi", 25000, 98));
        listNoiBat.add(new SanPham(R.drawable.ic_ngo, 3, "Ngô ngọt", 20000, 200));

        listMoi = new ArrayList<>();
        listMoi.add(new SanPham(R.drawable.ic_ca_rot, 4, "Cà rốt", 18000, 75));
        listMoi.add(new SanPham(R.drawable.ic_dau_ha_lan, 5, "Đậu hà lan", 30000, 60));
        listMoi.add(new SanPham(R.drawable.ic_ca_chua, 2, "Cà chua tươi", 25000, 98));
        listMoi.add(new SanPham(R.drawable.ic_ngo, 3, "Ngô ngọt", 20000, 200));
        listMoi.add(new SanPham(R.drawable.ic_khoai_tay, 6, "Khoai tây", 22000, 150));

        listDanhMuc = new ArrayList<>();
        listDanhMuc.add(new DanhMuc(R.drawable.ic_rau, "Rau củ"));
        listDanhMuc.add(new DanhMuc(R.drawable.ic_traicay, "Trái cây"));
        listDanhMuc.add(new DanhMuc(R.drawable.ic_gao, "Gạo"));
        listDanhMuc.add(new DanhMuc(R.drawable.ic_more, "Thêm"));

        // ===== ADAPTER =====

        adapterNoiBat = new SanPhamAdapter(getContext(), listNoiBat);
        adapterMoi = new SanPhamMoiAdapter(getContext(), listMoi);
        danhMucAdapter = new DanhMucAdapter(getContext(), listDanhMuc);

        rvNoiBat.setAdapter(adapterNoiBat);
        rvSanPhamMoi.setAdapter(adapterMoi);
        rvDanhMuc.setAdapter(danhMucAdapter);

        return view;
    }
}