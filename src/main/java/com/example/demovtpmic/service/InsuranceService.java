package com.example.demovtpmic.service;

import com.example.demovtpmic.entity.HoSoBaoHiem;
import com.example.demovtpmic.entity.HoSoBoiThuong;
import com.example.demovtpmic.entity.Partner;
import com.example.demovtpmic.exception.PartnerConclusionException;
import com.example.demovtpmic.model.request.PartnerConcludesRequest;
import com.example.demovtpmic.repository.HoSoBaoHiemRepository;
import com.example.demovtpmic.repository.HoSoBoiThuongRepository;
import com.example.demovtpmic.repository.PartnerRepository;
import com.example.demovtpmic.repository.PhieuGuiRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class InsuranceService {
    private final PartnerRepository partnerRepository;
    private final PhieuGuiRepository phieuGuiRepository;
    private final HoSoBaoHiemRepository hoSoBaoHiemRepository;
    private final HoSoBoiThuongRepository hoSoBoiThuongRepository;

    public void concludeInsurance(PartnerConcludesRequest request) {
        Optional<Partner> partnerOptional = partnerRepository.findByMaDoiTac(request.getDoiTac());
        if (partnerOptional.isEmpty()) {
            throw new PartnerConclusionException("Không tìm thấy đối tác bảo hiểm");
        }

        Optional<HoSoBoiThuong> hoSoBoiThuongOptional = hoSoBoiThuongRepository.findBySoHoSoBaoHiem(request.getSoHoSo());
        if (hoSoBoiThuongOptional.isEmpty()) {
            throw new PartnerConclusionException("Không tìm thấy hồ sơ bồi thường");
        }

        HoSoBoiThuong hoSoBoiThuong = hoSoBoiThuongOptional.get();
        Optional<HoSoBaoHiem> hoSoBaoHiemOptional = hoSoBaoHiemRepository
                .findByMaVanDonAndMaHoSoBoiThuong(hoSoBoiThuong.getMaVanDon(), hoSoBoiThuong.getMaHoSo());
        if (hoSoBaoHiemOptional.isEmpty()) {
            throw new PartnerConclusionException("Không tìm thấy hồ sơ bảo hiểm");
        }

        HoSoBaoHiem hoSoBaoHiem = hoSoBaoHiemOptional.get();
        Long partnerCompensation = getInsuranceValue(request, hoSoBaoHiem);
        String partnerConclusionStatus = parsePartnerConclusionStatus(request.getKetQua());
        hoSoBaoHiem.setTienBoiThuongDoiTac(partnerCompensation);
        hoSoBaoHiem.setTrangThaiDoiTacKetLuan(partnerConclusionStatus);
        hoSoBaoHiem.setThoiGianDoiTacKetLuan(LocalDateTime.now());
        hoSoBaoHiemRepository.save(hoSoBaoHiem);
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
