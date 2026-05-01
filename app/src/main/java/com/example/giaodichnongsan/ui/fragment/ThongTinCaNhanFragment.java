package com.example.giaodichnongsan.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.giaodichnongsan.R;
import com.example.giaodichnongsan.viewmodel.AuthViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ThongTinCaNhanFragment extends Fragment {

    // ===== VIEW =====
    private ImageView imgAvatar, btnBack;
    private TextView tvDoiAnh, tvNgayTao;
    private EditText edtHoTen, edtSoDienThoai, edtEmail, edtDiaChi;
    private Button btnLuu;

    // ===== VIEWMODEL =====
    private AuthViewModel authViewModel;

    public ThongTinCaNhanFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_thong_tin_ca_nhan, container, false);

        initView(view);
        initViewModel();
        setupEvents();

        return view;
    }

    // ===== INIT VIEW =====
    private void initView(View view) {
        btnBack        = view.findViewById(R.id.btnBack);
        imgAvatar      = view.findViewById(R.id.imgAvatar);
        tvDoiAnh       = view.findViewById(R.id.tvDoiAnh);
        tvNgayTao      = view.findViewById(R.id.tvNgayTao);
        edtHoTen       = view.findViewById(R.id.edtHoTen);
        edtSoDienThoai = view.findViewById(R.id.edtSoDienThoai);
        edtEmail       = view.findViewById(R.id.edtEmail);
        edtDiaChi      = view.findViewById(R.id.edtDiaChi);
        btnLuu         = view.findViewById(R.id.btnLuu);

        // Email không cho sửa
        edtEmail.setEnabled(false);
        edtEmail.setAlpha(0.6f);
    }

    // ===== INIT VIEWMODEL =====
    private void initViewModel() {
        authViewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);

        // Load user từ Firestore
        authViewModel.loadCurrentUser();

        // Observe và điền vào form
        authViewModel.getCurrentUser().observe(getViewLifecycleOwner(), user -> {
            if (user == null) return;

            edtHoTen.setText(user.getHoTen());
            edtSoDienThoai.setText(user.getSoDienThoai());
            edtEmail.setText(user.getEmail());
            edtDiaChi.setText(user.getDiaChi() != null ? user.getDiaChi() : "");

            // Hiển thị ngày tham gia
            if (user.getNgayTao() > 0) {
                String ngayHienThi = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                        .format(new Date(user.getNgayTao()));
                tvNgayTao.setText(ngayHienThi);
            } else {
                tvNgayTao.setText("Đang cập nhật");
            }
        });
    }

    // ===== SETUP EVENTS =====
    private void setupEvents() {

        btnBack.setOnClickListener(v ->
                requireActivity().getSupportFragmentManager().popBackStack()
        );

        tvDoiAnh.setOnClickListener(v ->
                Toast.makeText(getContext(), "Chức năng đổi ảnh (coming soon)", Toast.LENGTH_SHORT).show()
        );

        btnLuu.setOnClickListener(v -> xuLyLuu());
    }

    // ===== LƯU THÔNG TIN =====
    private void xuLyLuu() {

        String ten    = edtHoTen.getText().toString().trim();
        String sdt    = edtSoDienThoai.getText().toString().trim();
        String diaChi = edtDiaChi.getText().toString().trim();

        // Validate
        if (TextUtils.isEmpty(ten)) {
            edtHoTen.setError("Vui lòng nhập họ tên");
            return;
        }

        if (sdt.length() < 9) {
            edtSoDienThoai.setError("Số điện thoại không hợp lệ");
            return;
        }

        // Kiểm tra đã đăng nhập chưa
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            Toast.makeText(getContext(), "Bạn chưa đăng nhập", Toast.LENGTH_SHORT).show();
            return;
        }

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Disable nút tránh bấm nhiều lần
        btnLuu.setEnabled(false);

        // Lưu lên Firestore
        FirebaseFirestore.getInstance()
                .collection("users")
                .document(uid)
                .update(
                        "hoTen", ten,
                        "soDienThoai", sdt,
                        "diaChi", diaChi
                )
                .addOnSuccessListener(v -> {
                    btnLuu.setEnabled(true);
                    Toast.makeText(getContext(), "Đã lưu thông tin", Toast.LENGTH_SHORT).show();
                    requireActivity().getSupportFragmentManager().popBackStack();
                })
                .addOnFailureListener(e -> {
                    btnLuu.setEnabled(true);
                    Toast.makeText(getContext(), "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}