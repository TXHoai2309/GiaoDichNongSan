package com.example.giaodichnongsan.ui.fragment;

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

import com.example.giaodichnongsan.R;
import com.example.giaodichnongsan.model.DonHang;
import com.example.giaodichnongsan.model.GioHangItem;
import com.example.giaodichnongsan.model.SanPham;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class QuanLyDoanhThuFragment extends Fragment {
    private static final String ARG_SHOP_ID = "shop_id";
    private static final String ARG_TEN_SHOP = "ten_shop";

    private final String[] filters = {"7 ngày", "30 ngày", "Tất cả"};
    private final ArrayList<DonHang> deliveredOrders = new ArrayList<>();

    private ImageView btnBack;
    private TextView tvTongDoanhThu, tvTenShop, tvDonDaGiao, tvSanPhamDaBan, tvTrungBinhDon, tvEmpty;
    private LinearLayout layoutBoLoc, layoutDoanhThuNgay, layoutTopSanPham;
    private FirebaseFirestore db;
    private String shopId;
    private String tenShop;
    private String currentFilter = "30 ngày";

    public static QuanLyDoanhThuFragment newInstance(String shopId, String tenShop) {
        QuanLyDoanhThuFragment fragment = new QuanLyDoanhThuFragment();
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
        View view = inflater.inflate(R.layout.fragment_quan_ly_doanh_thu, container, false);
        btnBack = view.findViewById(R.id.btnBack);
        tvTongDoanhThu = view.findViewById(R.id.tvTongDoanhThu);
        tvTenShop = view.findViewById(R.id.tvTenShop);
        tvDonDaGiao = view.findViewById(R.id.tvDonDaGiao);
        tvSanPhamDaBan = view.findViewById(R.id.tvSanPhamDaBan);
        tvTrungBinhDon = view.findViewById(R.id.tvTrungBinhDon);
        tvEmpty = view.findViewById(R.id.tvEmpty);
        layoutBoLoc = view.findViewById(R.id.layoutBoLoc);
        layoutDoanhThuNgay = view.findViewById(R.id.layoutDoanhThuNgay);
        layoutTopSanPham = view.findViewById(R.id.layoutTopSanPham);

        btnBack.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());
        tvTenShop.setText(giaTri(tenShop, "Cửa hàng"));
        taoBoLoc();
        loadDoanhThu();
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
            button.setTextColor(filter.equals(currentFilter) ? Color.WHITE : Color.parseColor("#4A423D"));
            button.setBackgroundTintList(ColorStateList.valueOf(
                    filter.equals(currentFilter) ? Color.parseColor("#6D55B3") : Color.parseColor("#FFF8F4")));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, dp(40));
            params.setMargins(0, 0, dp(8), 0);
            layoutBoLoc.addView(button, params);
            button.setOnClickListener(v -> {
                currentFilter = filter;
                taoBoLoc();
                hienThiDoanhThu();
            });
        }
    }

    private void loadDoanhThu() {
        if (TextUtils.isEmpty(shopId)) {
            tvEmpty.setVisibility(View.VISIBLE);
            return;
        }

        tvTongDoanhThu.setText("Đang tải...");
        db.collection("donhang")
                .orderBy("ngayDatMillis", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(snapshot -> {
                    if (!isAdded()) return;
                    deliveredOrders.clear();
                    for (com.google.firebase.firestore.DocumentSnapshot doc : snapshot.getDocuments()) {
                        DonHang donHang = doc.toObject(DonHang.class);
                        if (donHang == null) continue;
                        donHang.setId(doc.getId());
                        if (DonHang.DA_GIAO.equals(donHang.getTrangThai()) && tinhTienShop(donHang) > 0) {
                            deliveredOrders.add(donHang);
                        }
                    }
                    hienThiDoanhThu();
                })
                .addOnFailureListener(e -> {
                    if (!isAdded()) return;
                    tvTongDoanhThu.setText("0đ");
                    Toast.makeText(requireContext(), "Lỗi tải doanh thu: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }

    private void hienThiDoanhThu() {
        ArrayList<DonHang> orders = locTheoThoiGian();
        int doanhThu = 0;
        int soLuongSanPham = 0;
        Map<String, Integer> revenueByDay = taoMocNgay();
        Map<String, ProductStat> productStats = new HashMap<>();

        for (DonHang donHang : orders) {
            int tienShop = tinhTienShop(donHang);
            doanhThu += tienShop;

            String dayKey = formatNgay(donHang.getNgayDatMillis());
            revenueByDay.put(dayKey, revenueByDay.containsKey(dayKey) ? revenueByDay.get(dayKey) + tienShop : tienShop);

            for (GioHangItem item : laySanPhamCuaShop(donHang)) {
                SanPham sp = item.getSanPham();
                if (sp == null) continue;
                soLuongSanPham += item.getSoLuong();
                String key = giaTri(sp.getId(), sp.getTen());
                ProductStat stat = productStats.get(key);
                if (stat == null) {
                    stat = new ProductStat(giaTri(sp.getTen(), "Sản phẩm"));
                    productStats.put(key, stat);
                }
                stat.quantity += item.getSoLuong();
                stat.revenue += sp.getGia() * item.getSoLuong();
            }
        }

        tvTongDoanhThu.setText(formatGia(doanhThu));
        tvDonDaGiao.setText(orders.size() + "\nĐơn đã giao");
        tvSanPhamDaBan.setText(soLuongSanPham + "\nSản phẩm");
        tvTrungBinhDon.setText(formatGia(orders.isEmpty() ? 0 : doanhThu / orders.size()) + "\nTrung bình");
        tvEmpty.setVisibility(doanhThu == 0 ? View.VISIBLE : View.GONE);

        hienThiDoanhThuTheoNgay(revenueByDay);
        hienThiTopSanPham(productStats);
    }

    private ArrayList<DonHang> locTheoThoiGian() {
        if ("Tất cả".equals(currentFilter)) return new ArrayList<>(deliveredOrders);

        int days = "7 ngày".equals(currentFilter) ? 7 : 30;
        long from = System.currentTimeMillis() - days * 24L * 60L * 60L * 1000L;
        ArrayList<DonHang> result = new ArrayList<>();
        for (DonHang donHang : deliveredOrders) {
            if (donHang.getNgayDatMillis() >= from) result.add(donHang);
        }
        return result;
    }

    private Map<String, Integer> taoMocNgay() {
        LinkedHashMap<String, Integer> result = new LinkedHashMap<>();
        if ("Tất cả".equals(currentFilter)) return result;

        int days = "7 ngày".equals(currentFilter) ? 7 : 30;
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -(days - 1));
        for (int i = 0; i < days; i++) {
            result.put(formatNgay(calendar.getTimeInMillis()), 0);
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }
        return result;
    }

    private void hienThiDoanhThuTheoNgay(Map<String, Integer> revenueByDay) {
        layoutDoanhThuNgay.removeAllViews();
        if (revenueByDay.isEmpty()) {
            layoutDoanhThuNgay.addView(taoText("Chọn 7 ngày hoặc 30 ngày để xem doanh thu theo ngày", "#8A7F77", 14, false));
            return;
        }

        int max = 0;
        for (int value : revenueByDay.values()) max = Math.max(max, value);

        for (Map.Entry<String, Integer> entry : revenueByDay.entrySet()) {
            LinearLayout row = new LinearLayout(requireContext());
            row.setOrientation(LinearLayout.HORIZONTAL);
            row.setGravity(android.view.Gravity.CENTER_VERTICAL);
            row.setPadding(0, dp(5), 0, dp(5));

            TextView day = taoText(entry.getKey(), "#6E625B", 12, false);
            row.addView(day, new LinearLayout.LayoutParams(dp(54), ViewGroup.LayoutParams.WRAP_CONTENT));

            TextView bar = new TextView(requireContext());
            bar.setBackgroundColor(Color.parseColor(entry.getValue() > 0 ? "#68A25A" : "#E4DDD8"));
            LinearLayout.LayoutParams barParams = new LinearLayout.LayoutParams(
                    max == 0 ? dp(8) : Math.max(dp(8), dp(150) * entry.getValue() / max), dp(10));
            row.addView(bar, barParams);

            TextView value = taoText("  " + formatGia(entry.getValue()), "#4A423D", 12, false);
            row.addView(value, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            layoutDoanhThuNgay.addView(row);
        }
    }

    private void hienThiTopSanPham(Map<String, ProductStat> productStats) {
        layoutTopSanPham.removeAllViews();
        ArrayList<ProductStat> stats = new ArrayList<>(productStats.values());
        stats.sort((a, b) -> Integer.compare(b.revenue, a.revenue));

        if (stats.isEmpty()) {
            layoutTopSanPham.addView(taoText("Chưa có sản phẩm bán ra trong khoảng này", "#8A7F77", 14, false));
            return;
        }

        int limit = Math.min(5, stats.size());
        for (int i = 0; i < limit; i++) {
            ProductStat stat = stats.get(i);
            TextView row = taoText((i + 1) + ". " + stat.name
                    + "\nĐã bán " + stat.quantity + " - " + formatGia(stat.revenue),
                    "#4A423D", 14, i == 0);
            row.setPadding(0, dp(6), 0, dp(6));
            layoutTopSanPham.addView(row);
        }
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

    private TextView taoText(String text, String color, int size, boolean bold) {
        TextView textView = new TextView(requireContext());
        textView.setText(text);
        textView.setTextColor(Color.parseColor(color));
        textView.setTextSize(size);
        if (bold) textView.setTypeface(null, Typeface.BOLD);
        return textView;
    }

    private String formatNgay(long millis) {
        return new SimpleDateFormat("dd/MM", Locale.getDefault()).format(millis);
    }

    private String formatGia(int gia) {
        return NumberFormat.getNumberInstance(new Locale("vi", "VN")).format(gia) + "đ";
    }

    private String giaTri(String value, String fallback) {
        return value == null || value.trim().isEmpty() ? fallback : value.trim();
    }

    private int dp(int value) {
        return (int) (value * getResources().getDisplayMetrics().density);
    }

    private static class ProductStat {
        final String name;
        int quantity;
        int revenue;

        ProductStat(String name) {
            this.name = name;
        }
    }
}
