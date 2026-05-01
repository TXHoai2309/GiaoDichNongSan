package com.example.giaodichnongsan.data.repository;

import androidx.lifecycle.MutableLiveData;
import com.example.giaodichnongsan.model.SanPham;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class SanPhamRepository {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    // ===== LẤY SẢN PHẨM NỔI BẬT =====
    public MutableLiveData<List<SanPham>> getSanPhamNoiBat() {
        MutableLiveData<List<SanPham>> result = new MutableLiveData<>();

        db.collection("sanpham")
                .whereEqualTo("noiBat", true)
                .get()
                .addOnSuccessListener(snapshot -> {
                    List<SanPham> list = new ArrayList<>();
                    for (var doc : snapshot.getDocuments()) {
                        SanPham sp = doc.toObject(SanPham.class);
                        if (sp != null) {
                            sp.setId(doc.getId()); // gán document ID
                            list.add(sp);
                        }
                    }
                    result.setValue(list);
                })
                .addOnFailureListener(e -> result.setValue(new ArrayList<>()));

        return result;
    }

    // ===== LẤY SẢN PHẨM MỚI (không phải nổi bật) =====
    public MutableLiveData<List<SanPham>> getSanPhamMoi() {
        MutableLiveData<List<SanPham>> result = new MutableLiveData<>();

        db.collection("sanpham")
                .get()
                .addOnSuccessListener(snapshot -> {
                    List<SanPham> list = new ArrayList<>();
                    for (var doc : snapshot.getDocuments()) {
                        SanPham sp = doc.toObject(SanPham.class);
                        if (sp != null) {
                            sp.setId(doc.getId());
                            list.add(sp);
                        }
                    }
                    result.setValue(list);
                })
                .addOnFailureListener(e -> result.setValue(new ArrayList<>()));

        return result;
    }

    // ===== TÌM THEO ID =====
    public MutableLiveData<SanPham> getSanPhamById(String id) {
        MutableLiveData<SanPham> result = new MutableLiveData<>();

        db.collection("sanpham")
                .document(id)
                .get()
                .addOnSuccessListener(doc -> {
                    SanPham sp = doc.toObject(SanPham.class);
                    if (sp != null) sp.setId(doc.getId());
                    result.setValue(sp);
                })
                .addOnFailureListener(e -> result.setValue(null));

        return result;
    }

    // ===== LẤY SẢN PHẨM THEO SHOP =====
    public MutableLiveData<List<SanPham>> getSanPhamByShop(String shopId) {
        MutableLiveData<List<SanPham>> result = new MutableLiveData<>();

        db.collection("sanpham")
                .whereEqualTo("shopId", shopId)
                .get()
                .addOnSuccessListener(snapshot -> {
                    List<SanPham> list = new ArrayList<>();
                    for (var doc : snapshot.getDocuments()) {
                        SanPham sp = doc.toObject(SanPham.class);
                        if (sp != null) {
                            sp.setId(doc.getId());
                            list.add(sp);
                        }
                    }
                    result.setValue(list);
                })
                .addOnFailureListener(e -> result.setValue(new ArrayList<>()));

        return result;
    }
}