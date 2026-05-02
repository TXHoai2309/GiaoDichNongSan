package com.example.giaodichnongsan.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.giaodichnongsan.data.repository.GioHangRepository;
import com.example.giaodichnongsan.model.GioHangItem;
import com.example.giaodichnongsan.model.SanPham;
import java.util.ArrayList;

public class GioHangViewModel extends ViewModel {

    private final GioHangRepository repository = new GioHangRepository(); // ← THÊM
    private final MutableLiveData<ArrayList<GioHangItem>> gioHangList = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<Integer> tongTien = new MutableLiveData<>(0);
    private final MutableLiveData<Boolean> isAllSelected = new MutableLiveData<>(false);

    // ===== GETTER =====
    public LiveData<ArrayList<GioHangItem>> getGioHangList() { return gioHangList; }
    public LiveData<Integer> getTongTien()                   { return tongTien; }
    public LiveData<Boolean> getIsAllSelected()              { return isAllSelected; }

    // ===== LOAD TỪ FIREBASE (gọi khi mở app/đăng nhập) =====
    public void loadGioHang() {
        repository.getGioHang().observeForever(list -> {
            if (list != null) {
                gioHangList.setValue(list);
                updateState();
            }
        });
    }

    // ===== THÊM SẢN PHẨM =====
    public void addToCart(SanPham sp) {
        ArrayList<GioHangItem> list = gioHangList.getValue();
        if (list == null) list = new ArrayList<>();

        for (GioHangItem item : list) {
            if (sp.getId().equals(item.getSanPham().getId())) {
                item.setSoLuong(item.getSoLuong() + 1);
                repository.capNhatSoLuong(sp.getId(), item.getSoLuong()); // ← sync Firebase
                gioHangList.setValue(list);
                updateState();
                return;
            }
        }

        list.add(new GioHangItem(sp, 1));
        repository.addToCart(sp, list); // ← sync Firebase
        gioHangList.setValue(list);
        updateState();
    }

    // ===== THÊM VỚI SỐ LƯỢNG =====
    public void addToCartWithQuantity(SanPham sp, int soLuong) {
        ArrayList<GioHangItem> list = gioHangList.getValue();
        if (list == null) list = new ArrayList<>();

        for (GioHangItem item : list) {
            if (sp.getId().equals(item.getSanPham().getId())) {
                item.setSoLuong(item.getSoLuong() + soLuong);
                repository.capNhatSoLuong(sp.getId(), item.getSoLuong()); // ← sync Firebase
                gioHangList.setValue(list);
                updateState();
                return;
            }
        }

        list.add(new GioHangItem(sp, soLuong));
        repository.addToCart(sp, list); // ← sync Firebase
        gioHangList.setValue(list);
        updateState();
    }

    // ===== TĂNG =====
    public void increaseQuantity(GioHangItem item) {
        item.setSoLuong(item.getSoLuong() + 1);
        repository.capNhatSoLuong(item.getSanPham().getId(), item.getSoLuong()); // ← sync Firebase
        gioHangList.setValue(gioHangList.getValue());
        updateState();
    }

    // ===== GIẢM =====
    public void decreaseQuantity(GioHangItem item) {
        if (item.getSoLuong() > 1) {
            item.setSoLuong(item.getSoLuong() - 1);
            repository.capNhatSoLuong(item.getSanPham().getId(), item.getSoLuong()); // ← sync Firebase
            gioHangList.setValue(gioHangList.getValue());
            updateState();
        }
    }

    // ===== XOÁ =====
    public void removeItem(GioHangItem item) {
        ArrayList<GioHangItem> list = gioHangList.getValue();
        if (list != null) {
            repository.xoaKhoiGio(item.getSanPham().getId()); // ← sync Firebase
            list.remove(item);
            gioHangList.setValue(list);
            updateState();
        }
    }

    // ===== CLEAR (sau đặt hàng) =====
    public void clearCart() {
        repository.clearCart(); // ← sync Firebase
        gioHangList.setValue(new ArrayList<>());
        updateState();
    }

    // ===== GIỮ NGUYÊN các method cũ bên dưới =====
    public void selectAll(boolean isChecked) {
        ArrayList<GioHangItem> list = gioHangList.getValue();
        if (list != null) {
            for (GioHangItem item : list) item.setSelected(isChecked);
            gioHangList.setValue(list);
            updateState();
        }
    }

    public void updateSelection() { updateState(); }

    public ArrayList<GioHangItem> getSelectedItems() {
        ArrayList<GioHangItem> result = new ArrayList<>();
        ArrayList<GioHangItem> list = gioHangList.getValue();
        if (list != null)
            for (GioHangItem item : list)
                if (item.isSelected()) result.add(item);
        return result;
    }

    private int calculateTotal() {
        int total = 0;
        ArrayList<GioHangItem> list = gioHangList.getValue();
        if (list != null)
            for (GioHangItem item : list)
                if (item.isSelected())
                    total += item.getSoLuong() * item.getSanPham().getGia();
        return total;
    }

    private boolean checkAllSelected() {
        ArrayList<GioHangItem> list = gioHangList.getValue();
        if (list == null || list.isEmpty()) return false;
        for (GioHangItem item : list)
            if (!item.isSelected()) return false;
        return true;
    }

    private void updateState() {
        tongTien.setValue(calculateTotal());
        isAllSelected.setValue(checkAllSelected());
    }
}