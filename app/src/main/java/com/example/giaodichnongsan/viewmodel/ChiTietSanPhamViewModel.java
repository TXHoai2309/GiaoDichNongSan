package com.example.giaodichnongsan.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.giaodichnongsan.data.repository.SanPhamRepository;
import com.example.giaodichnongsan.model.SanPham;

// ChiTietSanPhamViewModel.java — SỬA TOÀN BỘ FILE

public class ChiTietSanPhamViewModel extends ViewModel {

    private SanPhamRepository repository;
    private MutableLiveData<SanPham> sanPham = new MutableLiveData<>();

    public ChiTietSanPhamViewModel() {
        repository = new SanPhamRepository();
    }

    public LiveData<SanPham> getSanPham() {
        return sanPham;
    }

    // ✅ Đổi int → String
    public void loadSanPham(String productId) {
        repository.getSanPhamById(productId).observeForever(sp -> {
            sanPham.setValue(sp);
        });
    }
}