package com.example.zencom.zencom_shop.modules.users.adapters.out;

import com.example.zencom.zencom_shop.modules.shared.ids.UserId;
import com.example.zencom.zencom_shop.modules.shared.security.Role;
import com.example.zencom.zencom_shop.modules.users.application.exception.InvalidToken;
import com.example.zencom.zencom_shop.modules.users.application.ports.out.TokenService;
import com.example.zencom.zencom_shop.modules.users.domain.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtTokenServiceAdapter implements TokenService {

    private final SecretKey secretKey;
    private final long expirationTime;

    public JwtTokenServiceAdapter(
            @Value("${security.jwt.secret}") String secret,
            @Value("${security.jwt.expirationSeconds}") long expirationSeconds
    ) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationTime = expirationSeconds;
    }


    @Override
    public String generateToken(User user) {
        Instant now = Instant.now();
        Instant expiresAt = now.plusSeconds(expirationTime);
        UUID userId = user.getId().getId();
        Set<Role> roles = user.getRole();

        return Jwts.builder()
                .setSubject(userId.toString())
                .claim("roles", roles.stream().map(Role::name).toList())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiresAt))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public UserId extractUserId(String token) {
        Claims claims = parseClaims(token);
        UUID id = UUID.fromString(claims.getSubject());
        return UserId.fromUUID(id);
    }

    @Override
    public Set<Role> extractRoles(String token) {
        Claims claims = parseClaims(token);

        Object raw = claims.get("roles");
        if(raw == null) return Set.of();

        @SuppressWarnings("unchecked")
        var list = (List<String>) raw;

        return  list.stream()
                .map(Role::valueOf)
                .collect(Collectors.toSet());
    }

    @Override
    public void validate(String token) {
        try{
            parseClaims(token);
        } catch (Exception e){
            throw new InvalidToken("Invalid token");
        }

    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token).getBody();
    }
}
