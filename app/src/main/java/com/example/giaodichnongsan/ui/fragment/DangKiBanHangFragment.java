package com.example.giaodichnongsan.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.giaodichnongsan.R;
import com.example.giaodichnongsan.data.repository.SellerRegistrationRepository;
import com.example.giaodichnongsan.model.SellerRegistrationRequest;

public class DangKiBanHangFragment extends Fragment {

    ImageView btnBack, btnMenu;
    LinearLayout btnGuiYeuCau;

    EditText edtHoTen, edtSoDienThoai, edtEmail, edtCCCD, edtDiaChiCuTru;
    EditText edtTenGianHang, edtMoTaGianHang, edtDiaChiKinhDoanh;
    EditText edtLoaiNongSan, edtNguonGocSanPham;
    EditText edtSoTaiKhoan, edtTenChuTaiKhoan;

    private SellerRegistrationRepository repository;

    public DangKiBanHangFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dang_ky_ban_hang, container, false);

        repository = new SellerRegistrationRepository();

        btnBack = view.findViewById(R.id.btnBackDangKyBanHang);
        btnMenu = view.findViewById(R.id.btnMenuDangKyBanHang);
        btnGuiYeuCau = view.findViewById(R.id.btnGuiYeuCau);

        edtHoTen = view.findViewById(R.id.edtHoTen);
        edtSoDienThoai = view.findViewById(R.id.edtSoDienThoai);
        edtEmail = view.findViewById(R.id.edtEmail);
        edtCCCD = view.findViewById(R.id.edtCCCD);
        edtDiaChiCuTru = view.findViewById(R.id.edtDiaChiCuTru);

        edtTenGianHang = view.findViewById(R.id.edtTenGianHang);
        edtMoTaGianHang = view.findViewById(R.id.edtMoTaGianHang);
        edtDiaChiKinhDoanh = view.findViewById(R.id.edtDiaChiKinhDoanh);
        edtLoaiNongSan = view.findViewById(R.id.edtLoaiNongSan);
        edtNguonGocSanPham = view.findViewById(R.id.edtNguonGocSanPham);

        edtSoTaiKhoan = view.findViewById(R.id.edtSoTaiKhoan);
        edtTenChuTaiKhoan = view.findViewById(R.id.edtTenChuTaiKhoan);

        btnBack.setOnClickListener(v ->
                requireActivity().getSupportFragmentManager().popBackStack()
        );

        btnMenu.setOnClickListener(v ->
                Toast.makeText(requireContext(), "Menu đăng ký bán hàng", Toast.LENGTH_SHORT).show()
        );

        btnGuiYeuCau.setOnClickListener(v -> guiYeuCauDangKy());

        return view;
    }

    private void guiYeuCauDangKy() {
        String hoTen = getText(edtHoTen);
        String soDienThoai = getText(edtSoDienThoai);
        String email = getText(edtEmail);
        String cccd = getText(edtCCCD);
        String diaChiCuTru = getText(edtDiaChiCuTru);

        String tenGianHang = getText(edtTenGianHang);
        String moTaGianHang = getText(edtMoTaGianHang);
        String diaChiKinhDoanh = getText(edtDiaChiKinhDoanh);
        String loaiNongSan = getText(edtLoaiNongSan);
        String nguonGocSanPham = getText(edtNguonGocSanPham);

        String soTaiKhoan = getText(edtSoTaiKhoan);
        String tenChuTaiKhoan = getText(edtTenChuTaiKhoan);

        // VALIDATE
        if (TextUtils.isEmpty(hoTen)) {
            edtHoTen.setError("Vui lòng nhập họ tên");
            return;
        }

        if (TextUtils.isEmpty(soDienThoai)) {
            edtSoDienThoai.setError("Vui lòng nhập số điện thoại");
            return;
        }

        if (TextUtils.isEmpty(cccd)) {
            edtCCCD.setError("Vui lòng nhập CCCD");
            return;
        }

        if (TextUtils.isEmpty(tenGianHang)) {
            edtTenGianHang.setError("Vui lòng nhập tên gian hàng");
            return;
        }

        if (TextUtils.isEmpty(diaChiKinhDoanh)) {
            edtDiaChiKinhDoanh.setError("Vui lòng nhập địa chỉ kinh doanh");
            return;
        }

        if (TextUtils.isEmpty(loaiNongSan)) {
            edtLoaiNongSan.setError("Vui lòng nhập loại nông sản");
            return;
        }

        // TẠO REQUEST (KHÔNG DÙNG ẢNH)
        SellerRegistrationRequest request = new SellerRegistrationRequest(
                hoTen,
                soDienThoai,
                email,
                cccd,
                diaChiCuTru,
                tenGianHang,
                moTaGianHang,
                diaChiKinhDoanh,
                loaiNongSan,
                nguonGocSanPham,
                "CHUA_UPLOAD_ANH",
                "CHUA_UPLOAD_ANH",
                soTaiKhoan,
                tenChuTaiKhoan,
                "CHO_DUYET"
        );

        // GỬI FIREBASE
        repository.submitSellerRequest(request, new SellerRegistrationRepository.OnSubmitListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(requireContext(), "Đã gửi yêu cầu", Toast.LENGTH_SHORT).show();

                requireActivity().getSupportFragmentManager().popBackStack();
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(requireContext(), "Lỗi: " + error, Toast.LENGTH_LONG).show();
            }
        });
    }

    private String getText(EditText edt) {
        return edt.getText().toString().trim();
    }
}