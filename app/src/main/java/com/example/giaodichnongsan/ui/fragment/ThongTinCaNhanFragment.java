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

import com.example.giaodichnongsan.R;

public class ThongTinCaNhanFragment extends Fragment {

    private ImageView imgAvatar, btnBack;
    private TextView tvDoiAnh;
    private EditText edtHoTen, edtSoDienThoai, edtEmail, edtDiaChi;
    private Button btnLuu;

    // Dữ liệu fake tạm (sau thay bằng ViewModel + Firebase)
    private String hoTen = "Nguyễn Văn A";
    private String soDienThoai = "0123456789";
    private String email = "test@gmail.com";
    private String diaChi = "";

    public ThongTinCaNhanFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_thong_tin_ca_nhan, container, false);

        initView(view);
        loadData();
        setupEvents();

        return view;
    }

    private void initView(View view) {
        btnBack        = view.findViewById(R.id.btnBack);
        imgAvatar      = view.findViewById(R.id.imgAvatar);
        tvDoiAnh       = view.findViewById(R.id.tvDoiAnh);
        edtHoTen       = view.findViewById(R.id.edtHoTen);
        edtSoDienThoai = view.findViewById(R.id.edtSoDienThoai);
        edtEmail       = view.findViewById(R.id.edtEmail);
        edtDiaChi      = view.findViewById(R.id.edtDiaChi);
        btnLuu         = view.findViewById(R.id.btnLuu);
    }

    // Load dữ liệu lên form
    private void loadData() {
        edtHoTen.setText(hoTen);
        edtSoDienThoai.setText(soDienThoai);
        edtEmail.setText(email);
        edtDiaChi.setText(diaChi);
    }

    private void setupEvents() {

        btnBack.setOnClickListener(v ->
                requireActivity().getSupportFragmentManager().popBackStack()
        );

        tvDoiAnh.setOnClickListener(v -> {
            // Sau này mở gallery để chọn ảnh
            Toast.makeText(getContext(), "Chức năng đổi ảnh (coming soon)", Toast.LENGTH_SHORT).show();
        });

        btnLuu.setOnClickListener(v -> xuLyLuu());
    }

    private void xuLyLuu() {

        String ten  = edtHoTen.getText().toString().trim();
        String sdt  = edtSoDienThoai.getText().toString().trim();
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

        // Fake lưu tạm (sau thay bằng Firestore update)
        // FirebaseFirestore.getInstance()
        //     .collection("users").document(uid)
        //     .update("hoTen", ten, "soDienThoai", sdt, "diaChi", diaChi)

        Toast.makeText(getContext(), "Đã lưu thông tin", Toast.LENGTH_SHORT).show();
        requireActivity().getSupportFragmentManager().popBackStack();
    }
}