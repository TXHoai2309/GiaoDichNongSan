package com.example.giaodichnongsan.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.giaodichnongsan.data.repository.DonHangRepository;
import com.example.giaodichnongsan.model.DonHang;
import com.example.giaodichnongsan.model.GioHangItem;

import java.util.ArrayList;

public class DonHangViewModel extends ViewModel {

    private final DonHangRepository repository = new DonHangRepository();

    private final MutableLiveData<ArrayList<DonHang>> donHangList = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<Boolean> isLoading              = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> datHangSuccess         = new MutableLiveData<>();

    public LiveData<ArrayList<DonHang>> getDonHangList()  { return donHangList; }
    public LiveData<Boolean>            getIsLoading()     { return isLoading; }
    public LiveData<Boolean>            getDatHangSuccess(){ return datHangSuccess; }

    // ===== THÊM ĐƠN HÀNG =====
    public void addDonHang(ArrayList<GioHangItem> list, int tongTien) {
        isLoading.setValue(true);

        repository.addDonHang(list, tongTien).observeForever(success -> {
            isLoading.setValue(false);
            datHangSuccess.setValue(success);
        });
    }

    // ===== LOAD ĐƠN HÀNG =====
    public void loadDonHang() {
        isLoading.setValue(true);

        repository.getDonHangByUser().observeForever(list -> {
            isLoading.setValue(false);
            donHangList.setValue(list);
        });
    }
    // ===== CẬP NHẬT TRẠNG THÁI =====
    public void capNhatTrangThai(String donHangId, String trangThaiMoi,
                                 MutableLiveData<Boolean> ketQua) {
        repository.capNhatTrangThai(donHangId, trangThaiMoi).observeForever(success -> {
            ketQua.setValue(success);
            if (Boolean.TRUE.equals(success)) {
                // Cập nhật lại list trong bộ nhớ, không cần load lại từ Firebase
                ArrayList<DonHang> list = donHangList.getValue();
                if (list != null) {
                    for (DonHang dh : list) {
                        if (dh.getId().equals(donHangId)) {
                            dh.setTrangThai(trangThaiMoi);
                            break;
                        }
                    }
                    donHangList.setValue(list); // trigger UI refresh
                }
            }
        });
    }
    // ===== HUỶ ĐƠN HÀNG =====
    public void huyDonHang(String donHangId, MutableLiveData<Boolean> ketQua) {
        repository.huyDonHang(donHangId).observeForever(success -> {
            ketQua.setValue(success);
            if (Boolean.TRUE.equals(success)) {
                // Cập nhật lại list trong bộ nhớ
                ArrayList<DonHang> list = donHangList.getValue();
                if (list != null) {
                    for (DonHang dh : list) {
                        if (dh.getId().equals(donHangId)) {
                            dh.setTrangThai(DonHang.DA_HUY);
                            break;
                        }
                    }
                    donHangList.setValue(list);
                }
            }
        });
    }
    // ===== LỌC THEO TRẠNG THÁI =====
    public ArrayList<DonHang> locTheotrangThai(String trangThai) {
        ArrayList<DonHang> allList = donHangList.getValue();
        if (allList == null) return new ArrayList<>();

        // "Tất cả" → trả về nguyên list
        if (trangThai == null || trangThai.equals("Tất cả")) {
            return new ArrayList<>(allList);
        }

        ArrayList<DonHang> filtered = new ArrayList<>();
        for (DonHang dh : allList) {
            if (trangThai.equals(dh.getTrangThai())) {
                filtered.add(dh);
            }
        }
        return filtered;
    }
}