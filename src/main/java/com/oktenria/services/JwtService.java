package com.oktenria.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    private SecretKey key;

    @PostConstruct
    public void setupKey() {
        key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccess(Authentication authentication, long expSec) {
        Date expiryDate = new Date(System.currentTimeMillis() + expSec * 1000);

        return Jwts.builder()
                .claim("type", "access")
                .setSubject(authentication.getName())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(key)
                .compact();
    }

    public String generateRefresh(Authentication authentication, long expSec) {
        Date expiryDate = new Date(System.currentTimeMillis() + expSec * 1000);

        return Jwts.builder()
                .claim("type", "refresh")
                .setSubject(authentication.getName())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(key)
                .compact();
    }

    public boolean isAccessTokenExpired(String token) {
        return resolveClaim(token, Claims::getExpiration).before(new Date());
    }

    public boolean isRefreshTokenExpired(String token) {
        return resolveClaim(token, claims -> Objects.equals(claims.get("type", String.class), "refresh"));
    }

    public String extractUsername(String token) {
        return resolveClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return resolveClaim(token, Claims::getExpiration);
    }

    private <T> T resolveClaim(String token, Function<Claims, T> resolver) {
        Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();

        return resolver.apply(claims);
    }
}
