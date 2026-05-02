package com.example.giaodichnongsan.data.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.giaodichnongsan.model.DonHang;
import com.example.giaodichnongsan.model.GioHangItem;
import com.example.giaodichnongsan.model.SanPham;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DonHangRepository {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseAuth auth = FirebaseAuth.getInstance();

    public MutableLiveData<Boolean> addDonHang(ArrayList<GioHangItem> list, int tongTien) {
        return addDonHang(list, tongTien, "", "", "", "");
    }

    public MutableLiveData<Boolean> addDonHang(ArrayList<GioHangItem> list, int tongTien,
                                               String tenNguoiMua, String sdtNguoiMua,
                                               String diaChiGiao, String ghiChu) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();

        if (auth.getCurrentUser() == null) {
            result.setValue(false);
            return result;
        }

        if (list == null || list.isEmpty()) {
            result.setValue(false);
            return result;
        }

        String userId = auth.getCurrentUser().getUid();
        String ngayDat = DateFormat.getDateTimeInstance().format(new Date());
        long ngayDatMillis = System.currentTimeMillis();

        DocumentReference orderRef = db.collection("donhang").document();

        Map<String, Object> donHangData = new HashMap<>();
        donHangData.put("id", orderRef.getId());
        donHangData.put("userId", userId);
        donHangData.put("danhSachSP", toFirestoreItems(list));
        donHangData.put("tongTien", tongTien);
        donHangData.put("trangThai", DonHang.DANG_GIAO);
        donHangData.put("ngayDat", ngayDat);
        donHangData.put("ngayDatMillis", ngayDatMillis);
        donHangData.put("tenNguoiMua", tenNguoiMua);
        donHangData.put("sdtNguoiMua", sdtNguoiMua);
        donHangData.put("diaChiGiao", diaChiGiao);
        donHangData.put("ghiChu", ghiChu);

        orderRef.set(donHangData)
                .addOnSuccessListener(unused -> {
                    capNhatDaBan(list); // ← THÊM DÒNG NÀY
                    result.setValue(true);
                })
                .addOnFailureListener(e -> result.setValue(false));

        return result;

    }

    public MutableLiveData<ArrayList<DonHang>> getDonHangByUser() {
        MutableLiveData<ArrayList<DonHang>> result = new MutableLiveData<>();

        if (auth.getCurrentUser() == null) {
            result.setValue(new ArrayList<>());
            return result;
        }

        String userId = auth.getCurrentUser().getUid();

        db.collection("donhang")
                .whereEqualTo("userId", userId)
                .orderBy("ngayDatMillis", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(snapshot -> {
                    ArrayList<DonHang> list = new ArrayList<>();
                    for (var doc : snapshot.getDocuments()) {
                        DonHang dh = doc.toObject(DonHang.class);
                        if (dh != null) {
                            dh.setId(doc.getId());
                            list.add(dh);
                        }
                    }
                    result.setValue(list);
                })
                .addOnFailureListener(e -> result.setValue(new ArrayList<>()));

        return result;
    }

    private List<Map<String, Object>> toFirestoreItems(ArrayList<GioHangItem> list) {
        List<Map<String, Object>> result = new ArrayList<>();

        for (GioHangItem item : list) {
            if (item == null || item.getSanPham() == null) continue;

            SanPham sp = item.getSanPham();

            Map<String, Object> sanPhamMap = new HashMap<>();
            sanPhamMap.put("id", sp.getId());
            sanPhamMap.put("imageUrl", sp.getImageUrl());
            sanPhamMap.put("ten", sp.getTen());
            sanPhamMap.put("gia", sp.getGia());
            sanPhamMap.put("daBan", sp.getDaBan());
            sanPhamMap.put("moTa", sp.getMoTa());
            sanPhamMap.put("nguonGoc", sp.getNguonGoc());
            sanPhamMap.put("danhGia", sp.getDanhGia());
            sanPhamMap.put("danhMuc", sp.getDanhMuc());
            sanPhamMap.put("tenShop", sp.getTenShop());
            sanPhamMap.put("shopId", sp.getShopId());
            sanPhamMap.put("noiBat", sp.isNoiBat());

            Map<String, Object> itemMap = new HashMap<>();
            itemMap.put("sanPham", sanPhamMap);
            itemMap.put("soLuong", item.getSoLuong());
            itemMap.put("selected", item.isSelected());
            itemMap.put("thanhTien", sp.getGia() * item.getSoLuong());

            result.add(itemMap);
        }

        return result;
    }
    // ===== CẬP NHẬT TRẠNG THÁI ĐƠN HÀNG =====
    public MutableLiveData<Boolean> capNhatTrangThai(String donHangId, String trangThaiMoi) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();

        db.collection("donhang").document(donHangId)
                .update("trangThai", trangThaiMoi)
                .addOnSuccessListener(unused -> result.setValue(true))
                .addOnFailureListener(e -> {
                    android.util.Log.e("DonHangRepo", "Lỗi cập nhật trạng thái: " + e.getMessage());
                    result.setValue(false);
                });

        return result;
    }
    // ===== HUỶ ĐƠN HÀNG (người mua) =====
    public MutableLiveData<Boolean> huyDonHang(String donHangId) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();

        // Chỉ cho huỷ khi đang ở trạng thái "Đang giao"
        db.collection("donhang").document(donHangId)
                .get()
                .addOnSuccessListener(doc -> {
                    if (!doc.exists()) {
                        result.setValue(false);
                        return;
                    }
                    String trangThai = doc.getString("trangThai");
                    if (!DonHang.DANG_GIAO.equals(trangThai)) {
                        result.setValue(false); // không cho huỷ nếu đã giao/đã huỷ
                        return;
                    }
                    doc.getReference().update("trangThai", DonHang.DA_HUY)
                            .addOnSuccessListener(unused -> result.setValue(true))
                            .addOnFailureListener(e -> result.setValue(false));
                })
                .addOnFailureListener(e -> result.setValue(false));

        return result;
    }
    // ===== CẬP NHẬT SỐ LƯỢNG ĐÃ BÁN =====
    private void capNhatDaBan(ArrayList<GioHangItem> list) {
        for (GioHangItem item : list) {
            if (item.getSanPham() == null || item.getSanPham().getId() == null) continue;

            String sanPhamId = item.getSanPham().getId();
            int soLuongMua = item.getSoLuong();

            // Dùng FieldValue.increment để cộng dồn an toàn (tránh race condition)
            db.collection("sanpham").document(sanPhamId)
                    .update("daBan", com.google.firebase.firestore.FieldValue.increment(soLuongMua))
                    .addOnFailureListener(e ->
                            android.util.Log.e("DonHangRepo", "Lỗi cập nhật daBan: " + e.getMessage()));
        }
    }
}
