package com.example.demovtpmic.repository;

import com.example.demovtpmic.entity.HoSoBoiThuong;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HoSoBoiThuongRepository extends JpaRepository<HoSoBoiThuong, Integer> {
    List<HoSoBoiThuong> findBySoHoSoBaoHiem(String soHoSo);
}