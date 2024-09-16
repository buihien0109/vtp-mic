package com.example.demovtpmic;

import com.example.demovtpmic.entity.HoSoBaoHiem;
import com.example.demovtpmic.entity.HoSoBoiThuong;
import com.example.demovtpmic.entity.Partner;
import com.example.demovtpmic.repository.HoSoBaoHiemRepository;
import com.example.demovtpmic.repository.HoSoBoiThuongRepository;
import com.example.demovtpmic.repository.PartnerRepository;
import com.example.demovtpmic.repository.PhieuGuiRepository;
import com.example.demovtpmic.utils.JwtTokenUtils;
import com.example.demovtpmic.utils.KeyUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.PrivateKey;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@SpringBootTest
class DemoVtpMicApplicationTests {
    @Autowired
    private PartnerRepository partnerRepository;
    @Autowired
    private PhieuGuiRepository phieuGuiRepository;
    @Autowired
    private HoSoBoiThuongRepository hoSoBoiThuongRepository;
    @Autowired
    private HoSoBaoHiemRepository hoSoBaoHiemRepository;

    @Test
    void generate_jwt() throws Exception {
        PrivateKey privateKey = KeyUtils.getPrivateKey("KeyPair/private_key.pem");
        String jwtToken = JwtTokenUtils.generateJwtToken(privateKey);
        System.out.println("Generated JWT Token: " + jwtToken);
    }

    @Test
    void save_parter() {
        for (int i = 0; i < 3; i++) {
            Partner partner = Partner.builder()
                    .maDoiTac("DT" + i)
                    .tenDoiTac("Đối tác " + i)
                    .build();
            partnerRepository.save(partner);
        }
    }

    @Test
    void save_hosoboithuong() {
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            String maHoSo = "HS" + random.nextInt(1000000);
            String maVanDon = "VD" + random.nextInt(1000000);
            String soHoSoBaoHiem = "SHSBH" + random.nextInt(1000000);
            String soHopDongBaoHiem = "SHDBH" + random.nextInt(1000000);
            HoSoBoiThuong hoSoBoiThuong = HoSoBoiThuong.builder()
                    .maHoSo(maHoSo)
                    .maVanDon(maVanDon)
                    .soHoSoBaoHiem(soHoSoBaoHiem)
                    .soHopDongBaoHiem(soHopDongBaoHiem)
                    .tinhTrangBaoHiem(random.nextInt(2) == 0 ? "D" : "T")
                    .build();
            hoSoBoiThuongRepository.save(hoSoBoiThuong);
        }
    }

    @Test
    void save_hosobaohiem() {
        List<HoSoBoiThuong> hoSoBoiThuongs = hoSoBoiThuongRepository.findAll();
        List<HoSoBoiThuong> rdHoSoBoiThuongs = hoSoBoiThuongs.subList(0, 50);
        List<String> nnTonThatList = List.of("NN1", "NN2", "NN3", "NN4", "NN5", "QT5", "KHAC");
        Random random = new Random();

        for (HoSoBoiThuong hsbt : rdHoSoBoiThuongs) {
            HoSoBaoHiem hoSoBaoHiem = HoSoBaoHiem.builder()
                    .maHoSo("HSBH" + random.nextInt(1000000))
                    .maVanDon(hsbt.getMaVanDon())
                    .maBienBan("BB" + random.nextInt(1000000))
                    .maHoSoBoiThuong(hsbt.getMaHoSo())
                    .maDoiTac("MIC")
                    .maKhachHang("KH" + random.nextInt(1000000))
                    .giaTri((long) random.nextInt(1000000))
                    .thoiGianGuiHang(LocalDateTime.now())
                    .nguyenNhanTonThat(nnTonThatList.get(random.nextInt(nnTonThatList.size())))
                    .trangThaiVanDon("HOAN_THANH_HO_SO")
                    .tienBoiThuongVtp((long) random.nextInt(1000000))
                    .tienBoiThuongDoiTac(null)
                    .trangThaiDoiTacKetLuan("DANG_XU_LY")
                    .thoiGianDoiTacKetLuan(null)
                    .build();
            hoSoBaoHiemRepository.save(hoSoBaoHiem);
        }
    }
}
