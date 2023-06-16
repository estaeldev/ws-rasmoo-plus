package com.client.ws.rasmooplus.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.client.ws.rasmooplus.dto.AuthenticationResponseDto;
import com.client.ws.rasmooplus.dto.LoginDto;
import com.client.ws.rasmooplus.dto.RegisterDto;
import com.client.ws.rasmooplus.dto.UserTypeDto;
import com.client.ws.rasmooplus.mapper.UserCredentialsMapper;
import com.client.ws.rasmooplus.mapper.UserTypeMapper;
import com.client.ws.rasmooplus.model.jpa.UserCredentials;
import com.client.ws.rasmooplus.repository.jpa.UserCredentialsRepository;
import com.client.ws.rasmooplus.service.AuthenticationService;
import com.client.ws.rasmooplus.service.TokenService;
import com.client.ws.rasmooplus.service.UserTypeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final TokenService tokenService;
    private final UserCredentialsRepository userCredentialsRepository;
    private final UserTypeService userTypeService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthenticationResponseDto authenticate(LoginDto loginDto) {
        UsernamePasswordAuthenticationToken authToken = 
            new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        this.authenticationManager.authenticate(authToken);
        
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(loginDto.getUsername());
        String token = this.tokenService.getToken(userDetails);

        return AuthenticationResponseDto.builder().token(token).build();
    }

    @Override
    public AuthenticationResponseDto register(RegisterDto registerDto) {
        UserTypeDto userTypeDto = this.userTypeService.findById(registerDto.getUserTypeId());

        UserCredentials userCredentialsSaved = this.userCredentialsRepository.save(UserCredentialsMapper
            .fromDtoToEntity(registerDto, UserTypeMapper.fromDtoToEntity(userTypeDto), passwordEncoder));

        String token = this.tokenService.getToken(userCredentialsSaved);

        return AuthenticationResponseDto.builder().token(token).build();
    }
    
}
