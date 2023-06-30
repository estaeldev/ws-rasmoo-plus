package com.client.ws.rasmooplus.controller;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.client.ws.rasmooplus.dto.UserTypeDto;
import com.client.ws.rasmooplus.service.UserTypeService;

@AutoConfigureDataJpa
@AutoConfigureTestDatabase
@WebMvcTest(UserTypeController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles(profiles = "test")
class UserTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserTypeService userTypeService;

    @Test
    void findAll_then_returnAllUserType() throws Exception {
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

        when(this.userTypeService.findAll()).thenReturn(userTypeDtoList);
        
        this.mockMvc.perform(MockMvcRequestBuilders.get("/user-type"))
            .andExpect(MockMvcResultMatchers.status().isOk());

    }



}
