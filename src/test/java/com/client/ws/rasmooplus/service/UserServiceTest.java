package com.client.ws.rasmooplus.service;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.client.ws.rasmooplus.dto.UserDto;
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
    
    @Test
    void create_when_IdAndUserTypeIsNotNull_then_returnUserCreated() {
        
        UserDto userDto = UserDto.builder()
            .email("tael@gmail.com")
            .cpf("123456789123")
            .userTypeId(1L)
            .build();
        
        UserType userType = UserType.builder()
            .id(1L)
            .name("Aluno")
            .description("Aluno da plataforma")
            .build();
        
        Mockito.when(this.userTypeRepository.findById(1L)).thenReturn(Optional.of(userType));
        
        User user = UserMapper.fromDtoToEntity(userDto, userType, null);

        Mockito.when(this.userRepository.save(user)).thenReturn(user);

         Assertions.assertEquals(UserMapper.fromEntityToDto(user), this.userServiceImpl.create(userDto));
    }

}



