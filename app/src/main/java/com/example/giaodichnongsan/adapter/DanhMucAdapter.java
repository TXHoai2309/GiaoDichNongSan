package com.example.giaodichnongsan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.giaodichnongsan.R;
import com.example.giaodichnongsan.model.DanhMuc;

import java.util.ArrayList;

public class DanhMucAdapter extends RecyclerView.Adapter<DanhMucAdapter.ViewHolder> {

    private Context context;
    private ArrayList<DanhMuc> list;
    private int selectedPosition = -1; // -1 = chưa chọn cái nào
    private OnDanhMucClickListener listener;

    public interface OnDanhMucClickListener {
        void onClick(DanhMuc danhMuc);
    }

    public DanhMucAdapter(Context context, ArrayList<DanhMuc> list, OnDanhMucClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView ten;

        public ViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.imgDanhMuc);
            ten  = itemView.findViewById(R.id.tvDanhMuc);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_danhmuc, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DanhMuc dm = list.get(position);
        holder.icon.setImageResource(dm.getIcon());
        holder.ten.setText(dm.getTen());

        // Highlight item đang chọn
        if (position == selectedPosition) {
            holder.itemView.setAlpha(1.0f);
            holder.ten.setTypeface(null, android.graphics.Typeface.BOLD);
        } else {
            holder.itemView.setAlpha(selectedPosition == -1 ? 1.0f : 0.5f);
            holder.ten.setTypeface(null, android.graphics.Typeface.NORMAL);
        }

        holder.itemView.setOnClickListener(v -> {
            int prev = selectedPosition;

            if (selectedPosition == position) {
                // Click lại cái đang chọn → bỏ chọn (reset)
                selectedPosition = -1;
            } else {
                selectedPosition = position;
            }

            notifyItemChanged(prev);
            notifyItemChanged(selectedPosition);

            if (listener != null) listener.onClick(dm);
        });
    }

    @Override
    public int getItemCount() { return list.size(); }
}