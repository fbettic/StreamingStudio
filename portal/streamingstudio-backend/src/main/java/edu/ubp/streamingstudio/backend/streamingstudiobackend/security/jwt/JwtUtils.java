package edu.ubp.streamingstudio.backend.streamingstudiobackend.security.jwt;

import java.security.Key;
import java.security.Signature;
import java.sql.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.security.MacAlgorithm;


@Component
public class JwtUtils {
    MacAlgorithm alg = Jwts.SIG.HS512; //or HS384 or HS256
    SecretKey key = alg.key().build();

    @Value("${jwt.time.expiration}")
    private String timeExpiration;


    // Generar tocken de acceso
    public String generateAcceToken(String username){
        Long currentTime = System.currentTimeMillis();
        return Jwts.builder()
        .subject(username)
        .issuedAt(new Date(currentTime))
        .expiration(new Date(currentTime + Long.parseLong(timeExpiration)))
        .signWith(key, alg)
        .compact();
    }

}
