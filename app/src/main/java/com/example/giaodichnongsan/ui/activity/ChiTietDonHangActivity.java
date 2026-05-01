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

import com.example.giaodichnongsan.R;
import com.example.giaodichnongsan.model.DonHang;
import com.example.giaodichnongsan.model.GioHangItem;
import com.example.giaodichnongsan.viewmodel.DonHangViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


public class ChiTietDonHangActivity extends AppCompatActivity {

    private TextView tvTen, tvSoLuong, tvTien, tvTrangThai;
    private Button btnCapNhat;
    private Button btnHuyDon;
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
        tvTen      = findViewById(R.id.tvTen);
        tvSoLuong  = findViewById(R.id.tvSoLuong);
        tvTien     = findViewById(R.id.tvTien);
        tvTrangThai = findViewById(R.id.tvTrangThai);
        btnCapNhat = findViewById(R.id.btnCapNhatTrangThai);
        btnHuyDon = findViewById(R.id.btnHuyDon);
    }

    private void loadData() {
        donHang = (DonHang) getIntent().getSerializableExtra("don");
        if (donHang == null) { finish(); return; }

        StringBuilder tenSP = new StringBuilder();
        int tongSoLuong = 0;

        if (donHang.getDanhSachSP() != null) {
            for (GioHangItem item : donHang.getDanhSachSP()) {
                tenSP.append(item.getSanPham().getTen())
                        .append(" x").append(item.getSoLuong()).append("\n");
                tongSoLuong += item.getSoLuong();
            }
        }
        // Chỉ hiện nút huỷ nếu đơn đang giao
        if (DonHang.DANG_GIAO.equals(donHang.getTrangThai())) {
            btnHuyDon.setVisibility(View.VISIBLE);
            btnHuyDon.setOnClickListener(v -> xacNhanHuy());
        } else {
            btnHuyDon.setVisibility(View.GONE);
        }

        tvTen.setText(tenSP.toString().trim());
        tvSoLuong.setText("Tổng SL: " + tongSoLuong);
        tvTien.setText(String.format("Tổng: %,dđ", donHang.getTongTien()));
        setTrangThaiUI(donHang.getTrangThai());
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