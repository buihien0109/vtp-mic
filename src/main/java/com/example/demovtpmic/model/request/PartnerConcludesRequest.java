package com.example.demovtpmic.model.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PartnerConcludesRequest {
    String doiTac;
    String soHoSo;
    String ketQua;
    Long tienBoiThuong;
    String ghiChu;
}
