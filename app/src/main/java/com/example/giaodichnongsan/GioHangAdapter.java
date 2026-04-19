package com.example.giaodichnongsan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.ViewHolder> {

    private ArrayList<GioHangItem> list;
    private OnItemClickListener listener;

    public GioHangAdapter(ArrayList<GioHangItem> list, OnItemClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView tvTen, tvGia, tvSoLuong, tvTong, tvShop;
        TextView btnPlus, btnMinus, btnXoa;
        CheckBox cbChon;

        public ViewHolder(View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.imgSP);
            tvTen = itemView.findViewById(R.id.tvTen);
            tvGia = itemView.findViewById(R.id.tvGia);
            tvSoLuong = itemView.findViewById(R.id.tvSoLuong);
            tvTong = itemView.findViewById(R.id.tvTong);
            tvShop = itemView.findViewById(R.id.tvShop);

            btnPlus = itemView.findViewById(R.id.btnPlus);
            btnMinus = itemView.findViewById(R.id.btnMinus);
            btnXoa = itemView.findViewById(R.id.btnXoa);
            cbChon = itemView.findViewById(R.id.cbChon);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_giohang, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        GioHangItem item = list.get(position);
        SanPham sp = item.getSanPham();

        holder.img.setImageResource(sp.getHinh());
        holder.tvTen.setText(sp.getTen());
        holder.tvGia.setText(sp.getGia() + "đ");
        holder.tvSoLuong.setText(String.valueOf(item.getSoLuong()));
        holder.tvShop.setText(sp.getTenShop()); // 🔥 lấy từ SanPham
        holder.cbChon.setChecked(item.isChecked());

        int tong = sp.getGia() * item.getSoLuong();
        holder.tvTong.setText("Tổng: " + tong + "đ");

        // ===== CLICK EVENTS =====

        holder.btnPlus.setOnClickListener(v -> {
            if (listener != null) listener.onIncrease(item);
        });

        holder.btnMinus.setOnClickListener(v -> {
            if (listener != null) listener.onDecrease(item);
        });

        holder.btnXoa.setOnClickListener(v -> {
            if (listener != null) listener.onRemove(item);
        });

        holder.cbChon.setOnCheckedChangeListener((buttonView, isChecked) -> {
            item.setChecked(isChecked);
            if (listener != null) listener.onCheckChanged();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    // ===== UPDATE DATA =====
    public void setData(ArrayList<GioHangItem> newList) {
        this.list = newList;
        notifyDataSetChanged();
    }

    // ===== CALLBACK =====
    public interface OnItemClickListener {
        void onIncrease(GioHangItem item);
        void onDecrease(GioHangItem item);
        void onRemove(GioHangItem item);
        void onCheckChanged();
    }
}