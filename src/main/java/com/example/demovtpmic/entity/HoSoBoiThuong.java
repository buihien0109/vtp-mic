package com.example.demovtpmic.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "hoso_boithuongs")
public class HoSoBoiThuong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idHoSo;

    String maHoSo;

    String maVanDon;

    String soHoSoBaoHiem;

    String soHopDongBaoHiem;

    String tinhTrangBaoHiem;
}
