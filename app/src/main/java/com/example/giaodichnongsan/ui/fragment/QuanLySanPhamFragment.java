package com.example.giaodichnongsan.ui.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.giaodichnongsan.R;
import com.example.giaodichnongsan.model.SanPham;
import com.example.giaodichnongsan.utils.ImageUrlHelper;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class QuanLySanPhamFragment extends Fragment {

    private static final String ARG_SHOP_ID = "shop_id";
    private static final String ARG_TEN_SHOP = "ten_shop";

    private ImageView btnBack;
    private EditText edtSearchSanPham;
    private Button btnThemSanPham;
    private TextView tvSoLuongSanPham, tvEmpty;
    private LinearLayout layoutDanhSachSanPham;

    private final ArrayList<SanPham> allProducts = new ArrayList<>();
    private FirebaseFirestore db;
    private String shopId;
    private String tenShop;

    public QuanLySanPhamFragment() {}

    public static QuanLySanPhamFragment newInstance(String shopId, String tenShop) {
        QuanLySanPhamFragment fragment = new QuanLySanPhamFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SHOP_ID, shopId);
        args.putString(ARG_TEN_SHOP, tenShop);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            shopId = getArguments().getString(ARG_SHOP_ID, "");
            tenShop = getArguments().getString(ARG_TEN_SHOP, "");
        }
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quan_ly_san_pham, container, false);

        btnBack = view.findViewById(R.id.btnBack);
        edtSearchSanPham = view.findViewById(R.id.edtSearchSanPham);
        btnThemSanPham = view.findViewById(R.id.btnThemSanPham);
        tvSoLuongSanPham = view.findViewById(R.id.tvSoLuongSanPham);
        tvEmpty = view.findViewById(R.id.tvEmpty);
        layoutDanhSachSanPham = view.findViewById(R.id.layoutDanhSachSanPham);

        btnBack.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());
        btnThemSanPham.setOnClickListener(v -> hienFormSanPham(null));

        edtSearchSanPham.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                hienThiDanhSach(s.toString().trim().toLowerCase());
            }
        });

        loadSanPham();
        return view;
    }

    private void loadSanPham() {
        if (TextUtils.isEmpty(shopId)) {
            tvSoLuongSanPham.setText("Không tìm thấy cửa hàng");
            tvEmpty.setVisibility(View.VISIBLE);
            return;
        }

        tvSoLuongSanPham.setText("Đang tải sản phẩm...");
        db.collection("sanpham")
                .whereEqualTo("shopId", shopId)
                .get()
                .addOnSuccessListener(snapshot -> {
                    if (!isAdded()) return;

                    allProducts.clear();
                    for (com.google.firebase.firestore.DocumentSnapshot doc : snapshot.getDocuments()) {
                        SanPham sp = doc.toObject(SanPham.class);
                        if (sp != null) {
                            sp.setId(doc.getId());
                            allProducts.add(sp);
                        }
                    }
                    hienThiDanhSach(edtSearchSanPham.getText().toString().trim().toLowerCase());
                })
                .addOnFailureListener(e -> {
                    if (!isAdded()) return;
                    tvSoLuongSanPham.setText("Không tải được sản phẩm");
                    Toast.makeText(requireContext(), "Lỗi: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }

    private void hienThiDanhSach(String tuKhoa) {
        layoutDanhSachSanPham.removeAllViews();
        int count = 0;

        for (SanPham sp : allProducts) {
            if (!khopTimKiem(sp, tuKhoa)) continue;
            layoutDanhSachSanPham.addView(taoItemSanPham(sp));
            count++;
        }

        tvSoLuongSanPham.setText(count + " sản phẩm");
        tvEmpty.setVisibility(count == 0 ? View.VISIBLE : View.GONE);
    }

    private boolean khopTimKiem(SanPham sp, String tuKhoa) {
        if (tuKhoa.isEmpty()) return true;
        String text = (sp.getTen() + " " + sp.getDanhMuc() + " " + sp.getNguonGoc()).toLowerCase();
        return text.contains(tuKhoa);
    }

    private View taoItemSanPham(SanPham sp) {
        LinearLayout item = new LinearLayout(requireContext());
        item.setOrientation(LinearLayout.HORIZONTAL);
        item.setGravity(android.view.Gravity.CENTER_VERTICAL);
        item.setPadding(dp(12), dp(12), dp(12), dp(12));
        item.setBackgroundColor(android.graphics.Color.parseColor("#FFF8F4"));

        LinearLayout.LayoutParams itemParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        itemParams.setMargins(0, 0, 0, dp(10));
        item.setLayoutParams(itemParams);

        ImageView img = new ImageView(requireContext());
        img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        String imageUrl = ImageUrlHelper.normalize(sp.getImageUrl());
        if (!imageUrl.isEmpty()) {
            Glide.with(this).load(imageUrl).placeholder(R.drawable.ic_product).error(R.drawable.ic_product).into(img);
        } else {
            img.setImageResource(R.drawable.ic_product);
        }
        item.addView(img, new LinearLayout.LayoutParams(dp(58), dp(58)));

        LinearLayout content = new LinearLayout(requireContext());
        content.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams contentParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        contentParams.setMargins(dp(12), 0, dp(8), 0);

        TextView tvTen = new TextView(requireContext());
        tvTen.setText(sp.getTen());
        tvTen.setTextColor(android.graphics.Color.parseColor("#4A423D"));
        tvTen.setTextSize(16);
        tvTen.setTypeface(null, android.graphics.Typeface.BOLD);

        TextView tvInfo = new TextView(requireContext());
        tvInfo.setText(formatGia(sp.getGia()) + " | Đã bán " + sp.getDaBan() + "\n" + giaTri(sp.getDanhMuc(), "Chưa phân loại"));
        tvInfo.setTextColor(android.graphics.Color.parseColor("#6E625B"));
        tvInfo.setTextSize(13);

        content.addView(tvTen);
        content.addView(tvInfo);
        item.addView(content, contentParams);

        Button btnSua = new Button(requireContext());
        btnSua.setText("Sửa");
        btnSua.setTextSize(12);
        btnSua.setAllCaps(false);
        btnSua.setTextColor(android.graphics.Color.WHITE);
        btnSua.setBackgroundTintList(android.content.res.ColorStateList.valueOf(android.graphics.Color.parseColor("#68A25A")));
        item.addView(btnSua, new LinearLayout.LayoutParams(dp(64), dp(40)));

        btnSua.setOnClickListener(v -> hienFormSanPham(sp));
        item.setOnClickListener(v -> hienChiTietSanPham(sp));

        return item;
    }

    private void hienFormSanPham(SanPham sanPham) {
        boolean isEdit = sanPham != null;

        LinearLayout form = new LinearLayout(requireContext());
        form.setOrientation(LinearLayout.VERTICAL);
        int padding = dp(18);
        form.setPadding(padding, dp(8), padding, 0);

        ScrollView formScroll = new ScrollView(requireContext());
        formScroll.setFillViewport(false);
        formScroll.addView(form, new ScrollView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        EditText edtTen = taoInput("Tên sản phẩm", InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        EditText edtGia = taoInput("Giá", InputType.TYPE_CLASS_NUMBER);
        EditText edtDanhMuc = taoInput("Danh mục", InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        EditText edtNguonGoc = taoInput("Nguồn gốc", InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        EditText edtMoTa = taoInput("Mô tả", InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        EditText edtImageUrl = taoInput("Link ảnh hoặc data:image/base64", InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        edtImageUrl.setSingleLine(false);
        edtImageUrl.setMinLines(3);
        edtImageUrl.setMaxLines(8);
        edtImageUrl.setHorizontallyScrolling(false);
        edtImageUrl.setGravity(android.view.Gravity.TOP | android.view.Gravity.START);

        if (isEdit) {
            edtTen.setText(sanPham.getTen());
            edtGia.setText(String.valueOf(sanPham.getGia()));
            edtDanhMuc.setText(sanPham.getDanhMuc());
            edtNguonGoc.setText(sanPham.getNguonGoc());
            edtMoTa.setText(sanPham.getMoTa());
            edtImageUrl.setText(sanPham.getImageUrl());
            edtImageUrl.setSelection(edtImageUrl.length());
        }

        form.addView(edtTen);
        form.addView(edtGia);
        form.addView(edtDanhMuc);
        form.addView(edtNguonGoc);
        form.addView(edtMoTa);
        form.addView(edtImageUrl);

        AlertDialog dialog = new AlertDialog.Builder(requireContext())
                .setTitle(isEdit ? "Sửa sản phẩm" : "Thêm sản phẩm")
                .setView(formScroll)
                .setNegativeButton("Hủy", null)
                .setNeutralButton(isEdit ? "Xóa" : null, null)
                .setPositiveButton("Lưu", null)
                .create();

        dialog.setOnShowListener(d -> {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
                Map<String, Object> data = layDuLieuForm(edtTen, edtGia, edtDanhMuc, edtNguonGoc, edtMoTa, edtImageUrl);
                if (data == null) return;

                if (isEdit) capNhatSanPham(sanPham, data, dialog);
                else themSanPham(data, dialog);
            });

            if (isEdit) {
                dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener(v -> xacNhanXoa(sanPham, dialog));
            }
        });

        dialog.show();
    }

    private EditText taoInput(String hint, int inputType) {
        EditText editText = new EditText(requireContext());
        editText.setHint(hint);
        editText.setInputType(inputType);
        editText.setSingleLine((inputType & InputType.TYPE_TEXT_FLAG_MULTI_LINE) == 0);
        editText.setPadding(0, dp(8), 0, dp(8));
        return editText;
    }

    private Map<String, Object> layDuLieuForm(EditText edtTen, EditText edtGia, EditText edtDanhMuc,
                                             EditText edtNguonGoc, EditText edtMoTa, EditText edtImageUrl) {
        String ten = text(edtTen);
        String giaText = text(edtGia);
        String danhMuc = text(edtDanhMuc);
        String nguonGoc = text(edtNguonGoc);
        String moTa = text(edtMoTa);
        String imageUrlInput = text(edtImageUrl);
        String imageUrl = ImageUrlHelper.normalize(imageUrlInput);

        if (ten.isEmpty()) {
            edtTen.setError("Vui lòng nhập tên sản phẩm");
            return null;
        }
        if (giaText.isEmpty()) {
            edtGia.setError("Vui lòng nhập giá");
            return null;
        }

        int gia;
        try {
            gia = Integer.parseInt(giaText);
        } catch (NumberFormatException e) {
            edtGia.setError("Giá không hợp lệ");
            return null;
        }

        if (gia <= 0) {
            edtGia.setError("Giá phải lớn hơn 0");
            return null;
        }
        if (!imageUrlInput.isEmpty() && !ImageUrlHelper.isSupportedImageSource(imageUrlInput)) {
            edtImageUrl.setError("Ảnh phải là http://, https:// hoặc data:image/base64");
            return null;
        }

        Map<String, Object> data = new HashMap<>();
        data.put("ten", ten);
        data.put("gia", gia);
        data.put("danhMuc", danhMuc);
        data.put("nguonGoc", nguonGoc);
        data.put("moTa", moTa);
        data.put("imageUrl", imageUrl);
        data.put("shopId", shopId);
        data.put("tenShop", tenShop);
        data.put("noiBat", false);
        data.put("danhGia", 0);
        return data;
    }

    private void themSanPham(Map<String, Object> data, AlertDialog dialog) {
        data.put("daBan", 0);
        db.collection("sanpham")
                .add(data)
                .addOnSuccessListener(documentReference -> {
                    dialog.dismiss();
                    Toast.makeText(requireContext(), "Đã thêm sản phẩm", Toast.LENGTH_SHORT).show();
                    loadSanPham();
                })
                .addOnFailureListener(e -> Toast.makeText(requireContext(), "Lỗi: " + e.getMessage(), Toast.LENGTH_LONG).show());
    }

    private void capNhatSanPham(SanPham sanPham, Map<String, Object> data, AlertDialog dialog) {
        db.collection("sanpham")
                .document(sanPham.getId())
                .update(data)
                .addOnSuccessListener(unused -> {
                    dialog.dismiss();
                    Toast.makeText(requireContext(), "Đã cập nhật sản phẩm", Toast.LENGTH_SHORT).show();
                    loadSanPham();
                })
                .addOnFailureListener(e -> Toast.makeText(requireContext(), "Lỗi: " + e.getMessage(), Toast.LENGTH_LONG).show());
    }

    private void xacNhanXoa(SanPham sanPham, AlertDialog editDialog) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Xóa sản phẩm?")
                .setMessage("Bạn có chắc muốn xóa \"" + sanPham.getTen() + "\" không?")
                .setNegativeButton("Hủy", null)
                .setPositiveButton("Xóa", (dialog, which) -> xoaSanPham(sanPham, editDialog))
                .show();
    }

    private void xoaSanPham(SanPham sanPham, AlertDialog editDialog) {
        db.collection("sanpham")
                .document(sanPham.getId())
                .delete()
                .addOnSuccessListener(unused -> {
                    editDialog.dismiss();
                    Toast.makeText(requireContext(), "Đã xóa sản phẩm", Toast.LENGTH_SHORT).show();
                    loadSanPham();
                })
                .addOnFailureListener(e -> Toast.makeText(requireContext(), "Lỗi: " + e.getMessage(), Toast.LENGTH_LONG).show());
    }

    private void hienChiTietSanPham(SanPham sp) {
        String info = "Tên: " + giaTri(sp.getTen(), "Chưa có")
                + "\nGiá: " + formatGia(sp.getGia())
                + "\nDanh mục: " + giaTri(sp.getDanhMuc(), "Chưa có")
                + "\nNguồn gốc: " + giaTri(sp.getNguonGoc(), "Chưa có")
                + "\nĐã bán: " + sp.getDaBan()
                + "\nMô tả: " + giaTri(sp.getMoTa(), "Chưa có");

        new AlertDialog.Builder(requireContext())
                .setTitle("Chi tiết sản phẩm")
                .setMessage(info)
                .setNegativeButton("Đóng", null)
                .setPositiveButton("Sửa", (dialog, which) -> hienFormSanPham(sp))
                .show();
    }

    private String text(EditText editText) {
        return editText.getText().toString().trim();
    }

    private String formatGia(int gia) {
        return NumberFormat.getNumberInstance(new Locale("vi", "VN")).format(gia) + "đ";
    }

    private String giaTri(String value, String fallback) {
        return value == null || value.trim().isEmpty() ? fallback : value.trim();
    }

    private int dp(int value) {
        return (int) (value * getResources().getDisplayMetrics().density);
    }
}
