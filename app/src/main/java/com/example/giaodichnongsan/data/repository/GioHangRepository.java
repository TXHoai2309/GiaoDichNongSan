package com.example.giaodichnongsan.data.repository;

import androidx.lifecycle.MutableLiveData;
import com.example.giaodichnongsan.model.GioHangItem;
import com.example.giaodichnongsan.model.SanPham;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GioHangRepository {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseAuth auth = FirebaseAuth.getInstance();

    private String getUserId() {
        return auth.getCurrentUser() != null
                ? auth.getCurrentUser().getUid() : null;
    }

    // ===== LẤY GIỎ HÀNG TỪ FIREBASE =====
    public MutableLiveData<ArrayList<GioHangItem>> getGioHang() {
        MutableLiveData<ArrayList<GioHangItem>> result = new MutableLiveData<>();
        String uid = getUserId();
        if (uid == null) { result.setValue(new ArrayList<>()); return result; }

        db.collection("users").document(uid).collection("giohang")
                .get()
                .addOnSuccessListener(snapshot -> {
                    ArrayList<GioHangItem> list = new ArrayList<>();
                    for (var doc : snapshot.getDocuments()) {
                        SanPham sp = doc.get("sanPham", SanPham.class);
                        Long soLuong = doc.getLong("soLuong");
                        if (sp != null && soLuong != null) {
                            sp.setId(doc.getId()); // đảm bảo ID không bị null
                            list.add(new GioHangItem(sp, soLuong.intValue()));
                        }
                    }
                    result.setValue(list);
                })
                .addOnFailureListener(e -> {
                    android.util.Log.e("GioHangRepo", "Lỗi load: " + e.getMessage());
                    result.setValue(new ArrayList<>());
                });

        return result;
    }

    // ===== THÊM / CẬP NHẬT SẢN PHẨM =====
    public void addToCart(SanPham sanPham, ArrayList<GioHangItem> currentList) {
        String uid = getUserId();
        if (uid == null) return;

        int soLuongMoi = 1;
        for (GioHangItem item : currentList) {
            if (item.getSanPham().getId().equals(sanPham.getId())) {
                soLuongMoi = item.getSoLuong();
                break;
            }
        }

        Map<String, Object> data = new HashMap<>();
        data.put("sanPham", sanPham);
        data.put("soLuong", soLuongMoi);

        db.collection("users").document(uid)
                .collection("giohang").document(sanPham.getId())
                .set(data, SetOptions.merge())
                .addOnFailureListener(e ->
                        android.util.Log.e("GioHangRepo", "Lỗi thêm: " + e.getMessage()));
    }

    // ===== CẬP NHẬT SỐ LƯỢNG =====
    public void capNhatSoLuong(String sanPhamId, int soLuongMoi) {
        String uid = getUserId();
        if (uid == null) return;

        if (soLuongMoi <= 0) {
            xoaKhoiGio(sanPhamId);
            return;
        }

        db.collection("users").document(uid)
                .collection("giohang").document(sanPhamId)
                .update("soLuong", soLuongMoi)
                .addOnFailureListener(e ->
                        android.util.Log.e("GioHangRepo", "Lỗi cập nhật SL: " + e.getMessage()));
    }

    // ===== XOÁ 1 SẢN PHẨM =====
    public void xoaKhoiGio(String sanPhamId) {
        String uid = getUserId();
        if (uid == null) return;

        db.collection("users").document(uid)
                .collection("giohang").document(sanPhamId)
                .delete()
                .addOnFailureListener(e ->
                        android.util.Log.e("GioHangRepo", "Lỗi xoá: " + e.getMessage()));
    }

    // ===== XOÁ TOÀN BỘ (sau khi đặt hàng) =====
    public void clearCart() {
        String uid = getUserId();
        if (uid == null) return;

        db.collection("users").document(uid).collection("giohang")
                .get()
                .addOnSuccessListener(snapshot -> {
                    for (var doc : snapshot.getDocuments()) {
                        doc.getReference().delete();
                    }
                });
    }

    // ===== TÍNH TỔNG TIỀN =====
    public int getTotalPrice(ArrayList<GioHangItem> list) {
        int total = 0;
        for (GioHangItem item : list)
            total += item.getSanPham().getGia() * item.getSoLuong();
        return total;
    }
}