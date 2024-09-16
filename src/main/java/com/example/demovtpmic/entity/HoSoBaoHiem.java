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

    String maHoSo;
    String maVanDon;
    String maBienBan;
    String maHoSoBoiThuong;
    String maDoiTac;
    String maKhachHang;
    Long giaTri;
    LocalDateTime thoiGianGuiHang;
    String nguyenNhanTonThat;
    String trangThaiVanDon;
    Long tienBoiThuongVtp;
    Long tienBoiThuongDoiTac;
    String trangThaiDoiTacKetLuan;
    LocalDateTime thoiGianDoiTacKetLuan;
    String ghiChu;
    LocalDateTime createdDate;
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
