package com.anuj.ecommerce.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtProvider {


    private final SecretKey key;

    public JwtProvider() {
        this.key = Keys.secretKeyFor(SignatureAlgorithm.HS256);  // Generate a secure key
    }

    public String generateToken(Authentication auth) {
        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 846000000))
                .claim("email", auth.getName())
                .signWith(key)
                .compact();
    }

     public String getEmailFromToken(String jwt) {
        jwt = jwt.substring(7);  // Remove 'Bearer ' prefix

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwt)
                .getBody();
        return claims.get("email", String.class);
    }
}
