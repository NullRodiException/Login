package com.rodrigo.login.implementation.services;

import com.rodrigo.login.implementation.entity.User;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;

@Service
public class TokenService {

    private final UserService userService;

    @Value("${jwt.secret.key}")
    private String secret;

    @Value("${jwt.expiration.time}")
    private long expirationTime;

    public TokenService(
            UserService userService, @Value("${jwt.secret.key}") String secret,
            @Value("${jwt.expiration.time}") long expirationTime){
        this.userService = userService;
        this.secret = secret;
        this.expirationTime = expirationTime;
    }

    public String generateToken(User user){
        return Jwts.builder()
                .subject(user.getId().toString())
                .claim("authorities", user.getRole().name())
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plus(expirationTime, ChronoUnit.SECONDS)))
                .signWith(Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret)), Jwts.SIG.HS256)
                .compact();
    }

    public String getIdFromToken(String token){
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret)))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    private Boolean tokenExpired(String token) {
        Date expiration = Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret)))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();
        return expiration.before(Date.from(Instant.now()));
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        try {
            final String idFromToken = getIdFromToken(token);
            final String login = userService.findUserById(idFromToken).getUsername();
            return (login.equals(userDetails.getUsername()) && !tokenExpired(token));
        } catch (SignatureException | ExpiredJwtException ex) {
            return Boolean.FALSE;
        }
    }
}
