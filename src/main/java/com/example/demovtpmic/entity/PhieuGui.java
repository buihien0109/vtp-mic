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
@Table(name = "phieu_guis")
public class PhieuGui {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idPhieuGui;

    String maPhieuGui;
}
