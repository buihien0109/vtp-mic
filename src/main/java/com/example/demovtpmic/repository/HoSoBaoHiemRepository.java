package com.example.demovtpmic.repository;

import com.example.demovtpmic.entity.HoSoBaoHiem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HoSoBaoHiemRepository extends JpaRepository<HoSoBaoHiem, Integer> {
    Optional<HoSoBaoHiem> findByMaVanDonAndMaHoSoBoiThuong(String maVanDon, String maHoSo);
}