package com.example.giaodichnongsan.ui.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giaodichnongsan.R;
import com.example.giaodichnongsan.adapter.SanPhamMoiAdapter;
import com.example.giaodichnongsan.model.SanPham;
import com.example.giaodichnongsan.model.Shop;

import java.util.ArrayList;

public class ChiTietShopFragment extends Fragment {

    SanPhamMoiAdapter adapterMoi;
    ImageView imgShop;
    TextView tvTenShop, tvDanhGia, tvSanPham, tvFollower, tvThamGia, tvDiaChi, tvMoTa, tvPhone;
    Button btnFollow, btnChat;
    ArrayList<SanPham> listMoi;

    RecyclerView rvSanPhamShop;

    ImageView btnMenu;
    public static ChiTietShopFragment newInstance(Shop shop) {
        ChiTietShopFragment fragment = new ChiTietShopFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("shop", shop);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chi_tiet_shop, container, false);

        // ánh xạ
        imgShop = view.findViewById(R.id.imgShop);
        tvTenShop = view.findViewById(R.id.tvTenShop);
        tvDanhGia = view.findViewById(R.id.tvDanhGia);
        tvSanPham = view.findViewById(R.id.tvSanPham);
        tvFollower = view.findViewById(R.id.tvFollower);
        tvThamGia = view.findViewById(R.id.tvThamGia);
        tvDiaChi = view.findViewById(R.id.tvDiaChi);
        tvMoTa = view.findViewById(R.id.tvMoTa);
        tvPhone = view.findViewById(R.id.tvPhone);

        btnFollow = view.findViewById(R.id.btnFollow);
        btnChat = view.findViewById(R.id.btnChat);

        rvSanPhamShop = view.findViewById(R.id.rvSanPhamShop);

        btnMenu = view.findViewById(R.id.btnMenu);
        rvSanPhamShop.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(8, 8, 8, 8);
            }
        });


        // nhận dữ liệu
        if (getArguments() != null) {
            Shop shop = (Shop) getArguments().getSerializable("shop");

            if (shop != null) {
                imgShop.setImageResource(shop.getAvatar());
                tvTenShop.setText(shop.getTenShop());
                tvDanhGia.setText("⭐ " + shop.getDanhGia());
                tvSanPham.setText(shop.getSoSanPham() + "\nSản phẩm");
                tvFollower.setText(shop.getNguoiTheoDoi() + "\nTheo dõi");
                tvThamGia.setText(shop.getThoiGianThamGia() + "\nTham gia");
                tvDiaChi.setText("📍 " + shop.getDiaChi());
                tvMoTa.setText(shop.getMoTa());
                tvPhone.setText("📞 " + shop.getSoDienThoai());
            }
        }

        // ===== BUTTON =====

        btnFollow.setOnClickListener(v -> {
            btnFollow.setText("Đã theo dõi");
        });

        btnChat.setOnClickListener(v -> {
            // demo thôi
            tvMoTa.setText("👉 Chat với shop (demo)");
        });

        // ===== TẠO DATA =====
        listMoi = new ArrayList<>();

        listMoi.add(new SanPham(
                4,
                R.drawable.ic_ca_rot,
                "Cà rốt",
                18000,
                75,
                "Cà rốt tươi",
                "Đà Lạt",
                4.3f,
                "Shop A"
        ));

        listMoi.add(new SanPham(
                5,
                R.drawable.ic_dau_ha_lan,
                "Đậu hà lan",
                30000,
                60,
                "Đậu sạch",
                "Sapa",
                4.6f,
                "Shop A"
        ));

        listMoi.add(new SanPham(
                6,
                R.drawable.ic_khoai_tay,
                "Khoai tây",
                22000,
                150,
                "Khoai ngon",
                "Đà Lạt",
                4.8f,
                "Shop A"
        ));

        btnMenu.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(getContext(), v);
            popup.inflate(R.menu.menu_shop);

            popup.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.menu_share) {

                    String tenShop = tvTenShop.getText().toString();

                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");

                    String noiDung = "🌿 Khám phá shop nông sản này: " + tenShop +
                            "\n👉 Chất lượng cao - Giá tốt!" +
                            "\n📲 Tải app ngay để xem thêm!";

                    shareIntent.putExtra(Intent.EXTRA_TEXT, noiDung);

                    startActivity(Intent.createChooser(shareIntent, "Chia sẻ shop qua"));

                    return true;
                }
                else if (item.getItemId() == R.id.menu_report) {

                    String[] lyDo = {
                            "Lừa đảo",
                            "Hàng kém chất lượng",
                            "Spam / Quảng cáo",
                            "Khác"
                    };


                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Chọn lý do báo cáo");

                    builder.setItems(lyDo, (dialog, which) -> {

                        String selected = lyDo[which];

                        Toast.makeText(getContext(),
                                "Đã báo cáo: " + selected,
                                Toast.LENGTH_SHORT).show();
                    });

                    builder.setNegativeButton("Hủy", null);

                    builder.show();

                    return true;
                }
                else if (item.getItemId() == R.id.menu_rate) {

                    View dialogView = LayoutInflater.from(getContext())
                            .inflate(R.layout.dialog_danhgia, null);

                    RatingBar ratingBar = dialogView.findViewById(R.id.ratingBar);
                    EditText edtNhanXet = dialogView.findViewById(R.id.edtNhanXet);

                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setView(dialogView);

                    builder.setPositiveButton("Gửi", (dialog, which) -> {
                        float soSao = ratingBar.getRating();
                        String nhanXet = edtNhanXet.getText().toString();

                        Toast.makeText(getContext(),
                                "Bạn đã đánh giá: " + soSao + "⭐\n" + nhanXet,
                                Toast.LENGTH_LONG).show();
                    });

                    builder.setNegativeButton("Hủy", null);

                    builder.show();

                    return true;
                }
                return false;
            });

            popup.show();
        });

// ===== ADAPTER =====
        adapterMoi = new SanPhamMoiAdapter(getContext(), listMoi);

// ===== RECYCLER =====
        rvSanPhamShop.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rvSanPhamShop.setAdapter(adapterMoi);
        return view;

    }
}