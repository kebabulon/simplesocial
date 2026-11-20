package io.github.kebabulon.simplesocial.security;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import io.github.kebabulon.simplesocial.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;
import java.time.Instant;
import java.util.List;

@Service
public class JwtService {
    private final SecretKey secretKey;
    private final Duration accessTokenTtl;
    private final Duration refreshTokenTtl;

    public JwtService(@Value("${jwt.secret}") String secret,
                      @Value("${jwt.access-token-ttl}") Duration accessTokenTtl,
                      @Value("${jwt.refresh-token-ttl}") Duration refreshTokenTtl) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.accessTokenTtl = accessTokenTtl;
        this.refreshTokenTtl = refreshTokenTtl;
    }

    public String generateAccessToken(User user){
        return buildToken(user, "access", accessTokenTtl);
    }
    public String generateRefreshToken(User user){
        return buildToken(user,"refresh", refreshTokenTtl);
    }

    private String buildToken(User user,String type, Duration ttl){
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(user.getUsername())
                .claim("uid",user.getId())
                .claim("type",type)
                .claim("roles",rolesOf(user))
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(ttl)))
                .signWith(secretKey)
                .compact();
    }

    public Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    public boolean isAccessToken(Claims claims) {
        return "access".equals(claims.get("type", String.class));
    }
    public boolean isRefreshToken(Claims claims) {
        return "refresh".equals(claims.get("type", String.class));
    }

    private List<String> rolesOf(User user) {
        if (user.isAdmin()) return List.of("ADMIN", "MODERATOR");
        if (user.isModerator()) return List.of("MODERATOR");
        return List.of("USER");
    }
}
