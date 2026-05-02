package com.example.giaodichnongsan.ui.activity;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giaodichnongsan.R;
import com.example.giaodichnongsan.adapter.SanPhamTrongDonAdapter;
import com.example.giaodichnongsan.model.DonHang;
import com.example.giaodichnongsan.model.GioHangItem;
import com.example.giaodichnongsan.viewmodel.DonHangViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class ChiTietDonHangActivity extends AppCompatActivity {

    private TextView tvTrangThai, tvNgayDat;
    private TextView tvTenNguoiMua, tvSdt, tvDiaChi;
    private TextView tvTien, tvTongTien;
    private Button btnCapNhat, btnHuyDon;
    private SanPhamTrongDonAdapter rvSanPhamAdapter;
    private DonHang donHang;
    private DonHangViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiet_donhang);

        viewModel = new ViewModelProvider(this).get(DonHangViewModel.class);

        initView();
        loadData();
        checkRole(); // kiểm tra quyền để hiện nút đổi trạng thái
    }

    private void initView() {
        tvTrangThai  = findViewById(R.id.tvTrangThai);
        tvNgayDat    = findViewById(R.id.tvNgayDat);
        tvTenNguoiMua = findViewById(R.id.tvTenNguoiMua);
        tvSdt        = findViewById(R.id.tvSdt);
        tvDiaChi     = findViewById(R.id.tvDiaChi);
        tvTien       = findViewById(R.id.tvTien);
        tvTongTien   = findViewById(R.id.tvTongTien); // (khai báo biến tương ứng ở đầu class)
        btnCapNhat   = findViewById(R.id.btnCapNhatTrangThai);
        btnHuyDon    = findViewById(R.id.btnHuyDon);

        RecyclerView rvSanPham = findViewById(R.id.rvSanPham);
        rvSanPham.setLayoutManager(new LinearLayoutManager(this));
        rvSanPham.setNestedScrollingEnabled(false);
        rvSanPhamAdapter = new SanPhamTrongDonAdapter(new ArrayList<>());
        rvSanPham.setAdapter(rvSanPhamAdapter);
    }

    private void loadData() {
        donHang = (DonHang) getIntent().getSerializableExtra("don");
        if (donHang == null) { finish(); return; }

        // Trạng thái + ngày
        setTrangThaiUI(donHang.getTrangThai());
        tvNgayDat.setText("Đặt lúc: " + donHang.getNgayDat());

        // Thông tin người mua
        tvTenNguoiMua.setText(donHang.getTenNguoiMua() != null ? donHang.getTenNguoiMua() : "Chưa cập nhật");
        tvSdt.setText(donHang.getSdtNguoiMua() != null ? donHang.getSdtNguoiMua() : "Chưa cập nhật");
        tvDiaChi.setText(donHang.getDiaChiGiao() != null ? donHang.getDiaChiGiao() : "Chưa cập nhật");

        // Danh sách sản phẩm
        rvSanPhamAdapter = new SanPhamTrongDonAdapter(donHang.getDanhSachSP());
        ((RecyclerView) findViewById(R.id.rvSanPham)).setAdapter(rvSanPhamAdapter);

        // Tiền
        tvTien.setText(String.format("%,dđ", donHang.getTongTien()));
        tvTongTien.setText(String.format("%,dđ", donHang.getTongTien()));

        // Nút huỷ
        if (DonHang.DANG_GIAO.equals(donHang.getTrangThai())) {
            btnHuyDon.setVisibility(View.VISIBLE);
            btnHuyDon.setOnClickListener(v -> xacNhanHuy());
        }
    }



    // ===== HIỂN THỊ MÀU TRẠNG THÁI =====
    private void setTrangThaiUI(String trangThai) {
        tvTrangThai.setText(trangThai);
        switch (trangThai) {
            case DonHang.DANG_GIAO:
                tvTrangThai.setTextColor(Color.parseColor("#FFA000")); break;
            case DonHang.DA_GIAO:
                tvTrangThai.setTextColor(Color.parseColor("#4CAF50")); break;
            case DonHang.DA_HUY:
                tvTrangThai.setTextColor(Color.RED); break;
        }
    }

    // ===== KIỂM TRA ROLE: chỉ seller/admin mới thấy nút đổi trạng thái =====
    private void checkRole() {
        String uid = FirebaseAuth.getInstance().getCurrentUser() != null
                ? FirebaseAuth.getInstance().getCurrentUser().getUid() : null;
        if (uid == null) { btnCapNhat.setVisibility(View.GONE); return; }

        FirebaseFirestore.getInstance().collection("users").document(uid)
                .get()
                .addOnSuccessListener(doc -> {
                    if (doc.exists()) {
                        // Chỉ cần kiểm tra "seller" thay vì isAdmin
                        Boolean isSeller = doc.getBoolean("seller"); // field là "seller" không phải "isSeller"
                        boolean coQuyen = Boolean.TRUE.equals(isSeller);

                        if (coQuyen && !DonHang.DA_GIAO.equals(donHang.getTrangThai())
                                && !DonHang.DA_HUY.equals(donHang.getTrangThai())) {
                            btnCapNhat.setVisibility(View.VISIBLE);
                            btnCapNhat.setOnClickListener(v -> showDialogCapNhat());
                        } else {
                            btnCapNhat.setVisibility(View.GONE);
                        }
                    }
                });
    }

    // ===== DIALOG CHỌN TRẠNG THÁI MỚI =====
    private void showDialogCapNhat() {
        String[] options = {DonHang.DA_GIAO, DonHang.DA_HUY};

        new AlertDialog.Builder(this)
                .setTitle("Cập nhật trạng thái")
                .setItems(options, (dialog, which) -> {
                    String trangThaiMoi = options[which];
                    xacNhanCapNhat(trangThaiMoi);
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    // ===== XÁC NHẬN & GỌI UPDATE =====
    private void xacNhanCapNhat(String trangThaiMoi) {
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận")
                .setMessage("Chuyển sang \"" + trangThaiMoi + "\"?")
                .setPositiveButton("Đồng ý", (d, w) -> {
                    MutableLiveData<Boolean> ketQua = new MutableLiveData<>();
                    ketQua.observe(this, success -> {
                        if (Boolean.TRUE.equals(success)) {
                            donHang.setTrangThai(trangThaiMoi);
                            setTrangThaiUI(trangThaiMoi);
                            btnCapNhat.setVisibility(View.GONE); // ẩn nút sau khi xong
                            Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
                        }
                    });
                    viewModel.capNhatTrangThai(donHang.getId(), trangThaiMoi, ketQua);
                })
                .setNegativeButton("Hủy", null)
                .show();
    }
    // ===== XÁC NHẬN HUỶ ĐƠN =====
    private void xacNhanHuy() {
        new AlertDialog.Builder(this)
                .setTitle("Huỷ đơn hàng")
                .setMessage("Bạn có chắc muốn huỷ đơn hàng này không?")
                .setPositiveButton("Huỷ đơn", (d, w) -> {
                    MutableLiveData<Boolean> ketQua = new MutableLiveData<>();
                    ketQua.observe(this, success -> {
                        if (Boolean.TRUE.equals(success)) {
                            donHang.setTrangThai(DonHang.DA_HUY);
                            setTrangThaiUI(DonHang.DA_HUY);
                            btnHuyDon.setVisibility(View.GONE);   // ẩn nút sau khi huỷ
                            btnCapNhat.setVisibility(View.GONE);  // ẩn luôn nút seller
                            Toast.makeText(this, "Đã huỷ đơn hàng!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Không thể huỷ đơn này!", Toast.LENGTH_SHORT).show();
                        }
                    });
                    viewModel.huyDonHang(donHang.getId(), ketQua);
                })
                .setNegativeButton("Giữ lại", null)
                .show();
    }
}