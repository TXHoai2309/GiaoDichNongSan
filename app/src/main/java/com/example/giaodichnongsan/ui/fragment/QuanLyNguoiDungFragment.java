package com.example.giaodichnongsan.ui.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
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
import com.example.giaodichnongsan.model.User;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class QuanLyNguoiDungFragment extends Fragment {

    private static final String FILTER_ALL = "Tất cả";
    private static final String FILTER_BUYER = "Người mua";
    private static final String FILTER_SELLER = "Người bán";
    private static final String FILTER_LOCKED = "Đã khóa";

    private ImageView btnBack;
    private EditText edtSearch;
    private Button btnLoc, btnSua, btnXoa;
    private LinearLayout layoutListUser;

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final List<User> danhSachNguoiDung = new ArrayList<>();
    private final List<User> danhSachDangHienThi = new ArrayList<>();

    private User nguoiDungDangChon;
    private String boLocHienTai = FILTER_ALL;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.quan_ly_nguoi_dung_fragment, container, false);

        anhXa(view);
        suKien();
        taiDanhSachNguoiDung();

        return view;
    }

    private void anhXa(View view) {
        btnBack = view.findViewById(R.id.btnBack);
        edtSearch = view.findViewById(R.id.edtSearch);
        btnLoc = view.findViewById(R.id.btnLoc);
        btnSua = view.findViewById(R.id.btnSua);
        btnXoa = view.findViewById(R.id.btnXoa);
        layoutListUser = view.findViewById(R.id.layoutListUser);
    }

    private void suKien() {
        btnBack.setOnClickListener(v -> requireActivity().onBackPressed());
        btnLoc.setOnClickListener(v -> hienDialogLoc());
        btnSua.setOnClickListener(v -> {
            if (nguoiDungDangChon == null) {
                Toast.makeText(getContext(), "Chọn người dùng cần sửa", Toast.LENGTH_SHORT).show();
                return;
            }
            hienDialogSua(nguoiDungDangChon);
        });
        btnXoa.setOnClickListener(v -> {
            if (nguoiDungDangChon == null) {
                Toast.makeText(getContext(), "Chọn người dùng cần xóa", Toast.LENGTH_SHORT).show();
                return;
            }
            xacNhanXoa(nguoiDungDangChon);
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                apDungTimKiemVaLoc();
            }
        });
    }

    private void taiDanhSachNguoiDung() {
        layoutListUser.removeAllViews();
        themDongThongBao("Đang tải người dùng...");

        db.collection("users")
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    danhSachNguoiDung.clear();
                    for (com.google.firebase.firestore.DocumentSnapshot doc : querySnapshot.getDocuments()) {
                        User user = doc.toObject(User.class);
                        if (user == null) continue;
                        if (TextUtils.isEmpty(user.getUid())) {
                            user.setUid(doc.getId());
                        }
                        danhSachNguoiDung.add(user);
                    }
                    apDungTimKiemVaLoc();
                })
                .addOnFailureListener(e -> {
                    layoutListUser.removeAllViews();
                    themDongThongBao("Không tải được danh sách người dùng");
                    Toast.makeText(getContext(), "Lỗi: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }

    private void apDungTimKiemVaLoc() {
        String tuKhoa = edtSearch.getText().toString().trim().toLowerCase(Locale.ROOT);
        danhSachDangHienThi.clear();
        nguoiDungDangChon = null;

        for (User user : danhSachNguoiDung) {
            if (!khopBoLoc(user)) continue;
            if (!khopTuKhoa(user, tuKhoa)) continue;
            danhSachDangHienThi.add(user);
        }

        hienThiDanhSach(danhSachDangHienThi);
    }

    private boolean khopBoLoc(User user) {
        if (FILTER_SELLER.equals(boLocHienTai)) return user.isSeller();
        if (FILTER_BUYER.equals(boLocHienTai)) return !user.isSeller();
        if (FILTER_LOCKED.equals(boLocHienTai)) return user.isBiKhoa();
        return true;
    }

    private boolean khopTuKhoa(User user, String tuKhoa) {
        if (tuKhoa.isEmpty()) return true;

        return chua(user.getUid(), tuKhoa)
                || chua(user.getHoTen(), tuKhoa)
                || chua(user.getEmail(), tuKhoa)
                || chua(user.getSoDienThoai(), tuKhoa)
                || chua(user.getDiaChi(), tuKhoa)
                || chua(user.isSeller() ? "người bán seller" : "người mua buyer", tuKhoa)
                || chua(user.isBiKhoa() ? "đã khóa khoa" : "hoạt động hoat dong", tuKhoa);
    }

    private boolean chua(String value, String tuKhoa) {
        return value != null && value.toLowerCase(Locale.ROOT).contains(tuKhoa);
    }

    private void hienThiDanhSach(List<User> ds) {
        layoutListUser.removeAllViews();

        if (ds.isEmpty()) {
            themDongThongBao("Không có người dùng phù hợp");
            return;
        }

        for (User user : ds) {
            layoutListUser.addView(taoDongNguoiDung(user));
        }
    }

    private View taoDongNguoiDung(User user) {
        LinearLayout dong = new LinearLayout(getContext());
        dong.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        dong.setOrientation(LinearLayout.HORIZONTAL);
        dong.setPadding(6, 14, 6, 14);
        dong.setBackgroundColor(user == nguoiDungDangChon ? 0xFFE9F4E4 : 0xFFFFFFFF);
        dong.setGravity(Gravity.CENTER_VERTICAL);

        TextView tvId = taoText(dpToPx(44), rutGonId(user.getUid()), 12, 0xFF2F2F2F);
        TextView tvTen = taoTextWeight(tenHienThi(user), 12, 0xFF2F2F2F);
        TextView tvEmail = taoTextWeight(emailHienThi(user), 12, 0xFF555555);

        TextView tvTrangThai = new TextView(getContext());
        tvTrangThai.setLayoutParams(new LinearLayout.LayoutParams(dpToPx(78), ViewGroup.LayoutParams.WRAP_CONTENT));
        tvTrangThai.setText(user.isBiKhoa() ? "Đã khóa" : (user.isSeller() ? "Người bán" : "Hoạt động"));
        tvTrangThai.setTextSize(11);
        tvTrangThai.setGravity(Gravity.CENTER);
        tvTrangThai.setPadding(6, 6, 6, 6);
        tvTrangThai.setSingleLine(true);

        if (user.isBiKhoa()) {
            tvTrangThai.setBackgroundColor(0xFFF8E3DE);
            tvTrangThai.setTextColor(0xFFC05A4A);
        } else if (user.isSeller()) {
            tvTrangThai.setBackgroundColor(0xFFE2EFF8);
            tvTrangThai.setTextColor(0xFF3F6D8A);
        } else {
            tvTrangThai.setBackgroundColor(0xFFE9F4E4);
            tvTrangThai.setTextColor(0xFF4E8A3F);
        }

        dong.addView(tvId);
        dong.addView(tvTen);
        dong.addView(tvEmail);
        dong.addView(tvTrangThai);

        dong.setOnClickListener(v -> {
            nguoiDungDangChon = user;
            hienThiDanhSach(danhSachDangHienThi);
            Toast.makeText(getContext(), "Đã chọn: " + tenHienThi(user), Toast.LENGTH_SHORT).show();
        });

        dong.setOnLongClickListener(v -> {
            hienThiHopThoaiNguoiDung(user);
            return true;
        });

        return dong;
    }

    private TextView taoText(int width, String text, int size, int color) {
        TextView tv = new TextView(getContext());
        tv.setLayoutParams(new LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT));
        tv.setText(text);
        tv.setTextSize(size);
        tv.setTextColor(color);
        tv.setSingleLine(true);
        return tv;
    }

    private TextView taoTextWeight(String text, int size, int color) {
        TextView tv = new TextView(getContext());
        tv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        tv.setText(text);
        tv.setTextSize(size);
        tv.setTextColor(color);
        tv.setSingleLine(true);
        tv.setEllipsize(TextUtils.TruncateAt.END);
        return tv;
    }

    private void themDongThongBao(String noiDung) {
        TextView tv = new TextView(getContext());
        tv.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        tv.setGravity(Gravity.CENTER);
        tv.setPadding(12, 28, 12, 28);
        tv.setText(noiDung);
        tv.setTextColor(0xFF777777);
        tv.setTextSize(14);
        layoutListUser.addView(tv);
    }

    private void hienDialogLoc() {
        String[] luaChon = {FILTER_ALL, FILTER_BUYER, FILTER_SELLER, FILTER_LOCKED};
        int checked = 0;
        for (int i = 0; i < luaChon.length; i++) {
            if (luaChon[i].equals(boLocHienTai)) checked = i;
        }

        new AlertDialog.Builder(requireContext())
                .setTitle("Lọc người dùng")
                .setSingleChoiceItems(luaChon, checked, (dialog, which) -> {
                    boLocHienTai = luaChon[which];
                    btnLoc.setText(boLocHienTai.equals(FILTER_ALL) ? "Lọc" : boLocHienTai);
                    apDungTimKiemVaLoc();
                    dialog.dismiss();
                })
                .show();
    }

    private void hienThiHopThoaiNguoiDung(User user) {
        String khoaText = user.isBiKhoa() ? "Mở khóa" : "Khóa";
        String[] luaChon = {"Xem thông tin", "Sửa", khoaText, "Xóa"};

        new AlertDialog.Builder(requireContext())
                .setTitle(tenHienThi(user))
                .setItems(luaChon, (dialog, which) -> {
                    if (which == 0) hienDialogThongTin(user);
                    else if (which == 1) hienDialogSua(user);
                    else if (which == 2) capNhatTrangThaiKhoa(user, !user.isBiKhoa());
                    else xacNhanXoa(user);
                })
                .show();
    }

    private void hienDialogThongTin(User user) {
        String thongTin = "UID: " + giaTri(user.getUid())
                + "\nHọ tên: " + tenHienThi(user)
                + "\nEmail: " + emailHienThi(user)
                + "\nSố điện thoại: " + giaTri(user.getSoDienThoai())
                + "\nĐịa chỉ: " + giaTri(user.getDiaChi())
                + "\nVai trò: " + (user.isSeller() ? "Người bán" : "Người mua")
                + "\nShop ID: " + giaTri(user.getShopId())
                + "\nTrạng thái: " + (user.isBiKhoa() ? "Đã khóa" : "Hoạt động")
                + "\nNgày tạo: " + dinhDangNgay(user.getNgayTao());

        new AlertDialog.Builder(requireContext())
                .setTitle("Thông tin người dùng")
                .setMessage(thongTin)
                .setPositiveButton("Đóng", null)
                .show();
    }

    private void hienDialogSua(User user) {
        LinearLayout form = new LinearLayout(requireContext());
        form.setOrientation(LinearLayout.VERTICAL);
        int padding = dpToPx(18);
        form.setPadding(padding, padding / 2, padding, 0);

        EditText edtTen = taoEditText("Họ tên", user.getHoTen());
        EditText edtSdt = taoEditText("Số điện thoại", user.getSoDienThoai());
        EditText edtDiaChi = taoEditText("Địa chỉ", user.getDiaChi());

        form.addView(edtTen);
        form.addView(edtSdt);
        form.addView(edtDiaChi);

        AlertDialog dialog = new AlertDialog.Builder(requireContext())
                .setTitle("Sửa người dùng")
                .setView(form)
                .setNegativeButton("Hủy", null)
                .setPositiveButton("Lưu", null)
                .create();

        dialog.setOnShowListener(d -> dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            String ten = edtTen.getText().toString().trim();
            String sdt = edtSdt.getText().toString().trim();
            String diaChi = edtDiaChi.getText().toString().trim();

            if (ten.isEmpty()) {
                edtTen.setError("Nhập họ tên");
                return;
            }

            Map<String, Object> data = new HashMap<>();
            data.put("hoTen", ten);
            data.put("soDienThoai", sdt);
            data.put("diaChi", diaChi);

            db.collection("users").document(user.getUid())
                    .update(data)
                    .addOnSuccessListener(unused -> {
                        user.setHoTen(ten);
                        user.setSoDienThoai(sdt);
                        user.setDiaChi(diaChi);
                        Toast.makeText(getContext(), "Đã cập nhật người dùng", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        apDungTimKiemVaLoc();
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(getContext(), "Lỗi: " + e.getMessage(), Toast.LENGTH_LONG).show());
        }));

        dialog.show();
    }

    private EditText taoEditText(String hint, String value) {
        EditText editText = new EditText(requireContext());
        editText.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        editText.setHint(hint);
        editText.setText(value == null ? "" : value);
        editText.setSingleLine(true);
        return editText;
    }

    private void capNhatTrangThaiKhoa(User user, boolean khoa) {
        db.collection("users").document(user.getUid())
                .update("biKhoa", khoa)
                .addOnSuccessListener(unused -> {
                    user.setBiKhoa(khoa);
                    Toast.makeText(getContext(), khoa ? "Đã khóa người dùng" : "Đã mở khóa người dùng", Toast.LENGTH_SHORT).show();
                    apDungTimKiemVaLoc();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(getContext(), "Lỗi: " + e.getMessage(), Toast.LENGTH_LONG).show());
    }

    private void xacNhanXoa(User user) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Xóa người dùng")
                .setMessage("Xóa hồ sơ Firestore của " + tenHienThi(user) + "? Tài khoản Firebase Auth không bị xóa từ ứng dụng client.")
                .setNegativeButton("Hủy", null)
                .setPositiveButton("Xóa", (dialog, which) -> xoaNguoiDung(user))
                .show();
    }

    private void xoaNguoiDung(User user) {
        db.collection("users").document(user.getUid())
                .delete()
                .addOnSuccessListener(unused -> {
                    danhSachNguoiDung.remove(user);
                    if (nguoiDungDangChon == user) nguoiDungDangChon = null;
                    Toast.makeText(getContext(), "Đã xóa hồ sơ người dùng", Toast.LENGTH_SHORT).show();
                    apDungTimKiemVaLoc();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(getContext(), "Lỗi: " + e.getMessage(), Toast.LENGTH_LONG).show());
    }

    private String tenHienThi(User user) {
        return TextUtils.isEmpty(user.getHoTen()) ? "Chưa cập nhật" : user.getHoTen();
    }

    private String emailHienThi(User user) {
        return TextUtils.isEmpty(user.getEmail()) ? "Chưa có email" : user.getEmail();
    }

    private String giaTri(String value) {
        return TextUtils.isEmpty(value) ? "Chưa cập nhật" : value;
    }

    private String rutGonId(String uid) {
        if (TextUtils.isEmpty(uid)) return "---";
        return uid.length() <= 5 ? uid : uid.substring(0, 5);
    }

    private String dinhDangNgay(long millis) {
        if (millis <= 0) return "Chưa cập nhật";
        return new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(new Date(millis));
    }

    private int dpToPx(int dp) {
        float density = requireContext().getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
}
