package com.example.giaodichnongsan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SanPhamMoiAdapter extends RecyclerView.Adapter<SanPhamMoiAdapter.ViewHolder> {

    Context context;
    ArrayList<SanPham> list;

    public SanPhamMoiAdapter(Context context, ArrayList<SanPham> list) {
        this.context = context;
        this.list = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView ten, gia, daBan;

        public ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgSP);
            ten = itemView.findViewById(R.id.tvTen);
            gia = itemView.findViewById(R.id.tvGia);
            daBan = itemView.findViewById(R.id.tvDaBan);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_sanpham_moi, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SanPham sp = list.get(position);

        holder.img.setImageResource(sp.getHinh());
        holder.ten.setText(sp.getTen());
        holder.gia.setText(String.format("%,dđ/kg", sp.getGia()));
        holder.daBan.setText(sp.getDaBan() + " đã bán");

        // 🔥 THÊM ĐOẠN NÀY
        holder.itemView.setOnClickListener(v -> {
            if (context instanceof AppCompatActivity) {
                AppCompatActivity activity = (AppCompatActivity) context;

                ChiTietSanPhamFragment fragment = ChiTietSanPhamFragment.newInstance(sp);

                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameLayout, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}