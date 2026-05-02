package com.example.giaodichnongsan.model;

import java.io.Serializable;
import java.util.ArrayList;
public class DonHang implements Serializable {

    public static final String DANG_GIAO = "Đang giao";
    public static final String DA_GIAO   = "Đã giao";
    public static final String DA_HUY    = "Đã hủy";

    private String id;          // document ID Firestore
    private String userId;      // uid của user đặt hàng
    private ArrayList<GioHangItem> danhSachSP;
    private int tongTien;
    private String trangThai;
    private String ngayDat;
    private String tenNguoiMua;    // lấy từ users.hoTen
    private String sdtNguoiMua;    // lấy từ users.soDienThoai
    private String diaChiGiao;     // lấy từ users.diaChi
    private long ngayDatMillis;    // đã có trong Firestore

    public DonHang() {} // bắt buộc cho Firestore

    public DonHang(String userId, String tenNguoiMua, String sdtNguoiMua,
                   String diaChiGiao, ArrayList<GioHangItem> danhSachSP,
                   int tongTien, String trangThai, String ngayDat, long ngayDatMillis) {
        this.userId       = userId;
        this.tenNguoiMua  = tenNguoiMua;
        this.sdtNguoiMua  = sdtNguoiMua;
        this.diaChiGiao   = diaChiGiao;
        this.danhSachSP   = danhSachSP;
        this.tongTien     = tongTien;
        this.trangThai    = trangThai;
        this.ngayDat      = ngayDat;
        this.ngayDatMillis = ngayDatMillis;
    }

    // Getter
    public String getId()                          { return id; }
    public String getUserId()                      { return userId; }
    public ArrayList<GioHangItem> getDanhSachSP()  { return danhSachSP; }
    public int getTongTien()                       { return tongTien; }
    public String getTrangThai()                   { return trangThai; }
    public String getNgayDat()                     { return ngayDat; }

    // Setter
    public void setId(String id)                              { this.id = id; }
    public void setUserId(String userId)                      { this.userId = userId; }
    public void setDanhSachSP(ArrayList<GioHangItem> list)    { this.danhSachSP = list; }
    public void setTongTien(int tongTien)                     { this.tongTien = tongTien; }
    public void setTrangThai(String trangThai)                { this.trangThai = trangThai; }
    public void setNgayDat(String ngayDat)                    { this.ngayDat = ngayDat; }
    public String getTenNguoiMua()                       { return tenNguoiMua; }
    public String getSdtNguoiMua()                       { return sdtNguoiMua; }
    public String getDiaChiGiao()                        { return diaChiGiao; }
    public long getNgayDatMillis()                       { return ngayDatMillis; }
    public void setTenNguoiMua(String tenNguoiMua)       { this.tenNguoiMua = tenNguoiMua; }
    public void setSdtNguoiMua(String sdtNguoiMua)       { this.sdtNguoiMua = sdtNguoiMua; }
    public void setDiaChiGiao(String diaChiGiao)         { this.diaChiGiao = diaChiGiao; }
    public void setNgayDatMillis(long ngayDatMillis)     { this.ngayDatMillis = ngayDatMillis; }
}