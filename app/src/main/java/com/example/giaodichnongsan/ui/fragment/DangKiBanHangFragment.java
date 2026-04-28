package com.example.giaodichnongsan.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

    ImageView imgGiayATTP, imgGiayVietGap;
    Button btnChonAnhATTP, btnChonAnhVietGap;

    Uri uriGiayATTP = null;
    Uri uriGiayVietGap = null;

    private static final int REQUEST_ATTP = 101;
    private static final int REQUEST_VIETGAP = 102;

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

        imgGiayATTP = view.findViewById(R.id.imgGiayATTP);
        imgGiayVietGap = view.findViewById(R.id.imgGiayVietGap);
        btnChonAnhATTP = view.findViewById(R.id.btnChonAnhATTP);
        btnChonAnhVietGap = view.findViewById(R.id.btnChonAnhVietGap);

        btnBack.setOnClickListener(v ->
                requireActivity().getSupportFragmentManager().popBackStack()
        );

        btnMenu.setOnClickListener(v ->
                Toast.makeText(requireContext(), "Menu đăng ký bán hàng", Toast.LENGTH_SHORT).show()
        );

        btnChonAnhATTP.setOnClickListener(v -> chonAnh(REQUEST_ATTP));
        btnChonAnhVietGap.setOnClickListener(v -> chonAnh(REQUEST_VIETGAP));

        btnGuiYeuCau.setOnClickListener(v -> guiYeuCauDangKy());

        return view;
    }

    private void chonAnh(int requestCode) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(Intent.createChooser(intent, "Chọn ảnh"), requestCode);
        } catch (Exception e) {
            Toast.makeText(requireContext(), "Không mở được trình chọn ảnh", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();

            if (requestCode == REQUEST_ATTP) {
                uriGiayATTP = uri;
                imgGiayATTP.setImageURI(uri);
            } else if (requestCode == REQUEST_VIETGAP) {
                uriGiayVietGap = uri;
                imgGiayVietGap.setImageURI(uri);
            }
        }
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

        if (TextUtils.isEmpty(hoTen)) {
            edtHoTen.setError("Vui lòng nhập họ tên");
            edtHoTen.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(soDienThoai)) {
            edtSoDienThoai.setError("Vui lòng nhập số điện thoại");
            edtSoDienThoai.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(cccd)) {
            edtCCCD.setError("Vui lòng nhập số CCCD");
            edtCCCD.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(diaChiCuTru)) {
            edtDiaChiCuTru.setError("Vui lòng nhập địa chỉ cư trú");
            edtDiaChiCuTru.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(tenGianHang)) {
            edtTenGianHang.setError("Vui lòng nhập tên gian hàng");
            edtTenGianHang.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(diaChiKinhDoanh)) {
            edtDiaChiKinhDoanh.setError("Vui lòng nhập địa chỉ kinh doanh");
            edtDiaChiKinhDoanh.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(loaiNongSan)) {
            edtLoaiNongSan.setError("Vui lòng nhập loại nông sản");
            edtLoaiNongSan.requestFocus();
            return;
        }

        if (uriGiayATTP == null) {
            Toast.makeText(requireContext(), "Vui lòng chọn ảnh giấy chứng nhận ATTP", Toast.LENGTH_SHORT).show();
            return;
        }

        if (uriGiayVietGap == null) {
            Toast.makeText(requireContext(), "Vui lòng chọn ảnh giấy chứng nhận VietGAP", Toast.LENGTH_SHORT).show();
            return;
        }

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
                uriGiayATTP.toString(),
                uriGiayVietGap.toString(),
                soTaiKhoan,
                tenChuTaiKhoan,
                "CHO_DUYET"
        );

        repository.submitSellerRequest(requireContext(), request);

        requireActivity()
                .getSharedPreferences("USER", requireActivity().MODE_PRIVATE)
                .edit()
                .putBoolean("isSeller", false)
                .putBoolean("hasSellerRequest", true)
                .apply();

        Toast.makeText(requireContext(), "Đã gửi yêu cầu, vui lòng chờ duyệt", Toast.LENGTH_SHORT).show();

        requireActivity().getSupportFragmentManager().popBackStack();
    }

    private String getText(EditText editText) {
        return editText.getText().toString().trim();
    }
}