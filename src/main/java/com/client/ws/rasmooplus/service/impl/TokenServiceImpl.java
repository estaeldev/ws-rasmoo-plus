package com.client.ws.rasmooplus.service.impl;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.client.ws.rasmooplus.service.TokenService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class TokenServiceImpl implements TokenService{

    @Value("${webservices.rasplus.jwt.secret}")
    private String secret;

    @Value("${webservices.rasplus.jwt.expiration}")
    private String expiration;


    @Override
    public String getUsername(String token) {
        return getClaim(token, Claims::getSubject);
    }

    @Override
    public String getToken(Map<String, Object> claims, UserDetails userDetails) {
        return Jwts.builder()
            .setIssuer("Api Rasmoo Plus")
            .setClaims(claims)
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(expiration)))
            .signWith(getSignKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    @Override
    public String getToken(UserDetails userDetails) {
        return getToken(new HashMap<>(), userDetails);
    }

    @Override
    public Boolean isValidToken(String token, String username) {
        String usernameToken = getUsername(token);
        return (usernameToken.equals(username) && !isTokenExpired(token));
    }

    private Boolean isTokenExpired(String token) {
        return getClaim(token, Claims::getExpiration).before(new Date());
    }

    private <T> T getClaim(String token, Function<Claims, T> claimsResolverFunction) {
        Claims claims = getAllClaims(token);
        return claimsResolverFunction.apply(claims);
    }
    
    private Claims getAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
    }

    private Key getSignKey() {
        byte[] key = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(key);
    }

}
