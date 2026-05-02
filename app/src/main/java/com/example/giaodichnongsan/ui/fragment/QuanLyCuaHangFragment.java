package com.example.giaodichnongsan.ui.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.giaodichnongsan.R;
import com.example.giaodichnongsan.model.DonHang;
import com.example.giaodichnongsan.model.GioHangItem;
import com.example.giaodichnongsan.model.SanPham;
import com.example.giaodichnongsan.model.SellerRegistrationRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class QuanLyCuaHangFragment extends Fragment {

    private ImageView btnBack, btnMenu;
    private TextView tvTenShop, tvTrangThaiShop, tvMoTaShop;
    private TextView tvSoSanPham, tvDonChoXuLy, tvDoanhThu, tvCanhBao;
    private LinearLayout itemThongTinShop, itemQuanLySanPham, itemQuanLyDonHang;
    private LinearLayout itemThongKeDoanhThu, itemCaiDatShop, itemTrungTamTroGiup;

    private FirebaseFirestore db;
    private SellerRegistrationRequest shopRequest;
    private String requestId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quan_ly_cua_hang, container, false);

        db = FirebaseFirestore.getInstance();
        initView(view);
        setupEvent();
        loadShopDaDuyet();

        return view;
    }

    private void initView(View view) {
        btnBack = view.findViewById(R.id.btnBackQuanLyShop);
        btnMenu = view.findViewById(R.id.btnMenuQuanLyShop);

        tvTenShop = view.findViewById(R.id.tvTenShop);
        tvTrangThaiShop = view.findViewById(R.id.tvTrangThaiShop);
        tvMoTaShop = view.findViewById(R.id.tvMoTaShop);
        tvSoSanPham = view.findViewById(R.id.tvSoSanPham);
        tvDonChoXuLy = view.findViewById(R.id.tvDonChoXuLy);
        tvDoanhThu = view.findViewById(R.id.tvDoanhThu);
        tvCanhBao = view.findViewById(R.id.tvCanhBao);

        itemThongTinShop = view.findViewById(R.id.itemThongTinShop);
        itemQuanLySanPham = view.findViewById(R.id.itemQuanLySanPham);
        itemQuanLyDonHang = view.findViewById(R.id.itemQuanLyDonHang);
        itemThongKeDoanhThu = view.findViewById(R.id.itemThongKeDoanhThu);
        itemCaiDatShop = view.findViewById(R.id.itemCaiDatShop);
        itemTrungTamTroGiup = view.findViewById(R.id.itemTrungTamTroGiup);
    }

    private void setupEvent() {
        btnBack.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());
        btnMenu.setOnClickListener(v -> hienMenuTong());

        itemThongTinShop.setOnClickListener(v -> hienThongTinShop());
        itemQuanLySanPham.setOnClickListener(v -> moQuanLySanPham());
        itemQuanLyDonHang.setOnClickListener(v -> moQuanLyDonHang());
        itemThongKeDoanhThu.setOnClickListener(v -> moQuanLyDoanhThu());
        itemCaiDatShop.setOnClickListener(v -> caiDatShop());
        itemTrungTamTroGiup.setOnClickListener(v -> hienThongBao("Trung tâm trợ giúp", "Liên hệ quản trị viên để được hỗ trợ người bán."));
    }

    private void loadShopDaDuyet() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            hienKhongCoShop();
            return;
        }

        tvTenShop.setText("Đang tải cửa hàng...");
        tvTrangThaiShop.setText("Đang kiểm tra");

        db.collection("seller_requests")
                .whereEqualTo("userId", user.getUid())
                .whereEqualTo("trangThai", "DA_DUYET")
                .limit(1)
                .get()
                .addOnSuccessListener(snapshot -> {
                    if (!isAdded()) return;

                    if (snapshot.isEmpty()) {
                        loadShopTheoEmail(user.getEmail());
                        return;
                    }

                    requestId = snapshot.getDocuments().get(0).getId();
                    shopRequest = snapshot.getDocuments().get(0).toObject(SellerRegistrationRequest.class);
                    if (shopRequest != null) shopRequest.setId(requestId);
                    hienThiShop();
                })
                .addOnFailureListener(e -> {
                    if (!isAdded()) return;
                    Toast.makeText(requireContext(), "Lỗi tải cửa hàng: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    hienKhongCoShop();
                });
    }

    private void loadShopTheoEmail(String email) {
        if (TextUtils.isEmpty(email)) {
            hienKhongCoShop();
            return;
        }

        db.collection("seller_requests")
                .whereEqualTo("email", email)
                .whereEqualTo("trangThai", "DA_DUYET")
                .limit(1)
                .get()
                .addOnSuccessListener(snapshot -> {
                    if (!isAdded()) return;

                    if (snapshot.isEmpty()) {
                        hienKhongCoShop();
                        return;
                    }

                    requestId = snapshot.getDocuments().get(0).getId();
                    shopRequest = snapshot.getDocuments().get(0).toObject(SellerRegistrationRequest.class);
                    if (shopRequest != null) shopRequest.setId(requestId);
                    hienThiShop();
                })
                .addOnFailureListener(e -> hienKhongCoShop());
    }

    private void hienThiShop() {
        if (shopRequest == null) {
            hienKhongCoShop();
            return;
        }

        tvCanhBao.setVisibility(View.GONE);
        tvTenShop.setText(giaTri(shopRequest.getTenGianHang(), "Cửa hàng của tôi"));
        tvTrangThaiShop.setText("Đã phê duyệt");
        tvMoTaShop.setText(giaTri(shopRequest.getMoTaGianHang(), "Chưa có mô tả gian hàng"));

        tvSoSanPham.setText("0\nSản phẩm");
        tvDonChoXuLy.setText("0\nChờ xử lý");
        tvDoanhThu.setText("0đ\nDoanh thu");
        loadSoLuongSanPham();
        loadThongKeDonHang();
    }

    private void hienKhongCoShop() {
        shopRequest = null;
        tvTenShop.setText("Chưa có cửa hàng");
        tvTrangThaiShop.setText("Chưa được phê duyệt");
        tvMoTaShop.setText("Sau khi admin phê duyệt yêu cầu đăng ký bán hàng, thông tin cửa hàng sẽ hiển thị tại đây.");
        tvCanhBao.setVisibility(View.VISIBLE);
    }

    private void loadSoLuongSanPham() {
        if (TextUtils.isEmpty(requestId)) return;

        db.collection("sanpham")
                .whereEqualTo("shopId", requestId)
                .get()
                .addOnSuccessListener(snapshot -> {
                    if (!isAdded()) return;
                    tvSoSanPham.setText(snapshot.size() + "\nSản phẩm");
                });
    }

    private void moQuanLySanPham() {
        if (shopRequest == null || TextUtils.isEmpty(requestId)) {
            hienThongBao("Quản lý sản phẩm", "Không tìm thấy cửa hàng đã được phê duyệt.");
            return;
        }

        getParentFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, QuanLySanPhamFragment.newInstance(requestId, shopRequest.getTenGianHang()))
                .addToBackStack(null)
                .commit();
    }

    private void moQuanLyDonHang() {
        if (shopRequest == null || TextUtils.isEmpty(requestId)) {
            hienThongBao("Quản lý đơn hàng", "Không tìm thấy cửa hàng đã được phê duyệt.");
            return;
        }

        getParentFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, QuanLyDonHangFragment.newInstance(requestId, shopRequest.getTenGianHang()))
                .addToBackStack(null)
                .commit();
    }

    private void moQuanLyDoanhThu() {
        if (shopRequest == null || TextUtils.isEmpty(requestId)) {
            hienThongBao("Thống kê doanh thu", "Không tìm thấy cửa hàng đã được phê duyệt.");
            return;
        }

        getParentFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, QuanLyDoanhThuFragment.newInstance(requestId, shopRequest.getTenGianHang()))
                .addToBackStack(null)
                .commit();
    }

    private void loadThongKeDonHang() {
        if (TextUtils.isEmpty(requestId)) return;

        db.collection("donhang")
                .get()
                .addOnSuccessListener(snapshot -> {
                    if (!isAdded()) return;
                    int choXuLy = 0;
                    int doanhThu = 0;

                    for (com.google.firebase.firestore.DocumentSnapshot doc : snapshot.getDocuments()) {
                        DonHang donHang = doc.toObject(DonHang.class);
                        if (donHang == null || donHang.getDanhSachSP() == null) continue;

                        int tienShop = tinhTienShop(donHang);
                        if (tienShop <= 0) continue;

                        if (DonHang.DANG_GIAO.equals(donHang.getTrangThai())) {
                            choXuLy++;
                        } else if (DonHang.DA_GIAO.equals(donHang.getTrangThai())) {
                            doanhThu += tienShop;
                        }
                    }

                    tvDonChoXuLy.setText(choXuLy + "\nChờ xử lý");
                    tvDoanhThu.setText(formatGia(doanhThu) + "\nDoanh thu");
                });
    }

    private int tinhTienShop(DonHang donHang) {
        int total = 0;
        if (donHang.getDanhSachSP() == null) return total;

        for (GioHangItem item : donHang.getDanhSachSP()) {
            if (item == null) continue;
            SanPham sp = item.getSanPham();
            if (sp != null && requestId.equals(sp.getShopId())) {
                total += sp.getGia() * item.getSoLuong();
            }
        }
        return total;
    }

    private void hienMenuTong() {
        String[] options = {"Xem thông tin cửa hàng", "Sửa tên cửa hàng", "Sửa mô tả", "Cài đặt cửa hàng"};
        new AlertDialog.Builder(requireContext())
                .setTitle("Quản lý bán hàng")
                .setItems(options, (dialog, which) -> {
                    if (which == 0) hienThongTinShop();
                    else if (which == 1) suaField("Tên cửa hàng", "tenGianHang", shopRequest != null ? shopRequest.getTenGianHang() : "");
                    else if (which == 2) suaField("Mô tả cửa hàng", "moTaGianHang", shopRequest != null ? shopRequest.getMoTaGianHang() : "");
                    else caiDatShop();
                })
                .show();
    }

    private void hienThongTinShop() {
        if (shopRequest == null) {
            hienThongBao("Thông tin cửa hàng", "Không tìm thấy cửa hàng đã được phê duyệt.");
            return;
        }

        String info = "Tên cửa hàng: " + giaTri(shopRequest.getTenGianHang(), "Chưa có")
                + "\nChủ cửa hàng: " + giaTri(shopRequest.getHoTen(), "Chưa có")
                + "\nSố điện thoại: " + giaTri(shopRequest.getSoDienThoai(), "Chưa có")
                + "\nEmail: " + giaTri(shopRequest.getEmail(), "Chưa có")
                + "\nĐịa chỉ kinh doanh: " + giaTri(shopRequest.getDiaChiKinhDoanh(), "Chưa có")
                + "\nLoại nông sản: " + giaTri(shopRequest.getLoaiNongSan(), "Chưa có")
                + "\nNguồn gốc: " + giaTri(shopRequest.getNguonGocSanPham(), "Chưa có")
                + "\nMô tả: " + giaTri(shopRequest.getMoTaGianHang(), "Chưa có");

        hienThongBao("Thông tin cửa hàng", info);
    }

    private void caiDatShop() {
        if (shopRequest == null) {
            hienThongBao("Cài đặt cửa hàng", "Không tìm thấy cửa hàng đã được phê duyệt.");
            return;
        }

        String[] options = {"Sửa tên cửa hàng", "Sửa số điện thoại", "Sửa địa chỉ kinh doanh", "Sửa mô tả"};
        new AlertDialog.Builder(requireContext())
                .setTitle("Cài đặt cửa hàng")
                .setItems(options, (dialog, which) -> {
                    if (which == 0) suaField("Tên cửa hàng", "tenGianHang", shopRequest.getTenGianHang());
                    else if (which == 1) suaField("Số điện thoại", "soDienThoai", shopRequest.getSoDienThoai());
                    else if (which == 2) suaField("Địa chỉ kinh doanh", "diaChiKinhDoanh", shopRequest.getDiaChiKinhDoanh());
                    else suaField("Mô tả cửa hàng", "moTaGianHang", shopRequest.getMoTaGianHang());
                })
                .show();
    }

    private void suaField(String title, String field, String currentValue) {
        if (TextUtils.isEmpty(requestId)) {
            hienThongBao(title, "Không tìm thấy dữ liệu cửa hàng để cập nhật.");
            return;
        }

        EditText input = new EditText(requireContext());
        input.setText(currentValue == null ? "" : currentValue);
        input.setHint(title);
        input.setSingleLine(false);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);

        new AlertDialog.Builder(requireContext())
                .setTitle(title)
                .setView(input)
                .setNegativeButton("Hủy", null)
                .setPositiveButton("Lưu", (dialog, which) -> {
                    String value = input.getText().toString().trim();
                    if (value.isEmpty()) {
                        Toast.makeText(getContext(), "Không được để trống", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    capNhatField(field, value);
                })
                .show();
    }

    private void capNhatField(String field, String value) {
        Map<String, Object> data = new HashMap<>();
        data.put(field, value);

        db.collection("seller_requests")
                .document(requestId)
                .update(data)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(requireContext(), "Đã cập nhật cửa hàng", Toast.LENGTH_SHORT).show();
                    loadShopDaDuyet();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(requireContext(), "Lỗi cập nhật: " + e.getMessage(), Toast.LENGTH_LONG).show()
                );
    }

    private void hienThongBao(String title, String message) {
        new AlertDialog.Builder(requireContext())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Đóng", null)
                .show();
    }

    private String giaTri(String value, String fallback) {
        return value == null || value.trim().isEmpty() ? fallback : value.trim();
    }

    private String formatGia(int gia) {
        return NumberFormat.getNumberInstance(new Locale("vi", "VN")).format(gia) + "đ";
    }
}
