package com.example.giaodichnongsan.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.giaodichnongsan.data.repository.DonHangRepository;
import com.example.giaodichnongsan.model.DonHang;
import com.example.giaodichnongsan.model.GioHangItem;

import java.util.ArrayList;

public class DonHangViewModel extends ViewModel {

    private MutableLiveData<ArrayList<DonHang>> donHangList;
    private DonHangRepository repository;

    public DonHangViewModel() {
        repository = new DonHangRepository();
        donHangList = new MutableLiveData<>();
        loadData();
    }

    private void loadData() {
        donHangList.setValue(repository.getAllDonHang());
    }

    public LiveData<ArrayList<DonHang>> getDonHangList() {
        return donHangList;
    }

    public void addDonHang(ArrayList<GioHangItem> list, int tongTien) {
        repository.addDonHang(list, tongTien);
        loadData();
    }
}