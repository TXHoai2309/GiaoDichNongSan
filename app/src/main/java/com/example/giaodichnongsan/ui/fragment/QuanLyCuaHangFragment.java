package com.example.giaodichnongsan.ui.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.giaodichnongsan.R;
import com.example.giaodichnongsan.data.repository.ShopRepository;
import com.example.giaodichnongsan.model.Shop;

public class QuanLyCuaHangFragment extends Fragment {

    ImageView btnBack, btnMenu;

    LinearLayout itemTenGianHang, itemQuanLyDonHang1, itemQuanLyKhuyenMai;
    LinearLayout itemThongTinSanPham, itemQuanLyDonHang2, itemXuLyYeuCau;
    LinearLayout itemDanhGiaPhanHoi, itemThongKeDoanhThu;
    LinearLayout itemCaiDatShop, itemTrungTamTroGiup;

    private ShopRepository shopRepository;
    private Shop currentShop;

    private int currentShopId = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_quan_ly_cua_hang, container, false);

        shopRepository = new ShopRepository();
        currentShop = shopRepository.getShopById(currentShopId);

        btnBack = view.findViewById(R.id.btnBackQuanLyShop);
        btnMenu = view.findViewById(R.id.btnMenuQuanLyShop);

        itemTenGianHang = view.findViewById(R.id.itemTenGianHang);
        itemQuanLyDonHang1 = view.findViewById(R.id.itemQuanLyDonHang1);
        itemQuanLyKhuyenMai = view.findViewById(R.id.itemQuanLyKhuyenMai);

        itemThongTinSanPham = view.findViewById(R.id.itemThongTinSanPham);
        itemQuanLyDonHang2 = view.findViewById(R.id.itemQuanLyDonHang2);
        itemXuLyYeuCau = view.findViewById(R.id.itemXuLyYeuCau);
        itemDanhGiaPhanHoi = view.findViewById(R.id.itemDanhGiaPhanHoi);
        itemThongKeDoanhThu = view.findViewById(R.id.itemThongKeDoanhThu);

        itemCaiDatShop = view.findViewById(R.id.itemCaiDatShop);
        itemTrungTamTroGiup = view.findViewById(R.id.itemTrungTamTroGiup);

        btnBack.setOnClickListener(v ->
                requireActivity().getSupportFragmentManager().popBackStack()
        );

        btnMenu.setOnClickListener(v -> hienMenuTong());

        itemTenGianHang.setOnClickListener(v -> suaTenShop());

        itemThongTinSanPham.setOnClickListener(v -> hienThongTinShop());

        itemQuanLyDonHang1.setOnClickListener(v -> quanLyDonHang());
        itemQuanLyDonHang2.setOnClickListener(v -> quanLyDonHang());

        itemQuanLyKhuyenMai.setOnClickListener(v -> quanLyKhuyenMai());

        itemXuLyYeuCau.setOnClickListener(v -> xuLyYeuCauDangKy());

        itemDanhGiaPhanHoi.setOnClickListener(v ->
                hienThongTin("Đánh giá & phản hồi",
                        "Đánh giá hiện tại: " + currentShop.getDanhGia() +
                                "\nChức năng: xem đánh giá và phản hồi khách hàng.")
        );

        itemThongKeDoanhThu.setOnClickListener(v ->
                hienThongTin("Thống kê & doanh thu",
                        "Số sản phẩm: " + currentShop.getSoSanPham() +
                                "\nNgười theo dõi: " + currentShop.getNguoiTheoDoi() +
                                "\nChức năng: thống kê hoạt động kinh doanh.")
        );

        itemCaiDatShop.setOnClickListener(v -> caiDatShop());

        itemTrungTamTroGiup.setOnClickListener(v ->
                hienThongTin("Trung tâm trợ giúp",
                        "Hỗ trợ người bán trong quá trình quản lý cửa hàng.")
        );

        return view;
    }

    private void hienMenuTong() {
        String[] options = {
                "Xem thông tin shop",
                "Sửa tên shop",
                "Quản lý đơn hàng",
                "Quản lý khuyến mãi",
                "Cài đặt shop"
        };

        new AlertDialog.Builder(requireContext())
                .setTitle("Menu quản lý cửa hàng")
                .setItems(options, (dialog, which) -> {
                    if (which == 0) hienThongTinShop();
                    else if (which == 1) suaTenShop();
                    else if (which == 2) quanLyDonHang();
                    else if (which == 3) quanLyKhuyenMai();
                    else if (which == 4) caiDatShop();
                })
                .show();
    }

    private void hienThongTinShop() {
        hienThongTin(
                "Thông tin cửa hàng",
                "ID shop: " + currentShop.getId() +
                        "\nTên shop: " + currentShop.getTenShop() +
                        "\nĐánh giá: " + currentShop.getDanhGia() +
                        "\nSố sản phẩm: " + currentShop.getSoSanPham() +
                        "\nNgười theo dõi: " + currentShop.getNguoiTheoDoi() +
                        "\nThời gian tham gia: " + currentShop.getThoiGianThamGia() +
                        "\nĐịa chỉ: " + currentShop.getDiaChi() +
                        "\nSố điện thoại: " + currentShop.getSoDienThoai() +
                        "\nMô tả: " + currentShop.getMoTa()
        );
    }

    private void suaTenShop() {
        EditText input = new EditText(requireContext());
        input.setHint("Nhập tên shop mới");
        input.setText(currentShop.getTenShop());

        new AlertDialog.Builder(requireContext())
                .setTitle("Sửa tên shop")
                .setView(input)
                .setPositiveButton("Lưu", (dialog, which) -> {
                    String tenMoi = input.getText().toString().trim();

                    if (tenMoi.isEmpty()) {
                        Toast.makeText(getContext(), "Tên shop không được để trống", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    currentShop.setTenShop(tenMoi);

                    Toast.makeText(getContext(), "Đã cập nhật tên shop", Toast.LENGTH_SHORT).show();
                    hienThongTinShop();
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void quanLyDonHang() {
        String[] options = {
                "Xem đơn hàng",
                "Xác nhận đơn hàng",
                "Từ chối đơn hàng",
                "Cập nhật trạng thái giao hàng"
        };

        new AlertDialog.Builder(requireContext())
                .setTitle("Quản lý đơn hàng")
                .setItems(options, (dialog, which) -> {
                    if (which == 0) {
                        hienThongTin("Danh sách đơn hàng",
                                "Đơn chờ xác nhận\nĐơn đang chuẩn bị\nĐơn đang giao\nĐơn hoàn tất");
                    } else if (which == 1) {
                        Toast.makeText(getContext(), "Đã xác nhận đơn hàng", Toast.LENGTH_SHORT).show();
                    } else if (which == 2) {
                        Toast.makeText(getContext(), "Đã từ chối đơn hàng", Toast.LENGTH_SHORT).show();
                    } else if (which == 3) {
                        capNhatTrangThaiDonHang();
                    }
                })
                .show();
    }

    private void capNhatTrangThaiDonHang() {
        String[] trangThai = {
                "Đang chuẩn bị",
                "Đang giao",
                "Đã giao",
                "Hoàn tất"
        };

        new AlertDialog.Builder(requireContext())
                .setTitle("Cập nhật trạng thái")
                .setItems(trangThai, (dialog, which) ->
                        Toast.makeText(getContext(), "Đã cập nhật: " + trangThai[which], Toast.LENGTH_SHORT).show()
                )
                .show();
    }

    private void quanLyKhuyenMai() {
        String[] options = {
                "Xem khuyến mãi",
                "Tạo voucher",
                "Sửa voucher",
                "Xóa voucher"
        };

        new AlertDialog.Builder(requireContext())
                .setTitle("Quản lý khuyến mãi")
                .setItems(options, (dialog, which) -> {
                    if (which == 0) {
                        hienThongTin("Danh sách khuyến mãi",
                                "Giảm giá theo đơn hàng\nMiễn phí vận chuyển\nVoucher khách hàng mới");
                    } else if (which == 1) {
                        nhapNoiDung("Tạo voucher", "Nhập tên voucher", "Đã tạo voucher");
                    } else if (which == 2) {
                        nhapNoiDung("Sửa voucher", "Nhập nội dung voucher mới", "Đã sửa voucher");
                    } else if (which == 3) {
                        xacNhan("Xóa voucher", "Bạn có chắc muốn xóa voucher này không?", "Đã xóa voucher");
                    }
                })
                .show();
    }

    private void xuLyYeuCauDangKy() {
        String[] options = {
                "Xem yêu cầu chờ duyệt",
                "Duyệt yêu cầu",
                "Từ chối yêu cầu"
        };

        new AlertDialog.Builder(requireContext())
                .setTitle("Xử lý yêu cầu đăng ký")
                .setItems(options, (dialog, which) -> {
                    if (which == 0) {
                        hienThongTin("Yêu cầu đăng ký",
                                "Danh sách yêu cầu đăng ký bán hàng đang chờ xử lý.");
                    } else if (which == 1) {
                        Toast.makeText(getContext(), "Đã duyệt yêu cầu", Toast.LENGTH_SHORT).show();
                    } else if (which == 2) {
                        Toast.makeText(getContext(), "Đã từ chối yêu cầu", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }

    private void caiDatShop() {
        String[] options = {
                "Sửa số điện thoại",
                "Sửa địa chỉ",
                "Sửa mô tả shop"
        };

        new AlertDialog.Builder(requireContext())
                .setTitle("Cài đặt shop")
                .setItems(options, (dialog, which) -> {
                    if (which == 0) suaSoDienThoai();
                    else if (which == 1) suaDiaChi();
                    else if (which == 2) suaMoTa();
                })
                .show();
    }

    private void suaSoDienThoai() {
        EditText input = new EditText(requireContext());
        input.setHint("Nhập số điện thoại mới");
        input.setText(currentShop.getSoDienThoai());

        new AlertDialog.Builder(requireContext())
                .setTitle("Sửa số điện thoại")
                .setView(input)
                .setPositiveButton("Lưu", (dialog, which) -> {
                    String sdtMoi = input.getText().toString().trim();

                    if (sdtMoi.isEmpty()) {
                        Toast.makeText(getContext(), "Số điện thoại không được để trống", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    currentShop.setSoDienThoai(sdtMoi);
                    Toast.makeText(getContext(), "Đã cập nhật số điện thoại", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void suaDiaChi() {
        EditText input = new EditText(requireContext());
        input.setHint("Nhập địa chỉ mới");
        input.setText(currentShop.getDiaChi());

        new AlertDialog.Builder(requireContext())
                .setTitle("Sửa địa chỉ")
                .setView(input)
                .setPositiveButton("Lưu", (dialog, which) -> {
                    String diaChiMoi = input.getText().toString().trim();

                    if (diaChiMoi.isEmpty()) {
                        Toast.makeText(getContext(), "Địa chỉ không được để trống", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    currentShop.setDiaChi(diaChiMoi);
                    Toast.makeText(getContext(), "Đã cập nhật địa chỉ", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void suaMoTa() {
        EditText input = new EditText(requireContext());
        input.setHint("Nhập mô tả shop");
        input.setText(currentShop.getMoTa());

        new AlertDialog.Builder(requireContext())
                .setTitle("Sửa mô tả shop")
                .setView(input)
                .setPositiveButton("Lưu", (dialog, which) -> {
                    String moTaMoi = input.getText().toString().trim();

                    if (moTaMoi.isEmpty()) {
                        Toast.makeText(getContext(), "Mô tả không được để trống", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    currentShop.setMoTa(moTaMoi);
                    Toast.makeText(getContext(), "Đã cập nhật mô tả shop", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void nhapNoiDung(String title, String hint, String successMessage) {
        EditText input = new EditText(requireContext());
        input.setHint(hint);

        new AlertDialog.Builder(requireContext())
                .setTitle(title)
                .setView(input)
                .setPositiveButton("Lưu", (dialog, which) -> {
                    String value = input.getText().toString().trim();

                    if (value.isEmpty()) {
                        Toast.makeText(getContext(), "Không được để trống", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), successMessage + ": " + value, Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void xacNhan(String title, String message, String successMessage) {
        new AlertDialog.Builder(requireContext())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Đồng ý", (dialog, which) ->
                        Toast.makeText(getContext(), successMessage, Toast.LENGTH_SHORT).show()
                )
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void hienThongTin(String tieuDe, String noiDung) {
        new AlertDialog.Builder(requireContext())
                .setTitle(tieuDe)
                .setMessage(noiDung)
                .setPositiveButton("Đóng", null)
                .show();
    }
}