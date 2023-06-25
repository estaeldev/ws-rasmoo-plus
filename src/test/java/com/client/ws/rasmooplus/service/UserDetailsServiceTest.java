package com.client.ws.rasmooplus.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import com.client.ws.rasmooplus.exception.NotFoundException;
import com.client.ws.rasmooplus.model.jpa.UserCredentials;
import com.client.ws.rasmooplus.model.jpa.UserType;
import com.client.ws.rasmooplus.repository.jpa.UserCredentialsRepository;
import com.client.ws.rasmooplus.service.impl.UserDetailsServiceImpl;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceTest {

    @Mock
    private UserCredentialsRepository userCredentialsRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsServiceImpl;

    private static UserType userType;

    private static UserCredentials userCredentials;

    @BeforeAll
    static void loadDatas() {
        userType = UserType.builder()
            .id(1L)
            .name("Aluno")
            .description("Aluno da plataforma")
            .build();

        userCredentials = UserCredentials.builder()
            .id(UUID.randomUUID())
            .username("tael@gmail.com")
            .password("tael123")
            .userType(userType)
            .build();

    }

    @Test
    void loadUserByUsername_when_UsernameIsNotNull_then_returnUserDetails() {
        
        when(this.userCredentialsRepository.findByUsername(userCredentials.getUsername()))
            .thenReturn(Optional.of(userCredentials));

        UserDetails userDetails = this.userDetailsServiceImpl.loadUserByUsername(userCredentials.getUsername());

        Assertions.assertNotNull(userDetails);
        Assertions.assertEquals(userDetails.getUsername(), userCredentials.getUsername());
        Assertions.assertEquals(userDetails.getPassword(), userCredentials.getPassword());
        Assertions.assertEquals(userDetails.getAuthorities().size(), userCredentials.getAuthorities().size());

        verify(this.userCredentialsRepository, times(1)).findByUsername(any());

    }

    @Test
    void loadUserByUsername_when_UsernameIsNull_then_returnNotFoundException() {
        
        when(this.userCredentialsRepository.findByUsername(null)).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> 
            this.userDetailsServiceImpl.loadUserByUsername(null));

        verify(this.userCredentialsRepository, times(0)).findByUsername(userCredentials.getUsername());

    }



}
