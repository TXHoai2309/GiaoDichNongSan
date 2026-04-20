package com.example.giaodichnongsan.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.giaodichnongsan.data.repository.TrangChuRepository;
import com.example.giaodichnongsan.model.DanhMuc;
import com.example.giaodichnongsan.model.SanPham;

import java.util.List;

public class TrangChuViewModel extends ViewModel {

    private TrangChuRepository repository;

    private MutableLiveData<List<SanPham>> sanPhamNoiBat = new MutableLiveData<>();
    private MutableLiveData<List<SanPham>> sanPhamMoi = new MutableLiveData<>();
    private MutableLiveData<List<DanhMuc>> danhMuc = new MutableLiveData<>();

    public TrangChuViewModel() {
        repository = new TrangChuRepository();
    }

    // ===== GETTER =====
    public LiveData<List<SanPham>> getSanPhamNoiBat() {
        return sanPhamNoiBat;
    }

    public LiveData<List<SanPham>> getSanPhamMoi() {
        return sanPhamMoi;
    }

    public LiveData<List<DanhMuc>> getDanhMuc() {
        return danhMuc;
    }

    // ===== LOAD DATA =====
    public void loadData() {
        sanPhamNoiBat.setValue(repository.getSanPhamNoiBat());
        sanPhamMoi.setValue(repository.getSanPhamMoi());
        danhMuc.setValue(repository.getDanhMuc());
    }
}