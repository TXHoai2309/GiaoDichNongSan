package com.example.giaodichnongsan;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TrangChu extends AppCompatActivity {

    RecyclerView rvNoiBat, rvSanPhamMoi, rvDanhMuc;

    ArrayList<SanPham> listNoiBat, listMoi;
    ArrayList<DanhMuc> listDanhMuc;

    SanPhamAdapter adapterNoiBat;
    SanPhamMoiAdapter adapterMoi; // adapter riêng
    DanhMucAdapter danhMucAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu);

        rvNoiBat = findViewById(R.id.rvNoiBat);
        rvSanPhamMoi = findViewById(R.id.rvSanPhamMoi);
        rvDanhMuc = findViewById(R.id.rvDanhMuc);

        // 🔥 Sản phẩm nổi bật (kéo ngang)
        rvNoiBat.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        );

        // 🔥 Sản phẩm mới (grid 3 cột)
        rvSanPhamMoi.setLayoutManager(new GridLayoutManager(this, 3));
        // 🔥 Danh mục (kéo ngang)
        rvDanhMuc.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        );
        // spacing cho đẹp
        rvSanPhamMoi.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(8, 8, 8, 8);
            }
        });

        // ===== SẢN PHẨM NỔI BẬT =====
        listNoiBat = new ArrayList<>();
        listNoiBat.add(new SanPham(R.drawable.ic_sup_lo, 1,"Súp lơ nhà trồng", 15000, 120));
        listNoiBat.add(new SanPham(R.drawable.ic_ca_chua, 2,"Cà chua tươi", 25000, 98));
        listNoiBat.add(new SanPham(R.drawable.ic_ngo, 3,"Ngô ngọt", 20000, 200));

        // ===== SẢN PHẨM MỚI =====
        listMoi = new ArrayList<>();
        listMoi.add(new SanPham(R.drawable.ic_ca_rot, 4,"Cà rốt", 18000, 75));
        listMoi.add(new SanPham(R.drawable.ic_dau_ha_lan,5, "Đậu hà lan", 30000, 60));
        listMoi.add(new SanPham(R.drawable.ic_ca_chua, 2,"Cà chua tươi", 25000, 98));
        listMoi.add(new SanPham(R.drawable.ic_ngo, 3, "Ngô ngọt", 20000, 200));
        listMoi.add(new SanPham(R.drawable.ic_khoai_tay, 6,"Khoai tây", 22000, 150));

        // ===== DANH MỤC =====
        listDanhMuc = new ArrayList<>();
        listDanhMuc.add(new DanhMuc(R.drawable.ic_rau, "Rau củ"));
        listDanhMuc.add(new DanhMuc(R.drawable.ic_traicay, "Trái cây"));
        listDanhMuc.add(new DanhMuc(R.drawable.ic_gao, "Gạo"));
        listDanhMuc.add(new DanhMuc(R.drawable.ic_more, "Thêm"));

        adapterNoiBat = new SanPhamAdapter(this, listNoiBat);
        adapterMoi = new SanPhamMoiAdapter(this, listMoi); // dùng adapter mới
        danhMucAdapter = new DanhMucAdapter(this, listDanhMuc);

        rvNoiBat.setAdapter(adapterNoiBat);
        rvSanPhamMoi.setAdapter(adapterMoi);
        rvDanhMuc.setAdapter(danhMucAdapter);
    }
}