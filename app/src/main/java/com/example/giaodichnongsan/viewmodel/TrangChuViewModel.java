package com.example.giaodichnongsan.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.giaodichnongsan.data.repository.TrangChuRepository;
import com.example.giaodichnongsan.model.DanhMuc;
import com.example.giaodichnongsan.model.SanPham;

import java.util.ArrayList;
import java.util.List;

public class TrangChuViewModel extends ViewModel {

    private TrangChuRepository repository;

    private MutableLiveData<List<SanPham>> sanPhamNoiBat = new MutableLiveData<>();
    private MutableLiveData<List<SanPham>> sanPhamMoi    = new MutableLiveData<>();
    private MutableLiveData<List<DanhMuc>> danhMuc       = new MutableLiveData<>();

    // 🔥 THÊM — giữ full list để filter
    private List<SanPham> allSanPham = new ArrayList<>();

    public TrangChuViewModel() {
        repository = new TrangChuRepository();
    }

    public LiveData<List<SanPham>> getSanPhamNoiBat() { return sanPhamNoiBat; }
    public LiveData<List<SanPham>> getSanPhamMoi()    { return sanPhamMoi; }
    public LiveData<List<DanhMuc>> getDanhMuc()       { return danhMuc; }

    public void loadData() {
        sanPhamNoiBat.setValue(repository.getSanPhamNoiBat());
        allSanPham = repository.getSanPhamMoi();
        sanPhamMoi.setValue(allSanPham);
        danhMuc.setValue(repository.getDanhMuc());
    }

    // 🔥 THÊM — filter theo danh mục
    public void filterByDanhMuc(String danhMucId) {
        if (danhMucId.equals("all")) {
            sanPhamMoi.setValue(allSanPham);
            return;
        }

        List<SanPham> filtered = new ArrayList<>();
        for (SanPham sp : allSanPham) {
            if (danhMucId.equals(sp.getDanhMuc())) {
                filtered.add(sp);
            }
        }
        sanPhamMoi.setValue(filtered);
    }
}