package com.example.giaodichnongsan.adapter;

import android.view.*;
import android.widget.*;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.giaodichnongsan.R;
import com.example.giaodichnongsan.model.GioHangItem;
import java.util.ArrayList;

public class SanPhamTrongDonAdapter extends RecyclerView.Adapter<SanPhamTrongDonAdapter.VH> {

    private ArrayList<GioHangItem> list;

    public SanPhamTrongDonAdapter(ArrayList<GioHangItem> list) {
        this.list = list != null ? list : new ArrayList<>();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sanpham_trong_don, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        GioHangItem item = list.get(position);
        if (item.getSanPham() == null) return;

        holder.tvTen.setText(item.getSanPham().getTen());
        holder.tvGia.setText(String.format("%,dđ", item.getSanPham().getGia()));
        holder.tvSoLuong.setText("x" + item.getSoLuong());
        holder.tvThanhTien.setText(String.format("%,dđ",
                item.getSanPham().getGia() * item.getSoLuong()));

        Glide.with(holder.imgSP.getContext())
                .load(item.getSanPham().getImageUrl())
                .placeholder(R.drawable.ic_holder) // thay bằng drawable có sẵn
                .into(holder.imgSP);
    }

    @Override
    public int getItemCount() { return list.size(); }

    static class VH extends RecyclerView.ViewHolder {
        ImageView imgSP;
        TextView tvTen, tvGia, tvSoLuong, tvThanhTien;

        VH(View v) {
            super(v);
            imgSP       = v.findViewById(R.id.imgSanPham);
            tvTen       = v.findViewById(R.id.tvTenSP);
            tvGia       = v.findViewById(R.id.tvGiaSP);
            tvSoLuong   = v.findViewById(R.id.tvSoLuong);
            tvThanhTien = v.findViewById(R.id.tvThanhTien);
        }
    }
}