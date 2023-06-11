package com.client.ws.rasmooplus.service;

import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;

public interface TokenService {
    String getUsername(String token);
    String getToken(Map<String, Object> claims, UserDetails userDetails);    
    String getToken(UserDetails userDetails);
    Boolean isValidToken(String token, String username);
}
