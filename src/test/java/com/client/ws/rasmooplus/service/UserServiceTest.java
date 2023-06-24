package com.client.ws.rasmooplus.service;

import static org.mockito.Mockito.times;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.client.ws.rasmooplus.dto.UserDto;
import com.client.ws.rasmooplus.exception.BadRequestException;
import com.client.ws.rasmooplus.exception.NotFoundException;
import com.client.ws.rasmooplus.mapper.UserMapper;
import com.client.ws.rasmooplus.model.jpa.User;
import com.client.ws.rasmooplus.model.jpa.UserType;
import com.client.ws.rasmooplus.repository.jpa.UserRepository;
import com.client.ws.rasmooplus.repository.jpa.UserTypeRepository;
import com.client.ws.rasmooplus.service.impl.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserTypeRepository userTypeRepository;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    private static UserDto userDto;

    private static UserType userType;

    @BeforeAll
    static void loadDefault() {
        userDto = UserDto.builder()
            .id(UUID.randomUUID())
            .email("tael@gmail.com")
            .cpf("123456789123")
            .userTypeId(1L)
            .build();

        userType = UserType.builder()
            .id(1L)
            .name("Aluno")
            .description("Aluno da plataforma")
            .build();

    }
    
    @Test
    void create_when_IdIsNullAndUserTypeIsNotNull_then_returnUserCreated() {
        
        userDto.setId(null);
        
        Mockito.when(this.userTypeRepository.findById(1L)).thenReturn(Optional.of(userType));
        
        User user = UserMapper.fromDtoToEntity(userDto, userType, null);

        Mockito.when(this.userRepository.save(user)).thenReturn(user);

        Assertions.assertEquals(UserMapper.fromEntityToDto(user), this.userServiceImpl.create(userDto));

        Mockito.verify(this.userTypeRepository, times(1)).findById(Mockito.any());

        Mockito.verify(this.userRepository, times(1)).save(user);
        
    }

    @Test
    void create_when_IdIsNotNull_then_throwBadRequestException() {
        
        Assertions.assertThrows(BadRequestException.class, () -> this.userServiceImpl.create(userDto));

        Mockito.verify(this.userTypeRepository, times(0)).findById(Mockito.any());
        Mockito.verify(this.userRepository, times(0)).save(Mockito.any());

    }

    @Test
    void create_when_IdIsNullAndUserTypeIsNull_then_throwNotFoundException() {

        userDto.setId(null);
        
        Mockito.when(this.userTypeRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> this.userServiceImpl.create(userDto));
        
        Mockito.verify(this.userTypeRepository, times(1)).findById(Mockito.any());
        Mockito.verify(this.userRepository, times(0)).save(Mockito.any());

    }

}



