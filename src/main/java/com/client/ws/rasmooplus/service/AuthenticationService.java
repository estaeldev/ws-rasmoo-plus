package com.client.ws.rasmooplus.service;

import com.client.ws.rasmooplus.dto.AuthenticationResponseDto;
import com.client.ws.rasmooplus.dto.LoginDto;
import com.client.ws.rasmooplus.dto.RegisterDto;
import com.client.ws.rasmooplus.dto.redis.UserRecoveryCodeDto;

public interface AuthenticationService {

    AuthenticationResponseDto authenticate(LoginDto loginDto);
    AuthenticationResponseDto register(RegisterDto registerDto);
    void sendRecoveryCode(String email);
    Boolean recoveryCodeIsValid(UserRecoveryCodeDto dto);
    void updatePasswordByRecoveryCode(UserRecoveryCodeDto dto);

}
