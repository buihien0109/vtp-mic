package com.example.demovtpmic.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@ToString
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "hoso_baohiems")
public class HoSoBaoHiem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "ma_hoso")
    String maHoSo;

    @Column(name = "ma_vandon")
    String maVanDon;

    @Column(name = "ma_bienban")
    String maBienBan;

    @Column(name = "ma_hosoboithuong")
    String maHoSoBoiThuong;

    @Column(name = "ma_doitac")
    String maDoiTac;

    @Column(name = "ma_khachhang")
    String maKhachHang;

    @Column(name = "gia_tri")
    Long giaTri;

    @Column(name = "thoigian_guihang")
    LocalDateTime thoiGianGuiHang;

    @Column(name = "nguyennhan_tonthat")
    String nguyenNhanTonThat;

    @Column(name = "trangthai_vandon")
    String trangThaiVanDon;

    @Column(name = "tien_boithuong_vtp")
    Long tienBoiThuongVtp;

    @Column(name = "tien_boithuong_doitac")
    Long tienBoiThuongDoiTac;

    @Column(name = "trangthai_doitac_ketluan")
    String trangThaiDoiTacKetLuan;

    @Column(name = "thoigian_doitac_ketluan")
    LocalDateTime thoiGianDoiTacKetLuan;

    @Column(name = "created_by")
    String createdBy;

    @Column(name = "created_date")
    LocalDateTime createdDate;

    @Column(name = "last_modified_by")
    String lastModifiedBy;

    @Column(name = "last_modified_date")
    LocalDateTime lastModifiedDate;

    @PrePersist
    public void prePersist() {
        this.createdDate = LocalDateTime.now();
        this.lastModifiedDate = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.lastModifiedDate = LocalDateTime.now();
    }
}
