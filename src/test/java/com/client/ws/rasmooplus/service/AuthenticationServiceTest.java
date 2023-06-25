package com.client.ws.rasmooplus.service;

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
import org.springframework.test.util.ReflectionTestUtils;

import com.client.ws.rasmooplus.dto.redis.UserRecoveryCodeDto;
import com.client.ws.rasmooplus.exception.BadRequestException;
import com.client.ws.rasmooplus.model.redis.UserRecoveryCode;
import com.client.ws.rasmooplus.repository.redis.UserRecoveryCodeRepository;
import com.client.ws.rasmooplus.service.impl.AuthenticationServiceImpl;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private UserRecoveryCodeRepository userRecoveryCodeRepository;

    @InjectMocks
    private AuthenticationServiceImpl authenticationServiceImpl;

    private static UserRecoveryCodeDto userRecoveryCodeDto;

    private static UserRecoveryCode userRecoveryCode;

    @BeforeAll
    static void loadDatas() {

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
    void recoveryCodeIsValid_when_EmailIsNull_then_returnFalse() {
        ReflectionTestUtils.setField(authenticationServiceImpl, "recoveryCodeTimeout", "5");

        userRecoveryCodeDto.setEmail(null);

        when(this.userRecoveryCodeRepository.findByEmail(null))
            .thenReturn(Optional.empty());

        Assertions.assertThrows(BadRequestException.class, 
            () -> this.authenticationServiceImpl.recoveryCodeIsValid(userRecoveryCodeDto));

        verify(this.userRecoveryCodeRepository, times(1))
            .findByEmail(userRecoveryCodeDto.getEmail());

    }



}
