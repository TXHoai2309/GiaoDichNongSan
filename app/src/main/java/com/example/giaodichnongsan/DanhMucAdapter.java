package com.example.giaodichnongsan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DanhMucAdapter extends RecyclerView.Adapter<DanhMucAdapter.ViewHolder> {

    Context context;
    ArrayList<DanhMuc> list;

    public DanhMucAdapter(Context context, ArrayList<DanhMuc> list) {
        this.context = context;
        this.list = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView ten;

        public ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgDanhMuc);
            ten = itemView.findViewById(R.id.tvDanhMuc);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_danhmuc, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DanhMuc dm = list.get(position);
        holder.img.setImageResource(dm.getHinh());
        holder.ten.setText(dm.getTen());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}