package com.example.giaodichnongsan.adapter;

import android.graphics.Color;
import android.view.*;
import android.widget.*;

import androidx.recyclerview.widget.RecyclerView;

import com.example.giaodichnongsan.R;
import com.example.giaodichnongsan.model.DonHang;
import com.example.giaodichnongsan.model.GioHangItem;

import java.util.ArrayList;

public class DonHangAdapter extends RecyclerView.Adapter<DonHangAdapter.ViewHolder> {

    private ArrayList<DonHang> list;
    private OnItemClick listener;

    public interface OnItemClick {
        void onClick(DonHang donHang);
    }

    public DonHangAdapter(ArrayList<DonHang> list, OnItemClick listener) {
        this.list = list;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_donhang, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        DonHang dh = list.get(position);

        // ===== HIỂN THỊ SẢN PHẨM =====
        StringBuilder tenSP = new StringBuilder();
        int tongSoLuong = 0;

        if (dh.getDanhSachSP() != null) {
            for (GioHangItem item : dh.getDanhSachSP()) {
                tenSP.append(item.getSanPham().getTen())
                        .append(" x")
                        .append(item.getSoLuong())
                        .append("\n");

                tongSoLuong += item.getSoLuong();
            }
        }

        holder.tvTenSP.setText(tenSP.toString().trim());
        holder.tvTongTien.setText(String.format("%,dđ", dh.getTongTien()));
        holder.tvTrangThai.setText(dh.getTrangThai());

        // ===== CLICK =====
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onClick(dh);
        });

        // ===== MÀU TRẠNG THÁI =====
        switch (dh.getTrangThai()) {
            case DonHang.DANG_GIAO:
                holder.tvTrangThai.setTextColor(Color.parseColor("#FFA000"));
                break;
            case DonHang.DA_GIAO:
                holder.tvTrangThai.setTextColor(Color.parseColor("#4CAF50"));
                break;
            case DonHang.DA_HUY:
                holder.tvTrangThai.setTextColor(Color.RED);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    // ===== UPDATE DATA =====
    public void setData(ArrayList<DonHang> newList) {
        this.list = newList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTenSP, tvTongTien, tvTrangThai;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTenSP = itemView.findViewById(R.id.tvTenSP);
            tvTongTien = itemView.findViewById(R.id.tvTongTien);
            tvTrangThai = itemView.findViewById(R.id.tvTrangThai);
        }
    }
}