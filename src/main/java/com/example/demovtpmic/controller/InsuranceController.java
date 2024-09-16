package com.example.demovtpmic.controller;

import com.example.demovtpmic.model.request.PartnerConcludesRequest;
import com.example.demovtpmic.service.InsuranceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class InsuranceController {
    private final InsuranceService insuranceService;

    @PostMapping("/insurance/conclude")
    public ResponseEntity<?> concludeInsurance(@RequestBody PartnerConcludesRequest request) {
        insuranceService.concludeInsurance(request);
        return ResponseEntity.ok("Insurance concluded");
    }
}
