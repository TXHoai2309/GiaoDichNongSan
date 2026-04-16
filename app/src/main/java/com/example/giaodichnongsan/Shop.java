package com.example.giaodichnongsan;

import java.io.Serializable;

public class Shop implements Serializable {

    private int id; // 🔥 ID SHOP (QUAN TRỌNG)
    private String tenShop;
    private int avatar;
    private float danhGia;
    private int soSanPham;
    private int nguoiTheoDoi;
    private String thoiGianThamGia;
    private String diaChi;
    private String soDienThoai;
    private String moTa;

    public Shop(int id, String tenShop, int avatar, float danhGia, int soSanPham,
                int nguoiTheoDoi, String thoiGianThamGia,
                String diaChi, String soDienThoai, String moTa) {

        this.id = id;
        this.tenShop = tenShop;
        this.avatar = avatar;
        this.danhGia = danhGia;
        this.soSanPham = soSanPham;
        this.nguoiTheoDoi = nguoiTheoDoi;
        this.thoiGianThamGia = thoiGianThamGia;
        this.diaChi = diaChi;
        this.soDienThoai = soDienThoai;
        this.moTa = moTa;
    }

    // ===== GETTER =====
    public int getId() { return id; }
    public String getTenShop() { return tenShop; }
    public int getAvatar() { return avatar; }
    public float getDanhGia() { return danhGia; }
    public int getSoSanPham() { return soSanPham; }
    public int getNguoiTheoDoi() { return nguoiTheoDoi; }
    public String getThoiGianThamGia() { return thoiGianThamGia; }
    public String getDiaChi() { return diaChi; }
    public String getSoDienThoai() { return soDienThoai; }
    public String getMoTa() { return moTa; }
}