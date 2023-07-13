package com.client.ws.rasmooplus.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

import java.io.FileInputStream;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

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
@TestInstance(Lifecycle.PER_CLASS)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserTypeRepository userTypeRepository;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    private UserDto userDto;

    private UserType userType;

    @BeforeAll
    void loadDefault() {
        this.userDto = UserDto.builder()
            .id(UUID.randomUUID())
            .email("tael@gmail.com")
            .cpf("123456789123")
            .userTypeId(1L)
            .build();

        this.userType = UserType.builder()
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


    @Test
    void testUploadPhoto_when_thereIsUserAndFileAndItIsPNGorJPEG_then_updatePhotoAndReturnUser() throws Exception {
        UUID uuid = UUID.randomUUID();
        userDto.setId(uuid);

        User user = UserMapper.fromDtoToEntity(userDto, userType, null);

        FileInputStream file = new FileInputStream("src/test/resources/static/avatar.png");

        MockMultipartFile multipartFile = new MockMultipartFile(
            "file", "avatar.png", MediaType.MULTIPART_FORM_DATA_VALUE, file);
        
        Mockito.when(this.userRepository.findById(uuid)).thenReturn(Optional.of(user));

        UserDto userReturned = this.userServiceImpl.uploadPhoto(uuid, multipartFile);

        Assertions.assertNotNull(userReturned);
        Assertions.assertNotNull(userReturned.getPhoto());
        Assertions.assertNotNull(userReturned.getPhotoName());

        Assertions.assertEquals("avatar.png", userReturned.getPhotoName());
        
        Mockito.verify(this.userRepository, times(1)).findById(any());

    }

    @Test
    void testUploadPhoto_when_thereIsUserAndFileAndItIsNotPNGorJPEG_then_throwBadRequestException() throws Exception {
        UUID uuid = UUID.randomUUID();
        userDto.setId(uuid);

        FileInputStream file = new FileInputStream("src/test/resources/static/avatar.png");

        MockMultipartFile multipartFile = new MockMultipartFile(
            "file", "avatar.txt", MediaType.MULTIPART_FORM_DATA_VALUE, file);
        
        
        Assertions.assertThrows(BadRequestException.class, 
            () -> this.userServiceImpl.uploadPhoto(uuid, multipartFile));
        

        Mockito.verify(this.userRepository, times(0)).findById(any());

    }


}



