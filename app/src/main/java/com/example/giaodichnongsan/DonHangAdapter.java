package com.example.giaodichnongsan;

import android.graphics.Color;
import android.view.*;
import android.widget.*;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class DonHangAdapter extends RecyclerView.Adapter<DonHangAdapter.ViewHolder> {

    ArrayList<DonHang> list;
    OnItemClick listener;

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

        holder.tvTenSP.setText(dh.getTenSP() + " x" + dh.getSoLuong());
        holder.tvTongTien.setText(String.format("%,dđ", dh.getTongTien()));
        holder.tvTrangThai.setText(dh.getTrangThai());

        // 🔥 FIX CRASH
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onClick(dh);
            }
        });

        // màu trạng thái
        if (dh.getTrangThai().equals(DonHang.DANG_GIAO)) {
            holder.tvTrangThai.setTextColor(Color.parseColor("#FFA000"));
        } else if (dh.getTrangThai().equals(DonHang.DA_GIAO)) {
            holder.tvTrangThai.setTextColor(Color.parseColor("#4CAF50"));
        } else {
            holder.tvTrangThai.setTextColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
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