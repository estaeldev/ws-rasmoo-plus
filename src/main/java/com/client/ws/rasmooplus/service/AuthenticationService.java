package com.client.ws.rasmooplus.service;

import com.client.ws.rasmooplus.dto.AuthenticationResponseDto;
import com.client.ws.rasmooplus.dto.LoginDto;
import com.client.ws.rasmooplus.dto.RegisterDto;

public interface AuthenticationService {
    AuthenticationResponseDto authenticate(LoginDto loginDto);
    AuthenticationResponseDto register(RegisterDto registerDto);
}
