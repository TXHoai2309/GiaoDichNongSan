package com.example.giaodichnongsan.ui.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import com.example.giaodichnongsan.R;
import com.example.giaodichnongsan.data.repository.SellerRegistrationRepository;
import com.example.giaodichnongsan.model.SellerRegistrationRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class DangKiBanHangFragment extends Fragment {

    private ImageView btnBack, btnMenu, imgGiayATTP, imgGiayVietGap;
    private Button btnChonAnhATTP, btnChonAnhVietGap;
    private LinearLayout btnGuiYeuCau;
    private TextView tvGuiYeuCau;

    private EditText edtHoTen, edtSoDienThoai, edtEmail, edtCCCD, edtDiaChiCuTru;
    private EditText edtTenGianHang, edtMoTaGianHang, edtDiaChiKinhDoanh;
    private EditText edtLoaiNongSan, edtNguonGocSanPham;
    private EditText edtSoTaiKhoan, edtTenChuTaiKhoan;

    private Uri uriGiayATTP;
    private Uri uriGiayVietGap;
    private SellerRegistrationRepository repository;

    private final ActivityResultLauncher<String> chonAnhATTP =
            registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
                if (uri != null) {
                    uriGiayATTP = uri;
                    imgGiayATTP.setImageURI(uri);
                }
            });

    private final ActivityResultLauncher<String> chonAnhVietGap =
            registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
                if (uri != null) {
                    uriGiayVietGap = uri;
                    imgGiayVietGap.setImageURI(uri);
                }
            });

    public DangKiBanHangFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dang_ky_ban_hang, container, false);
        repository = new SellerRegistrationRepository();

        initView(view);
        setupEvent();
        loadThongTinNguoiDung();

        return view;
    }

    private void initView(View view) {
        btnBack = view.findViewById(R.id.btnBackDangKyBanHang);
        btnMenu = view.findViewById(R.id.btnMenuDangKyBanHang);
        btnGuiYeuCau = view.findViewById(R.id.btnGuiYeuCau);
        tvGuiYeuCau = view.findViewById(R.id.tvGuiYeuCau);

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
    }

    private void setupEvent() {
        btnBack.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());

        btnMenu.setOnClickListener(v ->
                Toast.makeText(requireContext(), "Hồ sơ sẽ được gửi cho quản trị viên xét duyệt", Toast.LENGTH_SHORT).show()
        );

        btnChonAnhATTP.setOnClickListener(v -> chonAnhATTP.launch("image/*"));
        btnChonAnhVietGap.setOnClickListener(v -> chonAnhVietGap.launch("image/*"));
        btnGuiYeuCau.setOnClickListener(v -> guiYeuCauDangKy());
    }

    private void loadThongTinNguoiDung() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser == null) return;

        if (firebaseUser.getEmail() != null) {
            edtEmail.setText(firebaseUser.getEmail());
        }

        FirebaseFirestore.getInstance()
                .collection("users")
                .document(firebaseUser.getUid())
                .get()
                .addOnSuccessListener(snapshot -> {
                    if (!isAdded() || !snapshot.exists()) return;

                    setTextIfEmpty(edtHoTen, snapshot.getString("hoTen"));
                    setTextIfEmpty(edtSoDienThoai, snapshot.getString("soDienThoai"));
                    setTextIfEmpty(edtEmail, snapshot.getString("email"));
                    setTextIfEmpty(edtDiaChiCuTru, snapshot.getString("diaChi"));
                });
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

        if (!validate(hoTen, soDienThoai, email, cccd, diaChiCuTru,
                tenGianHang, diaChiKinhDoanh, loaiNongSan, nguonGocSanPham,
                soTaiKhoan, tenChuTaiKhoan)) {
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
                uriGiayATTP != null ? uriGiayATTP.toString() : "",
                uriGiayVietGap != null ? uriGiayVietGap.toString() : "",
                soTaiKhoan,
                tenChuTaiKhoan,
                "CHO_DUYET"
        );
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            request.setUserId(firebaseUser.getUid());
        }

        setLoading(true);
        repository.submitSellerRequest(request, new SellerRegistrationRepository.OnSubmitListener() {
            @Override
            public void onSuccess() {
                if (!isAdded()) return;
                setLoading(false);
                Toast.makeText(requireContext(), "Đã gửi yêu cầu xét duyệt", Toast.LENGTH_SHORT).show();
                requireActivity().getSupportFragmentManager().popBackStack();
            }

            @Override
            public void onFailure(String error) {
                if (!isAdded()) return;
                setLoading(false);
                Toast.makeText(requireContext(), "Lỗi: " + error, Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean validate(String hoTen, String soDienThoai, String email, String cccd,
                             String diaChiCuTru, String tenGianHang, String diaChiKinhDoanh,
                             String loaiNongSan, String nguonGocSanPham,
                             String soTaiKhoan, String tenChuTaiKhoan) {
        if (isEmpty(edtHoTen, hoTen, "Vui lòng nhập họ tên")) return false;
        if (isEmpty(edtSoDienThoai, soDienThoai, "Vui lòng nhập số điện thoại")) return false;
        if (soDienThoai.length() < 9 || soDienThoai.length() > 11) {
            edtSoDienThoai.setError("Số điện thoại không hợp lệ");
            edtSoDienThoai.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtEmail.setError("Email không hợp lệ");
            edtEmail.requestFocus();
            return false;
        }

        if (isEmpty(edtCCCD, cccd, "Vui lòng nhập CCCD")) return false;
        if (cccd.length() != 12) {
            edtCCCD.setError("CCCD phải gồm 12 số");
            edtCCCD.requestFocus();
            return false;
        }

        if (isEmpty(edtDiaChiCuTru, diaChiCuTru, "Vui lòng nhập địa chỉ cư trú")) return false;
        if (isEmpty(edtTenGianHang, tenGianHang, "Vui lòng nhập tên gian hàng")) return false;
        if (isEmpty(edtDiaChiKinhDoanh, diaChiKinhDoanh, "Vui lòng nhập địa chỉ kinh doanh")) return false;
        if (isEmpty(edtLoaiNongSan, loaiNongSan, "Vui lòng nhập loại nông sản")) return false;
        if (isEmpty(edtNguonGocSanPham, nguonGocSanPham, "Vui lòng nhập nguồn gốc sản phẩm")) return false;
        if (isEmpty(edtSoTaiKhoan, soTaiKhoan, "Vui lòng nhập số tài khoản")) return false;
        if (isEmpty(edtTenChuTaiKhoan, tenChuTaiKhoan, "Vui lòng nhập tên chủ tài khoản")) return false;

        return true;
    }

    private boolean isEmpty(EditText editText, String value, String error) {
        if (TextUtils.isEmpty(value)) {
            editText.setError(error);
            editText.requestFocus();
            return true;
        }
        return false;
    }

    private void setTextIfEmpty(EditText editText, String value) {
        if (TextUtils.isEmpty(editText.getText()) && !TextUtils.isEmpty(value)) {
            editText.setText(value);
        }
    }

    private void setLoading(boolean loading) {
        btnGuiYeuCau.setEnabled(!loading);
        tvGuiYeuCau.setText(loading ? "Đang gửi..." : "Gửi yêu cầu xét duyệt");
    }

    private String getText(EditText edt) {
        return edt.getText().toString().trim();
    }
}
