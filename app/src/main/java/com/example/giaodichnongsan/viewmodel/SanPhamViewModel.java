package com.example.giaodichnongsan.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.giaodichnongsan.model.SanPham;
import com.example.giaodichnongsan.data.repository.TrangChuRepository;

import java.util.ArrayList;

public class SanPhamViewModel extends ViewModel {

    // ===== DATA =====
    private MutableLiveData<ArrayList<SanPham>> listNoiBat = new MutableLiveData<>();
    private MutableLiveData<ArrayList<SanPham>> listMoi = new MutableLiveData<>();

    // ===== REPOSITORY =====
    private TrangChuRepository repository;

    public SanPhamViewModel() {
        repository = new TrangChuRepository();
        loadData();
    }

    // ===== LOAD DATA =====
    public void loadData() {
        listNoiBat.setValue(new ArrayList<>(repository.getSanPhamNoiBat()));
        listMoi.setValue(new ArrayList<>(repository.getSanPhamMoi()));
    }

    // ===== GETTER =====
    public LiveData<ArrayList<SanPham>> getSanPhamNoiBat() {
        return listNoiBat;
    }

    public LiveData<ArrayList<SanPham>> getSanPhamMoi() {
        return listMoi;
    }
}