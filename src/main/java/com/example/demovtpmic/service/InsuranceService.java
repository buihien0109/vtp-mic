package com.example.demovtpmic.service;

import com.example.demovtpmic.entity.HoSoBaoHiem;
import com.example.demovtpmic.entity.HoSoBoiThuong;
import com.example.demovtpmic.entity.Partner;
import com.example.demovtpmic.exception.PartnerConclusionException;
import com.example.demovtpmic.model.request.PartnerConcludesRequest;
import com.example.demovtpmic.repository.HoSoBaoHiemRepository;
import com.example.demovtpmic.repository.HoSoBoiThuongRepository;
import com.example.demovtpmic.repository.PartnerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class InsuranceService {
    private final PartnerRepository partnerRepository;
    private final HoSoBaoHiemRepository hoSoBaoHiemRepository;
    private final HoSoBoiThuongRepository hoSoBoiThuongRepository;

    public InsuranceService(PartnerRepository partnerRepository, HoSoBaoHiemRepository hoSoBaoHiemRepository, HoSoBoiThuongRepository hoSoBoiThuongRepository) {
        this.partnerRepository = partnerRepository;
        this.hoSoBaoHiemRepository = hoSoBaoHiemRepository;
        this.hoSoBoiThuongRepository = hoSoBoiThuongRepository;
    }

    public void concludeInsurance(PartnerConcludesRequest request) {
        Optional<Partner> partnerOptional = partnerRepository.findByMaDoiTac(request.getDoiTac());
        if (partnerOptional.isEmpty()) {
            throw new PartnerConclusionException("Không tìm thấy đối tác bảo hiểm");
        }

        List<HoSoBoiThuong> hoSoBoiThuongList = hoSoBoiThuongRepository
                .findBySoHoSoBaoHiem(request.getSoHoSo());
        if (hoSoBoiThuongList.isEmpty()) {
            throw new PartnerConclusionException("Không tìm thấy hồ sơ bồi thường theo số hồ sơ bảo hiểm");
        }

        List<HoSoBaoHiem> hoSoBaoHiemList = new ArrayList<>();
        for (HoSoBoiThuong hoSoBoiThuong : hoSoBoiThuongList) {
            Optional<HoSoBaoHiem> hoSoBaoHiemOptional = hoSoBaoHiemRepository
                    .findByMaVanDonAndMaHoSoBoiThuong(
                            hoSoBoiThuong.getMaVanDon(),
                            hoSoBoiThuong.getMaHoSo()
                    );
            if (hoSoBaoHiemOptional.isEmpty()) {
                throw new PartnerConclusionException("Không tìm thấy hồ sơ bảo hiểm");
            }
            hoSoBaoHiemList.add(hoSoBaoHiemOptional.get());
        }

        List<HoSoBaoHiem> savedHoSoBaoHiemList = new ArrayList<>();
        for (HoSoBaoHiem hoSoBaoHiem : hoSoBaoHiemList) {
            Long partnerCompensation = getInsuranceValue(request, hoSoBaoHiem);
            String partnerConclusionStatus = parsePartnerConclusionStatus(request.getKetQua());
            hoSoBaoHiem.setTienBoiThuongDoiTac(partnerCompensation);
            hoSoBaoHiem.setTrangThaiDoiTacKetLuan(partnerConclusionStatus);
            hoSoBaoHiem.setThoiGianDoiTacKetLuan(LocalDateTime.now());
            hoSoBaoHiem.setGhiChu(request.getGhiChu());
            savedHoSoBaoHiemList.add(hoSoBaoHiem);
        }

        hoSoBaoHiemRepository.saveAll(savedHoSoBaoHiemList);
    }

    private String parsePartnerConclusionStatus(String ketQua) {
        return switch (ketQua) {
            case "ACCEPT" -> "DONG_Y";
            case "REJECT" -> "TU_CHOI";
            case "OTHER" -> "THOA_THUAN";
            default -> throw new PartnerConclusionException("Unexpected value: " + ketQua);
        };
    }

    private Long getInsuranceValue(PartnerConcludesRequest request, HoSoBaoHiem hoSoBaoHiem) {
        String ketQua = request.getKetQua();
        Long tienBoiThuongVtp = hoSoBaoHiem.getTienBoiThuongVtp();
        return switch (ketQua) {
            case "ACCEPT" -> tienBoiThuongVtp;
            case "REJECT" -> 0L;
            case "OTHER" -> request.getTienBoiThuong();
            default -> throw new PartnerConclusionException("Unexpected value: " + ketQua);
        };
    }
}
