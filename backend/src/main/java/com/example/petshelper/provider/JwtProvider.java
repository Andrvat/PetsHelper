package com.example.petshelper.provider;

import io.jsonwebtoken.*;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtProvider {

    private static final int REFRESH_TOKEN_LIFE_TIME = 15; // 15 days of life refresh token

    private final org.apache.commons.logging.Log logger = LogFactory.getLog(getClass());

    @Value("${jwt.authsecret}")
    private String jwtAuthSecret;

    private String generateToken(String email, String jwtSecret) {
        Date date = Date.from(LocalDate.now().plusDays(JwtProvider.REFRESH_TOKEN_LIFE_TIME).atStartOfDay(ZoneId.systemDefault()).toInstant());
        UUID tokenId = UUID.randomUUID();
        return Jwts.builder()
                .setId(tokenId.toString())
                .setSubject(email)
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String generateRefreshToken(String email) {
        logger.info("Generating refresh token for " + email);
        return generateToken(email, jwtAuthSecret);
    }

    private boolean validateToken(String token, String jwtSecret) {
        System.out.println(jwtSecret);
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            logger.error("invalid token");
        }
        return false;
    }

    public boolean validateAuthToken(String token) {
        return validateToken(token, jwtAuthSecret);
    }

    private String getEmailFromToken(String token, String jwtSecret) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public String getEmailFromAuthToken(String token) {
        return getEmailFromToken(token, jwtAuthSecret);
    }

}