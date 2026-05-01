package com.example.giaodichnongsan.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.giaodichnongsan.data.repository.ShopRepository;
import com.example.giaodichnongsan.model.SanPham;
import com.example.giaodichnongsan.model.Shop;

import java.util.List;

public class ChiTietShopViewModel extends ViewModel {

    private ShopRepository repository;

    private MutableLiveData<Shop> shop = new MutableLiveData<>();
    private MutableLiveData<List<SanPham>> sanPhamShop = new MutableLiveData<>();

    public ChiTietShopViewModel() {
        repository = new ShopRepository();
    }

    public LiveData<Shop> getShop() {
        return shop;
    }

    public LiveData<List<SanPham>> getSanPhamShop() {
        return sanPhamShop;
    }

    public void loadShop(int shopId) {
        shop.setValue(repository.getShopById(shopId));
        sanPhamShop.setValue(repository.getSanPhamByShop(shopId));
    }
}