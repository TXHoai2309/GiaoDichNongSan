package com.example.giaodichnongsan.ui.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giaodichnongsan.R;
import com.example.giaodichnongsan.adapter.SanPhamMoiAdapter;
import com.example.giaodichnongsan.model.SanPham;
import com.example.giaodichnongsan.model.Shop;
import com.example.giaodichnongsan.viewmodel.ChiTietShopViewModel;

import java.util.ArrayList;

public class ChiTietShopFragment extends Fragment {

    // ===== VIEW =====
    private ImageView imgShop, btnMenu;
    private TextView tvTenShop, tvDanhGia, tvSanPham, tvFollower,
            tvThamGia, tvDiaChi, tvMoTa, tvPhone;
    private Button btnFollow, btnChat;
    private RecyclerView rvSanPhamShop;

    // ===== DATA =====
    private int shopId;
    private Shop shop;
    private ArrayList<SanPham> listSanPham;
    private SanPhamMoiAdapter adapter;

    // ===== VIEWMODEL =====
    private ChiTietShopViewModel viewModel;

    public ChiTietShopFragment() {}

    // ===== NEW INSTANCE =====
    public static ChiTietShopFragment newInstance(int shopId) {
        ChiTietShopFragment fragment = new ChiTietShopFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("shopId", shopId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chi_tiet_shop, container, false);

        initView(view);
        initData();
        initViewModel();
        setupRecyclerView();
        setupEvents();
        observeData();

        return view;
    }

    // ===== INIT VIEW =====
    private void initView(View view) {
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
        btnMenu = view.findViewById(R.id.btnMenu);

        rvSanPhamShop = view.findViewById(R.id.rvSanPhamShop);
    }

    // ===== INIT DATA =====
    private void initData() {
        if (getArguments() != null) {
            shopId = getArguments().getInt("shopId", 0);
        }
    }

    // ===== INIT VIEWMODEL =====
    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(ChiTietShopViewModel.class);
    }

    // ===== OBSERVE =====
    private void observeData() {

        if (shopId == 0) {
            Toast.makeText(getContext(), "Lỗi: không có shopId", Toast.LENGTH_SHORT).show();
            return;
        }

        viewModel.getShop().observe(getViewLifecycleOwner(), s -> {
            if (s != null) {
                shop = s;
                bindShop();
            }
        });

        viewModel.getSanPhamShop().observe(getViewLifecycleOwner(), list -> {
            if (list != null) {
                listSanPham = new ArrayList<>(list);
                adapter.setData(listSanPham);
            }
        });

        viewModel.loadShop(shopId);
    }

    // ===== BIND SHOP =====
    private void bindShop() {
        if (shop == null) return;

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

    // ===== RECYCLER =====
    private void setupRecyclerView() {

        adapter = new SanPhamMoiAdapter(getContext(), new ArrayList<>());

        rvSanPhamShop.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rvSanPhamShop.setAdapter(adapter);

        if (rvSanPhamShop.getItemDecorationCount() == 0) {
            rvSanPhamShop.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    outRect.set(8, 8, 8, 8);
                }
            });
        }
    }

    // ===== EVENTS =====
    private void setupEvents() {

        btnFollow.setOnClickListener(v -> btnFollow.setText("Đã theo dõi"));

        btnChat.setOnClickListener(v ->
                tvMoTa.setText("👉 Chat với shop (demo)")
        );

        btnMenu.setOnClickListener(v -> showMenu());
    }

    // ===== MENU =====
    private void showMenu() {

        PopupMenu popup = new PopupMenu(getContext(), btnMenu);
        popup.inflate(R.menu.menu_shop);

        popup.setOnMenuItemClickListener(item -> {

            if (item.getItemId() == R.id.menu_share) {

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");

                intent.putExtra(Intent.EXTRA_TEXT,
                        "🌿 Shop: " + tvTenShop.getText());

                startActivity(Intent.createChooser(intent, "Chia sẻ"));
                return true;
            }

            else if (item.getItemId() == R.id.menu_report) {

                String[] lyDo = {"Lừa đảo", "Kém chất lượng", "Spam", "Khác"};

                new AlertDialog.Builder(getContext())
                        .setTitle("Báo cáo shop")
                        .setItems(lyDo, (d, i) ->
                                Toast.makeText(getContext(),
                                        "Đã báo cáo: " + lyDo[i],
                                        Toast.LENGTH_SHORT).show()
                        )
                        .setNegativeButton("Hủy", null)
                        .show();

                return true;
            }

            else if (item.getItemId() == R.id.menu_rate) {

                View dialog = LayoutInflater.from(getContext())
                        .inflate(R.layout.dialog_danhgia, null);

                RatingBar ratingBar = dialog.findViewById(R.id.ratingBar);
                EditText edt = dialog.findViewById(R.id.edtNhanXet);

                new AlertDialog.Builder(getContext())
                        .setView(dialog)
                        .setPositiveButton("Gửi", (d, i) -> {
                            Toast.makeText(getContext(),
                                    "Đánh giá: " + ratingBar.getRating(),
                                    Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("Hủy", null)
                        .show();

                return true;
            }

            return false;
        });

        popup.show();
    }
}