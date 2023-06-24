package com.client.ws.rasmooplus.service;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.client.ws.rasmooplus.dto.UserTypeDto;
import com.client.ws.rasmooplus.mapper.UserTypeMapper;
import com.client.ws.rasmooplus.model.jpa.UserType;
import com.client.ws.rasmooplus.repository.jpa.UserTypeRepository;
import com.client.ws.rasmooplus.service.impl.UserTypeServiceImpl;

@ExtendWith(MockitoExtension.class)
class UserTypeServiceTest {

    @Mock
    private UserTypeRepository userTypeRepository;

    @InjectMocks
    private UserTypeServiceImpl userTypeService;
    
    @Test
    void findAll_when_allDatasInDadabase_then_returnAllDatas() {
        List<UserTypeDto> userTypeDtoList = new ArrayList<>();

        UserTypeDto userType1 = UserTypeDto.builder()
            .id(1L)
            .name("Professor")
            .description("Professor da plataforma")
            .build();

        UserTypeDto userType2 = UserTypeDto.builder()
            .id(2L)
            .name("Administrador")
            .description("Administrador da plataforma")
            .build();

        userTypeDtoList.add(userType1);        
        userTypeDtoList.add(userType2);

        List<UserType> userTypeList = new ArrayList<>();
        userTypeDtoList.forEach(userType -> userTypeList.add(UserTypeMapper.fromDtoToEntity(userType)));

        Mockito.when(this.userTypeRepository.findAll()).thenReturn(userTypeList);
        
        List<UserTypeDto> result = this.userTypeService.findAll();

        Assertions.assertThat(result)
            .hasSize(2)
            .isNotEmpty();
        
    }


}
