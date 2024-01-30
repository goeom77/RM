package org.gyu.solution.global.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtTokenProvider {
    // 스프링의 Environment 인터페이스를 주입받아 환경 속성에 액세스합니다.
    private final Environment env;

    // JWT 토큰에서 UUID(사용자명)을 추출합니다. token -> loginId
    public String getLoginId(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    // JWT 토큰의 claims 부분에서 특정 claim을 추출합니다.
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//        log.info("extractClaim method : {}",token);
        Claims claims = extractAllClaims(token);
//        log.info("Clims: {}",claims);
        return claimsResolver.apply(claims);
    }


    // UserDetails 객체를 사용하여 JWT 토큰을 생성합니다. loginId -> token
    public String generateToken(UserDetails userDetails) {
        return generateToken(Map.of(), userDetails);
    }
    // 주어진 claims와 UserDetails 객체를 사용하여 JWT 토큰을 생성합니다.
    public String generateToken(
            Map<String, Objects> extractClaims,
            UserDetails userDetails
    ) {
//        log.info("generateToken : ", extractClaims, userDetails);
        System.out.println("generateToken : " + extractClaims + userDetails);
        return Jwts.builder()
                .setClaims(extractClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new java.util.Date(System.currentTimeMillis()))
                .setExpiration(new java.util.Date(System.currentTimeMillis()
                        + env.getProperty("JWT.EXPIRATION_TIME",Long.class)))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    // JWT 토큰이 유효한지 검증합니다. 토큰의 사용자명과 UserDetails의 사용자명이 일치하고 토큰이 만료되지 않았는지 확인합니다.
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String loginId = getLoginId(token);
        return (loginId.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    // JWT 토큰이 만료되었는지 확인합니다.
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new java.util.Date());
    }
    // JWT 토큰에서 만료 시간을 추출합니다.
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    // JWT 토큰의 모든 claims를 추출합니다.
    private Claims extractAllClaims(String token) {
        log.info("extractAllClaims {}", token);
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    // 환경 속성에서 JWT 비밀 키를 가져와서 사용할 수 있는 형태로 변환합니다.
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(env.getProperty("JWT.SECRET_KEY"));
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
