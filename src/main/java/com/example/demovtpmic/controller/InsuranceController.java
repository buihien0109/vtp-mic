package com.example.demovtpmic.controller;

import com.example.demovtpmic.exception.PartnerConclusionException;
import com.example.demovtpmic.model.request.PartnerConcludesRequest;
import com.example.demovtpmic.service.InsuranceService;
import com.example.demovtpmic.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class InsuranceController {
    private final InsuranceService insuranceService;

    @PostMapping("/insurance/conclude")
    public ResponseEntity<?> concludeInsurance(@RequestHeader(value = "Authorization", required = false) String authHeader,
                                               @RequestBody PartnerConcludesRequest request) {
        String token = JwtTokenUtils.getTokenFromHeader(authHeader);
        boolean isValid = JwtTokenUtils.validateJwt(token);
        if (!isValid) {
            throw new PartnerConclusionException("Token không hợp lệ");
        }
        insuranceService.concludeInsurance(request);
        return ResponseEntity.ok("Insurance concluded");
    }
}
