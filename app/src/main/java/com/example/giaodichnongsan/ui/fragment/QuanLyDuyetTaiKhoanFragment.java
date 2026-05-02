package com.example.giaodichnongsan.ui.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.giaodichnongsan.R;
import com.example.giaodichnongsan.data.repository.SellerRegistrationRepository;
import com.example.giaodichnongsan.model.SellerRegistrationRequest;

import java.util.ArrayList;

public class QuanLyDuyetTaiKhoanFragment extends Fragment {

    private ImageView btnBack;
    private EditText edtSearchDuyetTaiKhoan;
    private TextView tvSoLuongChoDuyet, tvEmpty;
    private LinearLayout layoutDanhSachYeuCau;

    private final ArrayList<SellerRegistrationRequest> allRequests = new ArrayList<>();
    private SellerRegistrationRepository repository;

    public QuanLyDuyetTaiKhoanFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quan_ly_duyet_tai_khoan, container, false);

        repository = new SellerRegistrationRepository();
        anhXa(view);
        suKien();
        loadYeuCau();

        return view;
    }

    private void anhXa(View view) {
        btnBack = view.findViewById(R.id.btnBack);
        edtSearchDuyetTaiKhoan = view.findViewById(R.id.edtSearchDuyetTaiKhoan);
        tvSoLuongChoDuyet = view.findViewById(R.id.tvSoLuongChoDuyet);
        tvEmpty = view.findViewById(R.id.tvEmpty);
        layoutDanhSachYeuCau = view.findViewById(R.id.layoutDanhSachYeuCau);
    }

    private void suKien() {
        btnBack.setOnClickListener(v -> requireActivity().onBackPressed());

        edtSearchDuyetTaiKhoan.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                hienThiDanhSach(s.toString().trim().toLowerCase());
            }
        });
    }

    private void loadYeuCau() {
        tvSoLuongChoDuyet.setText("Đang tải yêu cầu...");
        repository.getPendingRequests(new SellerRegistrationRepository.OnListListener() {
            @Override
            public void onSuccess(ArrayList<SellerRegistrationRequest> list) {
                if (!isAdded()) return;
                allRequests.clear();
                allRequests.addAll(list);
                hienThiDanhSach(edtSearchDuyetTaiKhoan.getText().toString().trim().toLowerCase());
            }

            @Override
            public void onFailure(String error) {
                if (!isAdded()) return;
                tvSoLuongChoDuyet.setText("Không tải được yêu cầu");
                Toast.makeText(requireContext(), "Lỗi: " + error, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void hienThiDanhSach(String tuKhoa) {
        layoutDanhSachYeuCau.removeAllViews();

        int count = 0;
        for (SellerRegistrationRequest request : allRequests) {
            if (!khopTimKiem(request, tuKhoa)) continue;

            layoutDanhSachYeuCau.addView(taoItemYeuCau(request));
            count++;
        }

        tvSoLuongChoDuyet.setText(count + " yêu cầu chờ duyệt");
        tvEmpty.setVisibility(count == 0 ? View.VISIBLE : View.GONE);
    }

    private boolean khopTimKiem(SellerRegistrationRequest request, String tuKhoa) {
        if (tuKhoa.isEmpty()) return true;

        String noiDung = (request.getHoTen() + " "
                + request.getSoDienThoai() + " "
                + request.getEmail() + " "
                + request.getTenGianHang()).toLowerCase();
        return noiDung.contains(tuKhoa);
    }

    private View taoItemYeuCau(SellerRegistrationRequest request) {
        LinearLayout item = new LinearLayout(requireContext());
        item.setOrientation(LinearLayout.HORIZONTAL);
        item.setGravity(android.view.Gravity.CENTER_VERTICAL);
        item.setPadding(dp(12), dp(12), dp(12), dp(12));
        item.setBackgroundColor(android.graphics.Color.parseColor("#FFF8F4"));

        LinearLayout.LayoutParams itemParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        itemParams.setMargins(0, 0, 0, dp(10));
        item.setLayoutParams(itemParams);

        ImageView icon = new ImageView(requireContext());
        icon.setImageResource(android.R.drawable.ic_menu_myplaces);
        item.addView(icon, new LinearLayout.LayoutParams(dp(36), dp(36)));

        LinearLayout content = new LinearLayout(requireContext());
        content.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams contentParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        contentParams.setMargins(dp(12), 0, dp(8), 0);

        TextView tvTen = new TextView(requireContext());
        tvTen.setText(request.getHoTen());
        tvTen.setTextColor(android.graphics.Color.parseColor("#4A423D"));
        tvTen.setTextSize(16);
        tvTen.setTypeface(null, android.graphics.Typeface.BOLD);

        TextView tvInfo = new TextView(requireContext());
        tvInfo.setText(request.getTenGianHang() + "\n" + request.getSoDienThoai());
        tvInfo.setTextColor(android.graphics.Color.parseColor("#6E625B"));
        tvInfo.setTextSize(13);

        content.addView(tvTen);
        content.addView(tvInfo);
        item.addView(content, contentParams);

        Button btnXem = new Button(requireContext());
        btnXem.setText("Xem");
        btnXem.setTextColor(android.graphics.Color.WHITE);
        btnXem.setTextSize(12);
        btnXem.setAllCaps(false);
        btnXem.setBackgroundTintList(android.content.res.ColorStateList.valueOf(
                android.graphics.Color.parseColor("#68A25A")
        ));
        item.addView(btnXem, new LinearLayout.LayoutParams(dp(74), dp(40)));

        View.OnClickListener openDetail = v -> moChiTiet(request);
        item.setOnClickListener(openDetail);
        btnXem.setOnClickListener(openDetail);

        return item;
    }

    private void moChiTiet(SellerRegistrationRequest request) {
        ChiTietDangKyBanHangFragment fragment = ChiTietDangKyBanHangFragment.newInstance(request);
        getParentFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .addToBackStack(null)
                .commit();
    }

    private int dp(int value) {
        return (int) (value * getResources().getDisplayMetrics().density);
    }
}
