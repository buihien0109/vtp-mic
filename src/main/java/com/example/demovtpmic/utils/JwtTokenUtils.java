package com.example.demovtpmic.utils;

import com.example.demovtpmic.exception.PartnerConclusionException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.ObjectUtils;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTokenUtils {
    public static String getTokenFromHeader(String header) {
        if (ObjectUtils.isEmpty(header)) {
            throw new PartnerConclusionException("Token không tồn tại");
        }
        String BEARER_TOKEN = "Bearer ";
        return header.replace(BEARER_TOKEN, "");
    }

    public static boolean validateJwt(String token) {
        try {
            PublicKey publicKey = KeyUtils.getPublicKey("KeyPair/public_key.pem");
            Jwts.parser()
                    .verifyWith(publicKey)
                    .build()
                    .parse(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public static String generateJwtToken(PrivateKey privateKey) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("issuer", "InsurancePartner");
        claims.put("subject", "UpdateConclusion");
        claims.put("role", "Partner");

        long expirationTime = 1000 * 60 * 60;
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + expirationTime);

        return Jwts.builder()
                .claims(claims)
                .issuedAt(now)
                .expiration(expirationDate)
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();
    }
}
