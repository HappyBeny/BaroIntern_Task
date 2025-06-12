package com.example.intern_task.security;

import com.example.intern_task.member.entity.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {

    @Value("${app.jwt.Secret}")
    private String jwtSecret;

    @Value("${app.jwt.ExpirationMs}")
    private long jwtExpirationMs;

    private static final String BEARER_PREFIX = "Bearer ";

    private Key getSigningKey() {
        byte[] keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(Long memberId, UserRole userRole) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + jwtExpirationMs);
        Key key = getSigningKey();

        String token = Jwts.builder()
                .setSubject(memberId.toString())
                .claim("role", userRole.name())
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return BEARER_PREFIX + token;
    }

    public boolean validateToken(String authHeader) {
        try {
            String token = authHeader.replace(BEARER_PREFIX, "");
            Key key = getSigningKey();
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new RuntimeException("Invalid JWT token");
        }
    }

    private Claims getClaims(String authHeader) {
        String token = authHeader.replace(BEARER_PREFIX, "");
        Key key = getSigningKey();
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Long getMemberId(String authHeader) {
        return Long.valueOf(getClaims(authHeader).getSubject());
    }

    public String getUserRole(String authHeader) {
        return getClaims(authHeader).get("role", String.class);
    }
}
