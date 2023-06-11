package com.client.ws.rasmooplus.mapper;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.client.ws.rasmooplus.dto.RegisterDto;
import com.client.ws.rasmooplus.model.UserCredentials;
import com.client.ws.rasmooplus.model.UserType;

public class UserCredentialsMapper {
    
    private UserCredentialsMapper() {}

    public static UserCredentials fromDtoToEntity(RegisterDto dto, UserType userType, PasswordEncoder passwordEncoder) {
        return UserCredentials.builder()
            .username(dto.getUsername())
            .password(passwordEncoder.encode(dto.getPassword()))
            .userType(userType)
            .build();
    }

}
