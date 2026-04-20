package com.example.giaodichnongsan.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.giaodichnongsan.data.repository.GioHangRepository;
import com.example.giaodichnongsan.model.GioHangItem;
import com.example.giaodichnongsan.model.SanPham;

import java.util.ArrayList;

public class GioHangViewModel extends ViewModel {

    private MutableLiveData<ArrayList<GioHangItem>> gioHangList;
    private MutableLiveData<Integer> totalPrice;

    private GioHangRepository repository;

    public GioHangViewModel() {
        repository = new GioHangRepository();

        gioHangList = new MutableLiveData<>();
        totalPrice = new MutableLiveData<>();

        loadData();
    }

    // ===== LOAD DATA BAN ĐẦU =====
    private void loadData() {
        gioHangList.setValue(repository.getGioHang());
        totalPrice.setValue(repository.getTotalPrice());
    }

    // ===== GET DATA =====
    public LiveData<ArrayList<GioHangItem>> getGioHangList() {
        return gioHangList;
    }

    public LiveData<Integer> getTotalPrice() {
        return totalPrice;
    }

    // ===== THÊM SẢN PHẨM =====
    public void addToCart(SanPham sanPham) {
        repository.addToCart(sanPham);
        refreshData();
    }

    // ===== XOÁ =====
    public void removeItem(GioHangItem item) {
        repository.removeItem(item);
        refreshData();
    }

    // ===== TĂNG =====
    public void increaseQuantity(GioHangItem item) {
        repository.increaseQuantity(item);
        refreshData();
    }

    // ===== GIẢM =====
    public void decreaseQuantity(GioHangItem item) {
        repository.decreaseQuantity(item);
        refreshData();
    }

    // ===== CLEAR =====
    public void clearCart() {
        repository.clearCart();
        refreshData();
    }

    // ===== REFRESH UI =====
    private void refreshData() {
        gioHangList.setValue(repository.getGioHang());
        totalPrice.setValue(repository.getTotalPrice());
    }
    public ArrayList<GioHangItem> getSelectedItems() {
        ArrayList<GioHangItem> selected = new ArrayList<>();

        if (gioHangList.getValue() != null) {
            for (GioHangItem item : gioHangList.getValue()) {
                if (item.isChecked()) {
                    selected.add(item);
                }
            }
        }

        return selected;
    }
    public int getSelectedTotalPrice() {
        int total = 0;

        if (gioHangList.getValue() != null) {
            for (GioHangItem item : gioHangList.getValue()) {
                if (item.isChecked()) {
                    total += item.getSanPham().getGia() * item.getSoLuong();
                }
            }
        }

        return total;
    }
    public void selectAll(boolean isChecked) {
        if (gioHangList.getValue() != null) {
            for (GioHangItem item : gioHangList.getValue()) {
                item.setChecked(isChecked);
            }
            gioHangList.setValue(gioHangList.getValue()); // trigger update UI
        }
    }

}