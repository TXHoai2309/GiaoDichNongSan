package com.example.giaodichnongsan.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ThanhToanFragment extends Fragment {

    private RecyclerView rvThanhToan;
    private TextView tvTongTien, tvTienHang;
    private EditText edtTenNguoiNhan, edtSdtNguoiNhan, edtDiaChiGiao, edtGhiChu;
    private Button btnDatHang;

    private ThanhToanAdapter adapter;
    private GioHangViewModel gioHangViewModel;
    private DonHangViewModel donHangViewModel;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thanhtoan, container, false);

        initView(view);
        initData();
        initViewModel();
        setupRecyclerView();
        setupUI();
        loadThongTinNguoiDung();
        setupEvent();

        return view;
    }

    private void initView(View view) {
        rvThanhToan = view.findViewById(R.id.rvThanhToan);
        tvTongTien = view.findViewById(R.id.tvTong);
        tvTienHang = view.findViewById(R.id.tvTienHang);
        edtTenNguoiNhan = view.findViewById(R.id.edtTenNguoiNhan);
        edtSdtNguoiNhan = view.findViewById(R.id.edtSdtNguoiNhan);
        edtDiaChiGiao = view.findViewById(R.id.edtDiaChiGiao);
        edtGhiChu = view.findViewById(R.id.edtGhiChu);
        btnDatHang = view.findViewById(R.id.btnDatHang);
    }

    private void initData() {
        if (getArguments() != null) {
            listMua = (ArrayList<GioHangItem>) getArguments().getSerializable("list");
        }
        if (listMua == null) {
            listMua = new ArrayList<>();
        }
    }

    private void initViewModel() {
        gioHangViewModel = new ViewModelProvider(requireActivity()).get(GioHangViewModel.class);
        donHangViewModel = new ViewModelProvider(requireActivity()).get(DonHangViewModel.class);

        donHangViewModel.getDatHangSuccess().observe(getViewLifecycleOwner(), success -> {
            if (success == null) return;

            if (success) {
                gioHangViewModel.clearCart();
                startActivity(new Intent(getActivity(), DatHangThanhCongActivity.class));
                requireActivity().getSupportFragmentManager()
                        .popBackStack(null, androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE);
            } else {
                Toast.makeText(getContext(), "Đặt hàng thất bại, vui lòng thử lại", Toast.LENGTH_SHORT).show();
                btnDatHang.setEnabled(true);
            }
        });
    }

    private void setupRecyclerView() {
        rvThanhToan.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ThanhToanAdapter(listMua);
        rvThanhToan.setAdapter(adapter);
    }

    private void setupUI() {
        int tongTien = tinhTongTien(listMua);
        tvTienHang.setText(formatGia(tongTien));
        tvTongTien.setText(formatGia(tongTien));
    }

    private void loadThongTinNguoiDung() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) return;

        FirebaseFirestore.getInstance().collection("users").document(user.getUid())
                .get()
                .addOnSuccessListener(doc -> {
                    if (!isAdded() || !doc.exists()) return;
                    setTextIfEmpty(edtTenNguoiNhan,
                            firstNonEmpty(doc.getString("hoTen"), doc.getString("ten"), doc.getString("name")));
                    setTextIfEmpty(edtSdtNguoiNhan,
                            firstNonEmpty(doc.getString("soDienThoai"), doc.getString("sdt"), doc.getString("phone")));
                    setTextIfEmpty(edtDiaChiGiao,
                            firstNonEmpty(doc.getString("diaChi"), doc.getString("diaChiGiao"), doc.getString("address")));
                });
    }

    private void setupEvent() {
        btnDatHang.setOnClickListener(v -> {
            if (listMua.isEmpty()) {
                Toast.makeText(getContext(), "Giỏ hàng trống", Toast.LENGTH_SHORT).show();
                return;
            }

            String tenNguoiNhan = text(edtTenNguoiNhan);
            String sdtNguoiNhan = text(edtSdtNguoiNhan);
            String diaChiGiao = text(edtDiaChiGiao);
            String ghiChu = text(edtGhiChu);

            if (tenNguoiNhan.isEmpty()) {
                edtTenNguoiNhan.setError("Vui lòng nhập họ tên người nhận");
                edtTenNguoiNhan.requestFocus();
                return;
            }
            if (sdtNguoiNhan.isEmpty()) {
                edtSdtNguoiNhan.setError("Vui lòng nhập số điện thoại");
                edtSdtNguoiNhan.requestFocus();
                return;
            }
            if (diaChiGiao.isEmpty()) {
                edtDiaChiGiao.setError("Vui lòng nhập địa chỉ nhận hàng");
                edtDiaChiGiao.requestFocus();
                return;
            }

            btnDatHang.setEnabled(false);
            int tongTien = tinhTongTien(listMua);
            donHangViewModel.addDonHang(new ArrayList<>(listMua), tongTien,
                    tenNguoiNhan, sdtNguoiNhan, diaChiGiao, ghiChu);
        });
    }

    private int tinhTongTien(ArrayList<GioHangItem> list) {
        int total = 0;
        for (GioHangItem item : list) {
            if (item != null && item.getSanPham() != null) {
                total += item.getSanPham().getGia() * item.getSoLuong();
            }
        }
        return total;
    }

    private String text(EditText editText) {
        return editText.getText().toString().trim();
    }

    private void setTextIfEmpty(EditText editText, String value) {
        if (editText.getText().toString().trim().isEmpty() && value != null && !value.trim().isEmpty()) {
            editText.setText(value.trim());
        }
    }

    private String firstNonEmpty(String... values) {
        for (String value : values) {
            if (value != null && !value.trim().isEmpty()) return value.trim();
        }
        return "";
    }

    private String formatGia(int gia) {
        return String.format("%,dđ", gia);
    }
}
