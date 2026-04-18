package com.example.giaodichnongsan;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.*;
import android.widget.*;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ThanhToanFragment extends Fragment {

    // UI
    private RecyclerView rvThanhToan;
    private TextView tvTienHang, tvTong, tvPhiShip;
    private TextView tvTenNguoiNhan, tvDiaChi, tvGhiChu, tvDanhSachSP, tvTenThanhToan, tvMoTaThanhToan, tvMoTaThanhToanChiTiet;;
    private LinearLayout layoutThongTin, layoutThanhToan;
    private Button btnDatHang;

    // Data
    private ArrayList<GioHangItem> listMua;
    private ThanhToanAdapter adapter;

    // trạng thái
    private String phuongThucThanhToan = "COD";

    // Giá trị
    private int phiShip = 30000;
    private int tienHang = 0;

    public static ThanhToanFragment newInstance(ArrayList<GioHangItem> list) {
        ThanhToanFragment fragment = new ThanhToanFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", list);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_thanhtoan, container, false);

        initView(view);
        getData();
        setupRecyclerView();
        setupListeners();
        updateUI();

        return view;
    }

    // ================= INIT =================
    private void initView(View view) {
        rvThanhToan = view.findViewById(R.id.rvThanhToan);
        tvTienHang = view.findViewById(R.id.tvTienHang);
        tvTong = view.findViewById(R.id.tvTong);
        tvPhiShip = view.findViewById(R.id.tvPhiShip);

        tvTenNguoiNhan = view.findViewById(R.id.tvTenNguoiNhan);
        tvDiaChi = view.findViewById(R.id.tvDiaChi);
        tvGhiChu = view.findViewById(R.id.tvGhiChu);
        tvDanhSachSP = view.findViewById(R.id.tvDanhSachSP);

        layoutThanhToan = view.findViewById(R.id.layoutThanhToan);
        tvTenThanhToan = view.findViewById(R.id.tvTenThanhToan);
        tvMoTaThanhToan = view.findViewById(R.id.tvMoTaThanhToan);
        tvMoTaThanhToanChiTiet = view.findViewById(R.id.tvMoTaThanhToanChiTiet);

        layoutThongTin = view.findViewById(R.id.layoutThongTin);
        btnDatHang = view.findViewById(R.id.btnDatHang);
    }

    // ================= DATA =================
    private void getData() {
        if (getArguments() != null) {
            listMua = (ArrayList<GioHangItem>) getArguments().getSerializable("data");
        }

        if (listMua == null) {
            listMua = new ArrayList<>();
        }
    }

    // ================= RECYCLER =================
    private void setupRecyclerView() {
        rvThanhToan.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ThanhToanAdapter(listMua);
        rvThanhToan.setAdapter(adapter);
        layoutThanhToan.setOnClickListener(v -> showChonThanhToan());

    }

    // ================= LISTENER =================
    private void setupListeners() {

        btnDatHang.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Đặt hàng thành công!", Toast.LENGTH_SHORT).show();
        });

        layoutThongTin.setOnClickListener(v -> showDialogCapNhatThongTin());
    }

    // ================= UPDATE UI =================
    private void updateUI() {
        tinhTien();
        hienThiDanhSachSanPham();
    }

    // ================= TÍNH TIỀN =================
    private void tinhTien() {
        tienHang = 0;

        for (GioHangItem item : listMua) {
            tienHang += item.getSanPham().getGia() * item.getSoLuong();
        }

        int tong = tienHang + phiShip;

        tvTienHang.setText("Tiền hàng: " + String.format("%,dđ", tienHang));
        tvTong.setText("Tổng thanh toán: " + String.format("%,dđ", tong));
        tvPhiShip.setText(String.format("%,dđ", phiShip));
    }

    // ================= HIỂN THỊ SP =================
    private void hienThiDanhSachSanPham() {
        StringBuilder builder = new StringBuilder();

        for (GioHangItem item : listMua) {
            builder.append(item.getSanPham().getTen())
                    .append(" x")
                    .append(item.getSoLuong())
                    .append("\n");
        }

        tvDanhSachSP.setText(builder.toString());
    }

    // ================= DIALOG =================
    private void showDialogCapNhatThongTin() {
        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(R.layout.dialog_thongtin);

// 🔥 FIX SIZE DIALOG
        if (dialog.getWindow() != null) {
            dialog.getWindow().setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
        }

        dialog.show();
        dialog.setContentView(R.layout.dialog_thongtin);

        EditText edtTen = dialog.findViewById(R.id.edtTen);
        EditText edtSDT = dialog.findViewById(R.id.edtSDT);
        EditText edtDiaChi = dialog.findViewById(R.id.edtDiaChi);
        EditText edtGhiChu = dialog.findViewById(R.id.edtGhiChu);
        Button btnLuu = dialog.findViewById(R.id.btnLuu);

        btnLuu.setOnClickListener(v -> {

            String ten = edtTen.getText().toString().trim();
            String sdt = edtSDT.getText().toString().trim();
            String diachi = edtDiaChi.getText().toString().trim();
            String ghichu = edtGhiChu.getText().toString().trim();

            if (TextUtils.isEmpty(ten) || TextUtils.isEmpty(sdt) || TextUtils.isEmpty(diachi)) {
                Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            tvTenNguoiNhan.setText(ten + " (" + sdt + ")");
            tvDiaChi.setText(diachi);

            if (TextUtils.isEmpty(ghichu)) {
                tvGhiChu.setText("Ghi chú: Không có");
            } else {
                tvGhiChu.setText("Ghi chú: " + ghichu);
            }

            dialog.dismiss();
        });

        dialog.show();
    }
    private void showChonThanhToan() {
        String[] options = {
                "Thanh toán khi nhận hàng (COD)",
                "Chuyển khoản ngân hàng",
                "Ví điện tử (Momo/ZaloPay)"
        };

        new AlertDialog.Builder(requireContext())
                .setTitle("Chọn phương thức thanh toán")
                .setItems(options, (dialog, which) -> {

                    if (which == 0) {
                        phuongThucThanhToan = "COD";
                        tvTenThanhToan.setText("Thanh toán khi nhận hàng");
                        tvMoTaThanhToan.setText("COD");
                        tvMoTaThanhToanChiTiet.setText("COD");

                    } else if (which == 1) {
                        phuongThucThanhToan = "BANK";
                        tvTenThanhToan.setText("Chuyển khoản ngân hàng");
                        tvMoTaThanhToan.setText("BANK");
                        tvMoTaThanhToanChiTiet.setText("BANK");

                    } else {
                        phuongThucThanhToan = "WALLET";
                        tvTenThanhToan.setText("Ví điện tử");
                        tvMoTaThanhToan.setText("MOMO");
                        tvMoTaThanhToanChiTiet.setText("MOMO");
                    }

                })
                .show();
    }
}