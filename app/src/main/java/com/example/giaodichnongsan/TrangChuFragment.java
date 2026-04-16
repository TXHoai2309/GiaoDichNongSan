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
                "Súp lơ xanh được trồng theo phương pháp hữu cơ tại vườn, không sử dụng thuốc trừ sâu hóa học. "
                        + "Sản phẩm được thu hoạch trong ngày, đảm bảo độ tươi ngon và giữ nguyên giá trị dinh dưỡng. "
                        + "Phù hợp cho các món xào, luộc hoặc ăn kiêng.",
                "Đà Lạt - Lâm Đồng",
                4.5f,
                "Shop Nông Sản Sạch Đà Lạt"
        ));

        listNoiBat.add(new SanPham(
                2,
                R.drawable.ic_ca_chua,
                "Cà chua tươi",
                25000,
                98,
                "Cà chua đỏ mọng, được thu hoạch trực tiếp từ nông trại vào mỗi buổi sáng. "
                        + "Không sử dụng chất bảo quản, đảm bảo an toàn cho sức khỏe. "
                        + "Thích hợp làm salad, nấu canh hoặc ép nước.",
                "Lâm Đồng",
                4.2f,
                "Farm Fresh Organic"
        ));

        listNoiBat.add(new SanPham(
                3,
                R.drawable.ic_ngo,
                "Ngô ngọt",
                20000,
                200,
                "Ngô ngọt tự nhiên, hạt vàng đều, vị ngọt thanh và thơm. "
                        + "Được trồng theo quy trình VietGAP, đảm bảo sạch và an toàn. "
                        + "Thích hợp luộc, nướng hoặc chế biến món ăn gia đình.",
                "Hà Nội",
                4.7f,
                "Nông Trại Xanh Miền Bắc"
        ));
        listMoi = new ArrayList<>();

        listMoi.add(new SanPham(
                4,
                R.drawable.ic_ca_rot,
                "Cà rốt",
                18000,
                75,
                "Cà rốt tươi, màu cam đậm, giàu vitamin A tốt cho mắt. "
                        + "Sản phẩm được trồng tại vùng cao nguyên, đảm bảo chất lượng và độ ngọt tự nhiên.",
                "Đà Lạt",
                4.3f,
                "Rau Củ Sạch 24h"
        ));

        listMoi.add(new SanPham(
                5,
                R.drawable.ic_dau_ha_lan,
                "Đậu hà lan",
                30000,
                60,
                "Đậu hà lan xanh, giòn, không xơ, rất thích hợp cho các món xào hoặc luộc. "
                        + "Được thu hoạch và đóng gói trong ngày.",
                "Sapa - Lào Cai",
                4.6f,
                "Nông Sản Tây Bắc"
        ));

        listMoi.add(new SanPham(
                2,
                R.drawable.ic_ca_chua,
                "Cà chua tươi",
                25000,
                98,
                "Cà chua sạch, mọng nước, vị chua nhẹ tự nhiên. "
                        + "Được tuyển chọn kỹ lưỡng trước khi đến tay người tiêu dùng.",
                "Lâm Đồng",
                4.2f,
                "Farm Fresh Organic"
        ));

        listMoi.add(new SanPham(
                3,
                R.drawable.ic_ngo,
                "Ngô ngọt",
                20000,
                200,
                "Ngô tươi, hạt đều, vị ngọt tự nhiên. "
                        + "Đảm bảo không sử dụng chất kích thích tăng trưởng.",
                "Hà Nội",
                4.7f,
                "Nông Trại Xanh Miền Bắc"
        ));

        listMoi.add(new SanPham(
                6,
                R.drawable.ic_khoai_tay,
                "Khoai tây",
                22000,
                150,
                "Khoai tây vàng, bở, thơm, thích hợp chiên, nướng hoặc nấu canh. "
                        + "Sản phẩm được bảo quản lạnh để giữ độ tươi lâu.",
                "Đà Lạt",
                4.8f,
                "Shop Nông Sản Sạch Đà Lạt"
        ));

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