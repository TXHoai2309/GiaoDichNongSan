package com.example.giaodichnongsan.ui.fragment;

import android.app.AlertDialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
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

import com.bumptech.glide.Glide;
import com.example.giaodichnongsan.R;
import com.example.giaodichnongsan.model.DonHang;
import com.example.giaodichnongsan.model.GioHangItem;
import com.example.giaodichnongsan.model.SanPham;
import com.example.giaodichnongsan.utils.ImageUrlHelper;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class QuanLyDonHangFragment extends Fragment {
    private static final String ARG_SHOP_ID = "shop_id";
    private static final String ARG_TEN_SHOP = "ten_shop";

    private final String[] filters = {"Tất cả", DonHang.DANG_GIAO, DonHang.DA_GIAO, DonHang.DA_HUY};
    private final ArrayList<DonHang> allOrders = new ArrayList<>();

    private ImageView btnBack;
    private LinearLayout layoutBoLoc, layoutDanhSachDon;
    private TextView tvTongDon, tvTongTien, tvEmpty;
    private FirebaseFirestore db;
    private String shopId;
    private String tenShop;
    private String currentFilter = "Tất cả";

    public static QuanLyDonHangFragment newInstance(String shopId, String tenShop) {
        QuanLyDonHangFragment fragment = new QuanLyDonHangFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SHOP_ID, shopId);
        args.putString(ARG_TEN_SHOP, tenShop);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            shopId = getArguments().getString(ARG_SHOP_ID, "");
            tenShop = getArguments().getString(ARG_TEN_SHOP, "");
        }
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quan_ly_don_hang, container, false);
        btnBack = view.findViewById(R.id.btnBack);
        layoutBoLoc = view.findViewById(R.id.layoutBoLoc);
        layoutDanhSachDon = view.findViewById(R.id.layoutDanhSachDon);
        tvTongDon = view.findViewById(R.id.tvTongDon);
        tvTongTien = view.findViewById(R.id.tvTongTien);
        tvEmpty = view.findViewById(R.id.tvEmpty);

        btnBack.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());
        taoBoLoc();
        loadDonHang();
        return view;
    }

    private void taoBoLoc() {
        layoutBoLoc.removeAllViews();
        for (String filter : filters) {
            Button button = new Button(requireContext());
            button.setText(filter);
            button.setAllCaps(false);
            button.setTextSize(13);
            button.setPadding(dp(14), 0, dp(14), 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, dp(40));
            params.setMargins(0, 0, dp(8), 0);
            layoutBoLoc.addView(button, params);
            button.setOnClickListener(v -> {
                currentFilter = filter;
                taoBoLoc();
                hienThiDanhSach();
            });
            styleFilterButton(button, filter.equals(currentFilter));
        }
    }

    private void styleFilterButton(Button button, boolean selected) {
        button.setTextColor(selected ? Color.WHITE : Color.parseColor("#4A423D"));
        button.setBackgroundTintList(ColorStateList.valueOf(
                selected ? Color.parseColor("#6D55B3") : Color.parseColor("#FFF8F4")));
    }

    private void loadDonHang() {
        if (TextUtils.isEmpty(shopId)) {
            tvEmpty.setVisibility(View.VISIBLE);
            tvEmpty.setText("Không tìm thấy cửa hàng");
            return;
        }

        tvTongDon.setText("Đang tải đơn...");
        db.collection("donhang")
                .orderBy("ngayDatMillis", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(snapshot -> {
                    if (!isAdded()) return;
                    allOrders.clear();
                    for (com.google.firebase.firestore.DocumentSnapshot doc : snapshot.getDocuments()) {
                        DonHang donHang = doc.toObject(DonHang.class);
                        if (donHang != null && coSanPhamCuaShop(donHang)) {
                            donHang.setId(doc.getId());
                            allOrders.add(donHang);
                        }
                    }
                    hienThiDanhSach();
                })
                .addOnFailureListener(e -> {
                    if (!isAdded()) return;
                    tvTongDon.setText("0 đơn");
                    Toast.makeText(requireContext(), "Lỗi tải đơn hàng: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }

    private void hienThiDanhSach() {
        layoutDanhSachDon.removeAllViews();
        int count = 0;
        int doanhThu = 0;

        for (DonHang donHang : allOrders) {
            if (!"Tất cả".equals(currentFilter) && !currentFilter.equals(donHang.getTrangThai())) continue;
            layoutDanhSachDon.addView(taoItemDonHang(donHang));
            count++;
            if (DonHang.DA_GIAO.equals(donHang.getTrangThai())) {
                doanhThu += tinhTienShop(donHang);
            }
        }

        tvTongDon.setText(count + " đơn");
        tvTongTien.setText("Đã giao: " + formatGia(doanhThu));
        tvEmpty.setVisibility(count == 0 ? View.VISIBLE : View.GONE);
    }

    private View taoItemDonHang(DonHang donHang) {
        LinearLayout card = new LinearLayout(requireContext());
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(dp(14), dp(12), dp(14), dp(12));
        card.setBackgroundColor(Color.parseColor("#FFF8F4"));
        LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        cardParams.setMargins(0, 0, 0, dp(10));
        card.setLayoutParams(cardParams);

        LinearLayout header = new LinearLayout(requireContext());
        header.setGravity(android.view.Gravity.CENTER_VERTICAL);
        header.setOrientation(LinearLayout.HORIZONTAL);

        TextView tvMaDon = new TextView(requireContext());
        tvMaDon.setText("Đơn #" + maNgan(donHang.getId()));
        tvMaDon.setTextColor(Color.parseColor("#4A423D"));
        tvMaDon.setTextSize(15);
        tvMaDon.setTypeface(null, Typeface.BOLD);
        header.addView(tvMaDon, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));

        TextView tvTrangThai = new TextView(requireContext());
        tvTrangThai.setText(giaTri(donHang.getTrangThai(), "Chưa rõ"));
        tvTrangThai.setTextColor(mauTrangThai(donHang.getTrangThai()));
        tvTrangThai.setTextSize(13);
        tvTrangThai.setTypeface(null, Typeface.BOLD);
        header.addView(tvTrangThai);
        card.addView(header);

        TextView tvKhach = new TextView(requireContext());
        tvKhach.setText("Khách: " + giaTri(donHang.getTenNguoiMua(), donHang.getUserId())
                + "\nNgày đặt: " + giaTri(donHang.getNgayDat(), "Chưa có"));
        tvKhach.setTextColor(Color.parseColor("#6E625B"));
        tvKhach.setTextSize(13);
        tvKhach.setPadding(0, dp(6), 0, dp(8));
        card.addView(tvKhach);

        for (GioHangItem item : laySanPhamCuaShop(donHang)) {
            card.addView(taoDongSanPham(item));
        }

        TextView tvTong = new TextView(requireContext());
        tvTong.setText("Tổng của shop: " + formatGia(tinhTienShop(donHang)));
        tvTong.setTextColor(Color.parseColor("#FF5722"));
        tvTong.setTextSize(15);
        tvTong.setTypeface(null, Typeface.BOLD);
        tvTong.setPadding(0, dp(8), 0, 0);
        card.addView(tvTong);

        LinearLayout actions = new LinearLayout(requireContext());
        actions.setGravity(android.view.Gravity.END);
        actions.setPadding(0, dp(10), 0, 0);

        Button btnChiTiet = taoButton("Chi tiết", "#6D55B3");
        Button btnCapNhat = taoButton("Cập nhật", "#68A25A");
        actions.addView(btnChiTiet, new LinearLayout.LayoutParams(dp(92), dp(40)));
        LinearLayout.LayoutParams updateParams = new LinearLayout.LayoutParams(dp(104), dp(40));
        updateParams.setMargins(dp(8), 0, 0, 0);
        actions.addView(btnCapNhat, updateParams);
        card.addView(actions);

        btnChiTiet.setOnClickListener(v -> hienChiTiet(donHang));
        btnCapNhat.setOnClickListener(v -> chonTrangThai(donHang));
        btnCapNhat.setEnabled(!DonHang.DA_GIAO.equals(donHang.getTrangThai()) && !DonHang.DA_HUY.equals(donHang.getTrangThai()));
        btnCapNhat.setAlpha(btnCapNhat.isEnabled() ? 1f : 0.45f);
        return card;
    }

    private View taoDongSanPham(GioHangItem item) {
        LinearLayout row = new LinearLayout(requireContext());
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setGravity(android.view.Gravity.CENTER_VERTICAL);
        row.setPadding(0, dp(6), 0, dp(6));

        ImageView img = new ImageView(requireContext());
        img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        SanPham sp = item.getSanPham();
        String imageUrl = sp == null ? "" : ImageUrlHelper.normalize(sp.getImageUrl());
        if (!imageUrl.isEmpty()) {
            Glide.with(this).load(imageUrl).placeholder(R.drawable.ic_product).error(R.drawable.ic_product).into(img);
        } else {
            img.setImageResource(R.drawable.ic_product);
        }
        row.addView(img, new LinearLayout.LayoutParams(dp(48), dp(48)));

        TextView tvInfo = new TextView(requireContext());
        String ten = sp == null ? "Sản phẩm" : giaTri(sp.getTen(), "Sản phẩm");
        int gia = sp == null ? 0 : sp.getGia();
        tvInfo.setText(ten + "\n" + item.getSoLuong() + " x " + formatGia(gia));
        tvInfo.setTextColor(Color.parseColor("#4A423D"));
        tvInfo.setTextSize(13);
        LinearLayout.LayoutParams infoParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        infoParams.setMargins(dp(10), 0, 0, 0);
        row.addView(tvInfo, infoParams);
        return row;
    }

    private Button taoButton(String text, String color) {
        Button button = new Button(requireContext());
        button.setText(text);
        button.setAllCaps(false);
        button.setTextSize(12);
        button.setTextColor(Color.WHITE);
        button.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)));
        return button;
    }

    private void chonTrangThai(DonHang donHang) {
        String[] options = {DonHang.DA_GIAO, DonHang.DA_HUY};
        new AlertDialog.Builder(requireContext())
                .setTitle("Cập nhật đơn hàng")
                .setItems(options, (dialog, which) -> capNhatTrangThai(donHang, options[which]))
                .show();
    }

    private void capNhatTrangThai(DonHang donHang, String trangThaiMoi) {
        db.collection("donhang").document(donHang.getId())
                .update("trangThai", trangThaiMoi)
                .addOnSuccessListener(unused -> {
                    donHang.setTrangThai(trangThaiMoi);
                    Toast.makeText(requireContext(), "Đã cập nhật đơn hàng", Toast.LENGTH_SHORT).show();
                    hienThiDanhSach();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(requireContext(), "Lỗi cập nhật: " + e.getMessage(), Toast.LENGTH_LONG).show());
    }

    private void hienChiTiet(DonHang donHang) {
        StringBuilder builder = new StringBuilder();
        builder.append("Mã đơn: ").append(giaTri(donHang.getId(), "Chưa có"));
        builder.append("\nKhách: ").append(giaTri(donHang.getTenNguoiMua(), donHang.getUserId()));
        builder.append("\nSĐT: ").append(giaTri(donHang.getSdtNguoiMua(), "Chưa có"));
        builder.append("\nĐịa chỉ: ").append(giaTri(donHang.getDiaChiGiao(), "Chưa có"));
        builder.append("\nNgày đặt: ").append(giaTri(donHang.getNgayDat(), "Chưa có"));
        builder.append("\nTrạng thái: ").append(giaTri(donHang.getTrangThai(), "Chưa rõ"));
        builder.append("\n\nSản phẩm của shop:");
        for (GioHangItem item : laySanPhamCuaShop(donHang)) {
            SanPham sp = item.getSanPham();
            builder.append("\n- ")
                    .append(sp == null ? "Sản phẩm" : giaTri(sp.getTen(), "Sản phẩm"))
                    .append(" x").append(item.getSoLuong());
        }
        builder.append("\n\nTổng của shop: ").append(formatGia(tinhTienShop(donHang)));

        new AlertDialog.Builder(requireContext())
                .setTitle("Chi tiết đơn hàng")
                .setMessage(builder.toString())
                .setNegativeButton("Đóng", null)
                .setPositiveButton("Cập nhật", (dialog, which) -> chonTrangThai(donHang))
                .show();
    }

    private boolean coSanPhamCuaShop(DonHang donHang) {
        return !laySanPhamCuaShop(donHang).isEmpty();
    }

    private ArrayList<GioHangItem> laySanPhamCuaShop(DonHang donHang) {
        ArrayList<GioHangItem> result = new ArrayList<>();
        if (donHang.getDanhSachSP() == null) return result;
        for (GioHangItem item : donHang.getDanhSachSP()) {
            if (item == null || item.getSanPham() == null) continue;
            if (shopId.equals(item.getSanPham().getShopId())) {
                result.add(item);
            }
        }
        return result;
    }

    private int tinhTienShop(DonHang donHang) {
        int total = 0;
        for (GioHangItem item : laySanPhamCuaShop(donHang)) {
            if (item.getSanPham() != null) {
                total += item.getSanPham().getGia() * item.getSoLuong();
            }
        }
        return total;
    }

    private int mauTrangThai(String trangThai) {
        if (DonHang.DA_GIAO.equals(trangThai)) return Color.parseColor("#4CAF50");
        if (DonHang.DA_HUY.equals(trangThai)) return Color.RED;
        return Color.parseColor("#FFA000");
    }

    private String formatGia(int gia) {
        return NumberFormat.getNumberInstance(new Locale("vi", "VN")).format(gia) + "đ";
    }

    private String giaTri(String value, String fallback) {
        return value == null || value.trim().isEmpty() ? fallback : value.trim();
    }

    private String maNgan(String id) {
        if (id == null || id.length() <= 6) return giaTri(id, "------");
        return id.substring(0, 6).toUpperCase(Locale.ROOT);
    }

    private int dp(int value) {
        return (int) (value * getResources().getDisplayMetrics().density);
    }
}
