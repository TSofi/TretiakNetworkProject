package com.tretiak.project.tretiaknetworkproject.service;

import com.tretiak.project.tretiaknetworkproject.commonTypes.UserRole;
import com.tretiak.project.tretiaknetworkproject.infrastrucuture.entity.AuthEntity;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Service
public class JwtService {
    @Value("${token.sign.key}")
    private String key;

    private SecretKey SECRET_KEY;

    @PostConstruct
    public void init() {
        SECRET_KEY = Keys.hmacShaKeyFor(Decoders.BASE64.decode(key));
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public UserRole extractRole(String token) {
        return UserRole.valueOf(extractClaim(token, claims -> claims.get("role", String.class)));
    }

    public Boolean isTokenValid(String token) {
        return extractExpiration(token).after(new Date());
    }

    public String generateToken(AuthEntity authEntity) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", authEntity.getRole().toString());
        return Jwts.builder()
                .claims(claims)
                .setSubject(authEntity.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(token).getPayload();
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}