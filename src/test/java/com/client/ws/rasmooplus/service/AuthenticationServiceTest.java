package com.client.ws.rasmooplus.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import com.client.ws.rasmooplus.dto.redis.UserRecoveryCodeDto;
import com.client.ws.rasmooplus.exception.BadRequestException;
import com.client.ws.rasmooplus.model.jpa.UserCredentials;
import com.client.ws.rasmooplus.model.jpa.UserType;
import com.client.ws.rasmooplus.model.redis.UserRecoveryCode;
import com.client.ws.rasmooplus.repository.jpa.UserCredentialsRepository;
import com.client.ws.rasmooplus.repository.redis.UserRecoveryCodeRepository;
import com.client.ws.rasmooplus.service.impl.AuthenticationServiceImpl;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private UserRecoveryCodeRepository userRecoveryCodeRepository;

    @Mock
    private UserCredentialsRepository userCredentialsRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthenticationServiceImpl authenticationServiceImpl;

    private static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    private static UserRecoveryCodeDto userRecoveryCodeDto;

    private static UserRecoveryCode userRecoveryCode;

    private static UserCredentials userCredentials;

    private static UserType userType;

    @BeforeAll
    static void loadDatas() {

        userType = UserType.builder()
            .id(1L)
            .name("Aluno")
            .description("Aluno da plataforma")
            .build();

        userRecoveryCodeDto = UserRecoveryCodeDto.builder()
            .email("tael@gmail.com")
            .code("102030")
            .password("tael123")
            .build();

        userRecoveryCode = UserRecoveryCode.builder()
            .id(UUID.randomUUID())
            .email(userRecoveryCodeDto.getEmail())
            .code(userRecoveryCodeDto.getCode())
            .build();

        userCredentials = UserCredentials.builder()
            .id(UUID.randomUUID())
            .username(userRecoveryCodeDto.getEmail())
            .password(encoder.encode(userRecoveryCodeDto.getPassword()))
            .userType(userType)
            .build();
        
    }


    @Test
    void recoveryCodeIsValid_when_EmailIsNotNull_then_returnTrue() {
        ReflectionTestUtils.setField(authenticationServiceImpl, "recoveryCodeTimeout", "5");

        when(this.userRecoveryCodeRepository.findByEmail(userRecoveryCodeDto.getEmail()))
            .thenReturn(Optional.of(userRecoveryCode));

        Assertions.assertTrue(this.authenticationServiceImpl.recoveryCodeIsValid(userRecoveryCodeDto));

        verify(this.userRecoveryCodeRepository, times(1))
            .findByEmail(userRecoveryCodeDto.getEmail());

    }
    
    @Test
    void recoveryCodeIsValid_when_EmailIsNotNullAndCodeIsNull_then_returnFalse() {
        ReflectionTestUtils.setField(authenticationServiceImpl, "recoveryCodeTimeout", "5");

        userRecoveryCode.setCode(null);

        when(this.userRecoveryCodeRepository.findByEmail(userRecoveryCodeDto.getEmail()))
            .thenReturn(Optional.of(userRecoveryCode));

        Assertions.assertFalse(this.authenticationServiceImpl.recoveryCodeIsValid(userRecoveryCodeDto));

        verify(this.userRecoveryCodeRepository, times(1))
            .findByEmail(userRecoveryCodeDto.getEmail());

    }
    
    @Test
    void recoveryCodeIsValid_when_EmailIsNotNullAndTimeExpiration_then_returnFalse() {
        ReflectionTestUtils.setField(authenticationServiceImpl, "recoveryCodeTimeout", "5");

        userRecoveryCode.setCreateDate(LocalDateTime.of(2023, 6, 24, 10, 0));

        when(this.userRecoveryCodeRepository.findByEmail(userRecoveryCodeDto.getEmail()))
            .thenReturn(Optional.of(userRecoveryCode));

        Assertions.assertFalse(this.authenticationServiceImpl.recoveryCodeIsValid(userRecoveryCodeDto));

        verify(this.userRecoveryCodeRepository, times(1))
            .findByEmail(userRecoveryCodeDto.getEmail());

    }

    @Test
    void recoveryCodeIsValid_when_EmailIsNull_then_returnBadRequestException() {
        ReflectionTestUtils.setField(authenticationServiceImpl, "recoveryCodeTimeout", "5");

        userRecoveryCodeDto.setEmail(null);

        when(this.userRecoveryCodeRepository.findByEmail(null))
            .thenReturn(Optional.empty());

        Assertions.assertThrows(BadRequestException.class, 
            () -> this.authenticationServiceImpl.recoveryCodeIsValid(userRecoveryCodeDto));

        verify(this.userRecoveryCodeRepository, times(1))
            .findByEmail(userRecoveryCodeDto.getEmail());

    }

    @Test
    void updatePasswordByRecoveryCode_when_DtoValied_then_returnVoid() {
        ReflectionTestUtils.setField(authenticationServiceImpl, "recoveryCodeTimeout", "5");

        String senhaAntiga = userRecoveryCodeDto.getPassword();
        String novaSenhaEncoder = encoder.encode("123");

        userCredentials.setPassword(novaSenhaEncoder);

        when(this.userRecoveryCodeRepository.findByEmail(userRecoveryCodeDto.getEmail()))
            .thenReturn(Optional.of(userRecoveryCode));

        when(this.userCredentialsRepository.findByUsername(userRecoveryCodeDto.getEmail()))
            .thenReturn(Optional.of(userCredentials));
        
        when(this.passwordEncoder.encode(senhaAntiga))
            .thenReturn(userCredentials.getPassword());

        when(this.userCredentialsRepository.save(any())).thenReturn(any());
        
        Assertions.assertDoesNotThrow(() -> this.authenticationServiceImpl.updatePasswordByRecoveryCode(userRecoveryCodeDto));

        Assertions.assertEquals(novaSenhaEncoder, userCredentials.getPassword());

        verify(this.userRecoveryCodeRepository, times(1)).findByEmail(any());
        verify(this.userCredentialsRepository, times(1)).findByUsername(any());
        verify(this.userCredentialsRepository, times(1)).save(any());

    }

    @Test
    void updatePasswordByRecoveryCode_when_EmailIsNull_then_returnBadRequestException() {
        ReflectionTestUtils.setField(authenticationServiceImpl, "recoveryCodeTimeout", "5");

        userRecoveryCodeDto.setEmail(null);

        when(this.userRecoveryCodeRepository.findByEmail(null))
            .thenReturn(Optional.empty());
        
        Assertions.assertEquals("email inválido!", Assertions.assertThrows(
            BadRequestException.class, 
            () -> this.authenticationServiceImpl.updatePasswordByRecoveryCode(userRecoveryCodeDto)).getLocalizedMessage());

        verify(this.userRecoveryCodeRepository, times(1)).findByEmail(any());

    }

    @Test
    void updatePasswordByRecoveryCode_when_PasswordIsNull_then_returnBadRequestException() {
        ReflectionTestUtils.setField(authenticationServiceImpl, "recoveryCodeTimeout", "5");

        userRecoveryCodeDto.setPassword(null);

        when(this.userRecoveryCodeRepository.findByEmail(userRecoveryCodeDto.getEmail()))
            .thenReturn(Optional.of(userRecoveryCode));

        Assertions.assertEquals("codigo invalido ou password nulo!", Assertions.assertThrows(
            BadRequestException.class, 
            () -> this.authenticationServiceImpl.updatePasswordByRecoveryCode(userRecoveryCodeDto)).getMessage());

        verify(this.userRecoveryCodeRepository, times(1)).findByEmail(any());

    }

}
