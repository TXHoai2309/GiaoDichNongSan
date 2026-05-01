package com.example.giaodichnongsan.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.giaodichnongsan.data.repository.SanPhamRepository;
import com.example.giaodichnongsan.data.repository.TrangChuRepository;
import com.example.giaodichnongsan.model.DanhMuc;
import com.example.giaodichnongsan.model.SanPham;

import java.util.ArrayList;
import java.util.List;

public class TrangChuViewModel extends ViewModel {

    private final SanPhamRepository sanPhamRepo = new SanPhamRepository();
    private final TrangChuRepository trangChuRepo = new TrangChuRepository();

    private final MutableLiveData<List<SanPham>> sanPhamNoiBat = new MutableLiveData<>();
    private final MutableLiveData<List<SanPham>> sanPhamMoi    = new MutableLiveData<>();
    private final MutableLiveData<List<DanhMuc>> danhMuc       = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading           = new MutableLiveData<>(false);

    private List<SanPham> allSanPham = new ArrayList<>();

    public LiveData<List<SanPham>> getSanPhamNoiBat() { return sanPhamNoiBat; }
    public LiveData<List<SanPham>> getSanPhamMoi()    { return sanPhamMoi; }
    public LiveData<List<DanhMuc>> getDanhMuc()       { return danhMuc; }
    public LiveData<Boolean>       getIsLoading()     { return isLoading; }

    public void loadData() {
        isLoading.setValue(true);

        // Load danh mục (vẫn dùng fake, sau nâng lên Firestore)
        danhMuc.setValue(trangChuRepo.getDanhMuc());

        // Load sản phẩm nổi bật từ Firestore
        sanPhamRepo.getSanPhamNoiBat().observeForever(list -> {
            sanPhamNoiBat.setValue(list);
        });

        // Load tất cả sản phẩm
        sanPhamRepo.getSanPhamMoi().observeForever(list -> {
            allSanPham = list;
            sanPhamMoi.setValue(list);
            isLoading.setValue(false);
        });
    }

    // Filter theo danh mục
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