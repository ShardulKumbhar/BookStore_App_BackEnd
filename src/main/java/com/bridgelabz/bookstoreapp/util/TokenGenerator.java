package com.bridgelabz.bookstoreapp.util;

import com.bridgelabz.bookstoreapp.entity.UserData;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class TokenGenerator {

    /**
     * Unique token generation
     * @param userDetails
     * @return
     */
    public String generateLoginToken(UserData userDetails) {

        long currentTime = System.currentTimeMillis();

        return Jwts.builder()
                .setId(String.valueOf(userDetails.getId()))
                .setSubject(userDetails.getFirstName()+userDetails.getLastName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(currentTime + 100000000))
                .signWith(SignatureAlgorithm.HS256, "sd5745FAHFW")
                .compact();
    }

    public String generateVerificationToken(UserData userDetails) {

        long currentTime = System.currentTimeMillis();
        System.out.println("generate token id:   " + userDetails.getId());
        return Jwts.builder()
                .setId(String.valueOf(userDetails.getId()))
                .setSubject(userDetails.getFirstName()+userDetails.getLastName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(currentTime +100000000))
                .signWith(SignatureAlgorithm.HS256, "sd5745FAHFW")
                .compact();
    }

    public Long decodeJWT(String jwt) throws JwtException {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey("sd5745FAHFW").parseClaimsJws(jwt).getBody();

            System.out.println("jwt id: " + claims.getId());
            return Long.parseLong(claims.getId());
        } catch (ExpiredJwtException e) {
            throw new JwtException("session time out");
        }
    }

}