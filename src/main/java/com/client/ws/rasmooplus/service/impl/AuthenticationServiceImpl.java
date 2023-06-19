package com.client.ws.rasmooplus.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

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
import com.client.ws.rasmooplus.dto.redis.UserRecoveryCodeDto;
import com.client.ws.rasmooplus.exception.BadRequestException;
import com.client.ws.rasmooplus.exception.NotFoundException;
import com.client.ws.rasmooplus.integration.MailIntegration;
import com.client.ws.rasmooplus.mapper.UserCredentialsMapper;
import com.client.ws.rasmooplus.mapper.UserTypeMapper;
import com.client.ws.rasmooplus.model.jpa.UserCredentials;
import com.client.ws.rasmooplus.model.redis.UserRecoveryCode;
import com.client.ws.rasmooplus.repository.jpa.UserCredentialsRepository;
import com.client.ws.rasmooplus.repository.redis.UserRecoveryCodeRepository;
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
    private final UserRecoveryCodeRepository userRecoveryCodeRepository;
    private final MailIntegration mailIntegration;

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

    @Override
    public void sendRecoveryCode(String email) {
        String code = String.format("%06d", new Random().nextInt(10000));
        UserRecoveryCode userRecoveryCode;
        Optional<UserRecoveryCode> userRecoveryCodeOpt = this.userRecoveryCodeRepository.findByEmail(email);

        if(userRecoveryCodeOpt.isEmpty()) {

            Optional<UserCredentials> userCredentialsOpt = this.userCredentialsRepository.findByUsername(email);

            if(userCredentialsOpt.isEmpty()) {
                throw new NotFoundException("Usuario não encontrado!");
            }

            userRecoveryCode = UserRecoveryCode.builder()
                .email(userCredentialsOpt.get().getUsername())
                .build();

        } else {
            userRecoveryCode = userRecoveryCodeOpt.get();
        }

        userRecoveryCode.setCode(code);
        userRecoveryCode.setCreateDate(LocalDateTime.now());

        this.userRecoveryCodeRepository.save(userRecoveryCode);

        this.mailIntegration.send(
            userRecoveryCode.getEmail(), "Codigo de recuperação de conta: " + code, "CODIGO DE ACESSO!");

    }

    @Override
    public Boolean recoveryCodeIsValid(UserRecoveryCodeDto dto) {
        Optional<UserRecoveryCode> userRecoveryCodeOpt = this.userRecoveryCodeRepository.findByEmail(dto.getEmail());
        return userRecoveryCodeOpt.map(userRecoveryCode -> {

            if(dto.getCode().equals(userRecoveryCode.getCode())) {
                return Boolean.TRUE;
            }

            return Boolean.FALSE;

        }).orElseThrow(() -> new BadRequestException("email inválido!"));
    }
   
}
