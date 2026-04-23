package com.example.giaodichnongsan.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.giaodichnongsan.model.GioHangItem;
import com.example.giaodichnongsan.model.SanPham;

import java.util.ArrayList;

public class GioHangViewModel extends ViewModel {

    // ===== DATA =====
    private final MutableLiveData<ArrayList<GioHangItem>> gioHangList = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<Integer> tongTien = new MutableLiveData<>(0);
    private final MutableLiveData<Boolean> isAllSelected = new MutableLiveData<>(false);

    // ===== GETTER =====
    public LiveData<ArrayList<GioHangItem>> getGioHangList() {
        return gioHangList;
    }

    public LiveData<Integer> getTongTien() {
        return tongTien;
    }

    public LiveData<Boolean> getIsAllSelected() {
        return isAllSelected;
    }

    // ===== THÊM SẢN PHẨM =====
    public void addToCart(SanPham sp) {
        ArrayList<GioHangItem> list = gioHangList.getValue();
        if (list == null) list = new ArrayList<>();

        // kiểm tra đã tồn tại chưa
        for (GioHangItem item : list) {
            if (item.getSanPham().getId() == sp.getId()) {
                item.setSoLuong(item.getSoLuong() + 1);
                updateState();
                gioHangList.setValue(list);
                return;
            }
        }

        list.add(new GioHangItem(sp, 1));
        gioHangList.setValue(list);
        updateState();
    }

    // ===== TĂNG =====
    public void increaseQuantity(GioHangItem item) {
        item.setSoLuong(item.getSoLuong() + 1);
        gioHangList.setValue(gioHangList.getValue());
        updateState();
    }

    // ===== GIẢM =====
    public void decreaseQuantity(GioHangItem item) {
        if (item.getSoLuong() > 1) {
            item.setSoLuong(item.getSoLuong() - 1);
            gioHangList.setValue(gioHangList.getValue());
            updateState();
        }
    }

    // ===== XOÁ =====
    public void removeItem(GioHangItem item) {
        ArrayList<GioHangItem> list = gioHangList.getValue();
        if (list != null) {
            list.remove(item);
            gioHangList.setValue(list);
            updateState();
        }
    }

    // ===== CHỌN TẤT CẢ =====
    public void selectAll(boolean isChecked) {
        ArrayList<GioHangItem> list = gioHangList.getValue();
        if (list != null) {
            for (GioHangItem item : list) {
                item.setSelected(isChecked);
            }
            gioHangList.setValue(list);
            updateState();
        }
    }

    // ===== UPDATE KHI CHECK =====
    public void updateSelection() {
        updateState();
    }
    public void clearCart() {
        gioHangList.setValue(new ArrayList<>());
        updateState();
    }

    // ===== LẤY ITEM ĐÃ CHỌN =====
    public ArrayList<GioHangItem> getSelectedItems() {
        ArrayList<GioHangItem> result = new ArrayList<>();
        ArrayList<GioHangItem> list = gioHangList.getValue();

        if (list != null) {
            for (GioHangItem item : list) {
                if (item.isSelected()) {
                    result.add(item);
                }
            }
        }
        return result;
    }

    // ===== TÍNH TỔNG =====
    private int calculateTotal() {
        int total = 0;
        ArrayList<GioHangItem> list = gioHangList.getValue();

        if (list != null) {
            for (GioHangItem item : list) {
                if (item.isSelected()) {
                    total += item.getSoLuong() * item.getSanPham().getGia();
                }
            }
        }
        return total;
    }

    // ===== CHECK ALL =====
    private boolean checkAllSelected() {
        ArrayList<GioHangItem> list = gioHangList.getValue();

        if (list == null || list.isEmpty()) return false;

        for (GioHangItem item : list) {
            if (!item.isSelected()) return false;
        }
        return true;
    }

    // ===== UPDATE STATE (🔥 QUAN TRỌNG NHẤT) =====
    private void updateState() {
        tongTien.setValue(calculateTotal());
        isAllSelected.setValue(checkAllSelected());
    }
    public void addToCartWithQuantity(SanPham sp, int soLuong) {
        ArrayList<GioHangItem> list = gioHangList.getValue();
        if (list == null) list = new ArrayList<>();

        for (GioHangItem item : list) {
            if (item.getSanPham().getId() == sp.getId()) {
                item.setSoLuong(item.getSoLuong() + soLuong);
                gioHangList.setValue(list);
                updateState();
                return;
            }
        }

        list.add(new GioHangItem(sp, soLuong));
        gioHangList.setValue(list);
        updateState();
    }
}