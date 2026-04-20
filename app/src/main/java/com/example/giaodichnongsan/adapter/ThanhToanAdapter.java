package com.example.giaodichnongsan.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giaodichnongsan.R;
import com.example.giaodichnongsan.model.GioHangItem;

import java.util.ArrayList;

public class ThanhToanAdapter extends RecyclerView.Adapter<ThanhToanAdapter.ViewHolder> {

    private ArrayList<GioHangItem> list;

    public ThanhToanAdapter(ArrayList<GioHangItem> list) {
        this.list = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView tvTen, tvGia, tvSoLuong;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.imgSanPham);
            tvTen = itemView.findViewById(R.id.tvTenSanPham);
            tvGia = itemView.findViewById(R.id.tvGia);
            tvSoLuong = itemView.findViewById(R.id.tvSoLuong);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_thanhtoan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GioHangItem item = list.get(position);

        holder.tvTen.setText(item.getSanPham().getTen());
        holder.tvGia.setText(item.getSanPham().getGia() + "đ");
        holder.tvSoLuong.setText("x" + item.getSoLuong());

        holder.img.setImageResource(item.getSanPham().getHinh());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}