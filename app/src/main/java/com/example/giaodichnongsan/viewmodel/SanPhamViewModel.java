package com.example.giaodichnongsan.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.giaodichnongsan.model.SanPham;
import com.example.giaodichnongsan.data.repository.SanPhamRepository;

import java.util.ArrayList;

public class SanPhamViewModel extends ViewModel {

    private MutableLiveData<ArrayList<SanPham>> listNoiBat;
    private MutableLiveData<ArrayList<SanPham>> listMoi;

    private SanPhamRepository repository;

    public SanPhamViewModel() {
        repository = new SanPhamRepository();

        listNoiBat = new MutableLiveData<>();
        listMoi = new MutableLiveData<>();

        loadData();
    }

    private void loadData() {
        listNoiBat.setValue(repository.getSanPhamNoiBat());
        listMoi.setValue(repository.getSanPhamMoi());
    }

    public LiveData<ArrayList<SanPham>> getSanPhamNoiBat() {
        return listNoiBat;
    }

    public LiveData<ArrayList<SanPham>> getSanPhamMoi() {
        return listMoi;
    }
}