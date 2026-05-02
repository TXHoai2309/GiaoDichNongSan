package com.example.giaodichnongsan.model;

import java.io.Serializable;

public class SellerRegistrationRequest implements Serializable {
    private String id;
    private String userId;
    private String hoTen;
    private String soDienThoai;
    private String email;
    private String cccd;
    private String diaChiCuTru;

    private String tenGianHang;
    private String moTaGianHang;
    private String diaChiKinhDoanh;
    private String loaiNongSan;
    private String nguonGocSanPham;
    private String giayChungNhanATTP;
    private String giayChungNhanVietGap;

    private String soTaiKhoan;
    private String tenChuTaiKhoan;

    private String trangThai;

    public SellerRegistrationRequest() {}

    public SellerRegistrationRequest(String hoTen, String soDienThoai, String email, String cccd,
                                     String diaChiCuTru, String tenGianHang, String moTaGianHang,
                                     String diaChiKinhDoanh, String loaiNongSan,
                                     String nguonGocSanPham, String giayChungNhanATTP,
                                     String giayChungNhanVietGap, String soTaiKhoan,
                                     String tenChuTaiKhoan, String trangThai) {
        this.hoTen = hoTen;
        this.soDienThoai = soDienThoai;
        this.email = email;
        this.cccd = cccd;
        this.diaChiCuTru = diaChiCuTru;
        this.tenGianHang = tenGianHang;
        this.moTaGianHang = moTaGianHang;
        this.diaChiKinhDoanh = diaChiKinhDoanh;
        this.loaiNongSan = loaiNongSan;
        this.nguonGocSanPham = nguonGocSanPham;
        this.giayChungNhanATTP = giayChungNhanATTP;
        this.giayChungNhanVietGap = giayChungNhanVietGap;
        this.soTaiKhoan = soTaiKhoan;
        this.tenChuTaiKhoan = tenChuTaiKhoan;
        this.trangThai = trangThai;
    }

    public String getId() { return id; }
    public String getUserId() { return userId; }
    public String getHoTen() { return hoTen; }
    public String getSoDienThoai() { return soDienThoai; }
    public String getEmail() { return email; }
    public String getCccd() { return cccd; }
    public String getDiaChiCuTru() { return diaChiCuTru; }
    public String getTenGianHang() { return tenGianHang; }
    public String getMoTaGianHang() { return moTaGianHang; }
    public String getDiaChiKinhDoanh() { return diaChiKinhDoanh; }
    public String getLoaiNongSan() { return loaiNongSan; }
    public String getNguonGocSanPham() { return nguonGocSanPham; }
    public String getGiayChungNhanATTP() { return giayChungNhanATTP; }
    public String getGiayChungNhanVietGap() { return giayChungNhanVietGap; }
    public String getSoTaiKhoan() { return soTaiKhoan; }
    public String getTenChuTaiKhoan() { return tenChuTaiKhoan; }
    public String getTrangThai() { return trangThai; }

    public void setId(String id) { this.id = id; }
    public void setUserId(String userId) { this.userId = userId; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
}
