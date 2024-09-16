package com.example.demovtpmic.repository;

import com.example.demovtpmic.entity.Partner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PartnerRepository extends JpaRepository<Partner, Integer> {
    Optional<Partner> findByMaDoiTac(String doiTac);
}