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

        listNoiBat.add(new SanPham(
                1,
                R.drawable.ic_sup_lo,
                "Súp lơ nhà trồng",
                15000,
                120,
                "Súp lơ sạch, trồng tại vườn, không thuốc hóa học",
                "Đà Lạt",
                4.5f, "shop rau sạch"
        ));

        listNoiBat.add(new SanPham(
                2,
                R.drawable.ic_ca_chua,
                "Cà chua tươi",
                25000,
                98,
                "Cà chua đỏ mọng, thu hoạch trong ngày",
                "Lâm Đồng",
                4.2f,"shop rau sạch"
        ));

        listNoiBat.add(new SanPham(
                3,
                R.drawable.ic_ngo,
                "Ngô ngọt",
                20000,
                200,
                "Ngô ngọt tự nhiên, ăn rất thơm",
                "Hà Nội",
                4.7f, "shop rau sạch"
        ));
        listMoi = new ArrayList<>();
        listMoi.add(new SanPham(4, R.drawable.ic_ca_rot, "Cà rốt", 18000, 75));
        listMoi.add(new SanPham(5, R.drawable.ic_dau_ha_lan, "Đậu hà lan", 30000, 60));
        listMoi.add(new SanPham(2, R.drawable.ic_ca_chua, "Cà chua tươi", 25000, 98));
        listMoi.add(new SanPham(3, R.drawable.ic_ngo, "Ngô ngọt", 20000, 200));
        listMoi.add(new SanPham(6, R.drawable.ic_khoai_tay, "Khoai tây", 22000, 150));

        listDanhMuc = new ArrayList<>();
        listDanhMuc.add(new DanhMuc(R.drawable.ic_rau, "Rau củ"));
        listDanhMuc.add(new DanhMuc(R.drawable.ic_traicay, "Trái cây"));
        listDanhMuc.add(new DanhMuc(R.drawable.ic_gao, "Gạo"));
        listDanhMuc.add(new DanhMuc(R.drawable.ic_more, "Thêm"));

        adapterMoi = new SanPhamMoiAdapter(getContext(), listMoi);
        danhMucAdapter = new DanhMucAdapter(getContext(), listDanhMuc);

        rvNoiBat.setAdapter(adapterNoiBat);
        rvSanPhamMoi.setAdapter(adapterMoi);
        rvDanhMuc.setAdapter(danhMucAdapter);

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