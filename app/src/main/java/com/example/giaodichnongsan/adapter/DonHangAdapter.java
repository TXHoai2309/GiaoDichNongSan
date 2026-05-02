package com.example.giaodichnongsan.adapter;

import android.graphics.Color;
import android.view.*;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.giaodichnongsan.R;
import com.example.giaodichnongsan.model.DonHang;
import java.util.ArrayList;

public class DonHangAdapter extends RecyclerView.Adapter<DonHangAdapter.ViewHolder> {

    private ArrayList<DonHang> list;
    private OnItemClick listener;

    public interface OnItemClick { void onClick(DonHang donHang); }

    public DonHangAdapter(ArrayList<DonHang> list, OnItemClick listener) {
        this.list = list;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_donhang, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder h, int position) {
        DonHang dh = list.get(position);

        // Tên shop (lấy từ sản phẩm đầu tiên)
        String tenShop = "Shop";
        if (dh.getDanhSachSP() != null && !dh.getDanhSachSP().isEmpty()
                && dh.getDanhSachSP().get(0).getSanPham() != null) {
            tenShop = dh.getDanhSachSP().get(0).getSanPham().getTenShop();
        }
        h.tvTenShop.setText(tenShop);

        // Trạng thái + màu
        h.tvTrangThai.setText(dh.getTrangThai());
        switch (dh.getTrangThai()) {
            case DonHang.DANG_GIAO: h.tvTrangThai.setTextColor(Color.parseColor("#FFA000")); break;
            case DonHang.DA_GIAO:   h.tvTrangThai.setTextColor(Color.parseColor("#4CAF50")); break;
            case DonHang.DA_HUY:    h.tvTrangThai.setTextColor(Color.RED); break;
        }

        // Danh sách sản phẩm (nested RecyclerView)
        h.rvSanPham.setLayoutManager(new LinearLayoutManager(h.itemView.getContext()));
        h.rvSanPham.setAdapter(new SanPhamTrongDonAdapter(dh.getDanhSachSP()));

        // Tổng tiền + ngày đặt
        h.tvTongTien.setText(String.format("%,dđ", dh.getTongTien()));
        h.tvNgayDat.setText("Ngày đặt: " + dh.getNgayDat());

        // Click → mở chi tiết
        h.itemView.setOnClickListener(v -> { if (listener != null) listener.onClick(dh); });
    }

    @Override
    public int getItemCount() { return list != null ? list.size() : 0; }

    public void setData(ArrayList<DonHang> newList) {
        this.list = newList;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenShop, tvTrangThai, tvTongTien, tvNgayDat;
        RecyclerView rvSanPham;

        ViewHolder(View v) {
            super(v);
            tvTenShop   = v.findViewById(R.id.tvTenShop);
            tvTrangThai = v.findViewById(R.id.tvTrangThai);
            tvTongTien  = v.findViewById(R.id.tvTongTien);
            tvNgayDat   = v.findViewById(R.id.tvNgayDat);
            rvSanPham   = v.findViewById(R.id.rvSanPhamTrongDon);
        }
    }
}