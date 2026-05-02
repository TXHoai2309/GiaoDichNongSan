package com.example.giaodichnongsan.ui.fragment;

import android.app.AlertDialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.giaodichnongsan.R;
import com.example.giaodichnongsan.data.repository.SellerRegistrationRepository;
import com.example.giaodichnongsan.model.SellerRegistrationRequest;

public class ChiTietDangKyBanHangFragment extends Fragment {

    private static final String KEY_REQUEST = "seller_request";

    private SellerRegistrationRequest request;
    private ImageView btnBack;
    private LinearLayout layoutNoiDung;
    private Button btnPheDuyet, btnTuChoi;
    private SellerRegistrationRepository repository;

    public ChiTietDangKyBanHangFragment() {}

    public static ChiTietDangKyBanHangFragment newInstance(SellerRegistrationRequest request) {
        ChiTietDangKyBanHangFragment fragment = new ChiTietDangKyBanHangFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_REQUEST, request);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            request = (SellerRegistrationRequest) getArguments().getSerializable(KEY_REQUEST);
        }
        repository = new SellerRegistrationRepository();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chi_tiet_quan_ly_dky_ban_hang, container, false);

        btnBack = view.findViewById(R.id.btnBack);
        layoutNoiDung = view.findViewById(R.id.layoutNoiDung);
        btnPheDuyet = view.findViewById(R.id.btnPheDuyet);
        btnTuChoi = view.findViewById(R.id.btnTuChoi);

        btnBack.setOnClickListener(v -> requireActivity().onBackPressed());
        hienThiDuLieu();
        suKien();

        return view;
    }

    private void hienThiDuLieu() {
        if (request == null) {
            themDong("Lỗi", "Không tìm thấy dữ liệu yêu cầu");
            btnPheDuyet.setEnabled(false);
            btnTuChoi.setEnabled(false);
            return;
        }

        themMuc("1. Thông tin cá nhân");
        themDong("Họ và tên", request.getHoTen());
        themDong("Số điện thoại", request.getSoDienThoai());
        themDong("Email", request.getEmail());
        themDong("CCCD", request.getCccd());
        themDong("Địa chỉ cư trú", request.getDiaChiCuTru());

        themMuc("2. Thông tin gian hàng");
        themDong("Tên gian hàng", request.getTenGianHang());
        themDong("Mô tả", request.getMoTaGianHang());
        themDong("Địa chỉ kinh doanh", request.getDiaChiKinhDoanh());
        themDong("Loại nông sản", request.getLoaiNongSan());
        themDong("Nguồn gốc", request.getNguonGocSanPham());

        themMuc("3. Giấy chứng nhận");
        themAnh("ATTP", request.getGiayChungNhanATTP());
        themAnh("VietGAP / hữu cơ", request.getGiayChungNhanVietGap());

        themMuc("4. Thanh toán");
        themDong("Số tài khoản", request.getSoTaiKhoan());
        themDong("Tên chủ tài khoản", request.getTenChuTaiKhoan());
        themDong("Trạng thái", request.getTrangThai());
    }

    private void suKien() {
        btnPheDuyet.setOnClickListener(v ->
                new AlertDialog.Builder(requireContext())
                        .setTitle("Phê duyệt yêu cầu?")
                        .setMessage("Người dùng sẽ được đánh dấu là người bán.")
                        .setNegativeButton("Hủy", null)
                        .setPositiveButton("Phê duyệt", (dialog, which) -> pheDuyet())
                        .show()
        );

        btnTuChoi.setOnClickListener(v ->
                new AlertDialog.Builder(requireContext())
                        .setTitle("Từ chối yêu cầu?")
                        .setMessage("Yêu cầu này sẽ chuyển sang trạng thái từ chối.")
                        .setNegativeButton("Hủy", null)
                        .setPositiveButton("Từ chối", (dialog, which) -> tuChoi())
                        .show()
        );
    }

    private void pheDuyet() {
        setLoading(true);
        repository.approveRequest(request, new SellerRegistrationRepository.OnSubmitListener() {
            @Override
            public void onSuccess() {
                if (!isAdded()) return;
                Toast.makeText(requireContext(), "Đã phê duyệt yêu cầu", Toast.LENGTH_SHORT).show();
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

    private void tuChoi() {
        setLoading(true);
        repository.rejectRequest(request.getId(), new SellerRegistrationRepository.OnSubmitListener() {
            @Override
            public void onSuccess() {
                if (!isAdded()) return;
                Toast.makeText(requireContext(), "Đã từ chối yêu cầu", Toast.LENGTH_SHORT).show();
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

    private void setLoading(boolean loading) {
        btnPheDuyet.setEnabled(!loading);
        btnTuChoi.setEnabled(!loading);
        btnPheDuyet.setText(loading ? "Đang xử lý..." : "Phê duyệt");
    }

    private void themMuc(String text) {
        TextView title = new TextView(requireContext());
        title.setText(text);
        title.setTextColor(android.graphics.Color.parseColor("#4A423D"));
        title.setTextSize(16);
        title.setTypeface(null, android.graphics.Typeface.BOLD);
        title.setPadding(0, dp(18), 0, dp(8));
        layoutNoiDung.addView(title);
    }

    private void themDong(String label, String value) {
        TextView tv = new TextView(requireContext());
        tv.setText(label + ": " + (value == null || value.isEmpty() ? "Chưa có" : value));
        tv.setTextColor(android.graphics.Color.parseColor("#555555"));
        tv.setTextSize(14);
        tv.setPadding(dp(12), dp(10), dp(12), dp(10));
        tv.setBackgroundColor(android.graphics.Color.parseColor("#FFF8F4"));

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, 0, dp(1));
        layoutNoiDung.addView(tv, params);
    }

    private void themAnh(String label, String uriText) {
        themDong(label, uriText == null || uriText.isEmpty() ? "Chưa chọn ảnh" : "Đã chọn ảnh");
        if (uriText == null || uriText.isEmpty()) return;

        ImageView img = new ImageView(requireContext());
        img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        img.setBackgroundColor(android.graphics.Color.parseColor("#E9E3DF"));
        img.setImageURI(Uri.parse(uriText));

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                dp(150)
        );
        params.setMargins(0, 0, 0, dp(8));
        layoutNoiDung.addView(img, params);
    }

    private int dp(int value) {
        return (int) (value * getResources().getDisplayMetrics().density);
    }
}
