package com.example.giaodichnongsan.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.giaodichnongsan.data.repository.SanPhamRepository;
import com.example.giaodichnongsan.model.SanPham;

public class ChiTietSanPhamViewModel extends ViewModel {

    private SanPhamRepository repository;
    private MutableLiveData<SanPham> sanPham = new MutableLiveData<>();

    public ChiTietSanPhamViewModel() {
        repository = new SanPhamRepository();
    }

    // ===== GETTER =====
    public LiveData<SanPham> getSanPham() {
        return sanPham;
    }

    // ===== LOAD DATA THEO ID =====
    public void loadSanPham(int productId) {
        SanPham result = repository.getSanPhamById(productId);
        sanPham.setValue(result);
    }
}