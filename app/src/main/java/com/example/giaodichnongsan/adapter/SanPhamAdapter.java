package com.example.giaodichnongsan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giaodichnongsan.ui.fragment.ChiTietSanPhamFragment;
import com.example.giaodichnongsan.R;
import com.example.giaodichnongsan.model.SanPham;

import java.util.ArrayList;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ViewHolder> {

    Context context;
    ArrayList<SanPham> list;

    public SanPhamAdapter(Context context, ArrayList<SanPham> list) {
        this.context = context;
        this.list = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView ten, tvGia, tvDaBan;

        public ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgSP);
            ten = itemView.findViewById(R.id.tvTen);
            tvGia = itemView.findViewById(R.id.tvGia);
            tvDaBan = itemView.findViewById(R.id.tvDaBan);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sanpham, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SanPham sp = list.get(position);

        holder.img.setImageResource(sp.getHinh());
        holder.ten.setText(sp.getTen());

        holder.tvGia.setText(String.format("%,dđ/kg", sp.getGia()));
        holder.tvDaBan.setText(sp.getDaBan() + " đã bán");

        // 🔥 CLICK ITEM
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