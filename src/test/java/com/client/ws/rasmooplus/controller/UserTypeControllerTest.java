package com.client.ws.rasmooplus.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.client.ws.rasmooplus.controller.impl.UserTypeController;
import com.client.ws.rasmooplus.dto.UserTypeDto;
import com.client.ws.rasmooplus.service.UserTypeService;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureDataJpa
@AutoConfigureTestDatabase
@WebMvcTest(UserTypeController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles(profiles = "test")
class UserTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

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

    @Test
    void findById_when_dtoIsOk_then_returnUserType() throws Exception {

        UserTypeDto userType = UserTypeDto.builder()
            .id(1L)
            .name("Professor")
            .description("Professor da plataforma")
            .build();

        when(this.userTypeService.findById(1L)).thenReturn(userType);
        
        this.mockMvc.perform(MockMvcRequestBuilders.get("/user-type/{id}", 1)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            )
            .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(userType.getName())))
            .andExpect(MockMvcResultMatchers.status().isOk());
        
        verify(this.userTypeService, times(1)).findById(any());

    }

    @Test
    void create_when_dtoIsOk_then_returnUserType() throws Exception {

        UserTypeDto userTypeDto = UserTypeDto.builder()
            .id(null)
            .name("Professor")
            .description("Professor da plataforma")
            .build();

        UserTypeDto userTypeCreated = UserTypeDto.builder()
            .id(1L)
            .name("Professor")
            .description("Professor da plataforma")
            .build();

        when(this.userTypeService.create(userTypeDto)).thenReturn(userTypeCreated);
        
        this.mockMvc.perform(MockMvcRequestBuilders.post("/user-type")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(this.objectMapper.writeValueAsString(userTypeDto))
            )
            .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(userTypeCreated.getName())))
            .andExpect(MockMvcResultMatchers.status().isCreated());
        
        verify(this.userTypeService, times(1)).create(any());

    }

    @Test
    void create_when_dtoIsMissingValue_then_returnBadRequest() throws Exception {

        UserTypeDto userTypeDto = UserTypeDto.builder()
            .id(null)
            .name("")
            .description("")
            .build();
        
        this.mockMvc.perform(MockMvcRequestBuilders.post("/user-type")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(this.objectMapper.writeValueAsString(userTypeDto))
            )
            .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("[name=não pode ser nulo ou vazio, description=não pode ser nulo ou vazio]")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.httpStatus", Matchers.is("BAD_REQUEST")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode", Matchers.is(400)))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
        
        verify(this.userTypeService, times(0)).create(any());

    }

    @Test
    void update_when_dtoIsOk_then_returnUserType() throws Exception {

        UserTypeDto userTypeDto = UserTypeDto.builder()
            .id(1L)
            .name("Professor")
            .description("Professor da plataforma")
            .build();

        UserTypeDto userTypeUpdated = UserTypeDto.builder()
            .id(1L)
            .name("Professor")
            .description("Professor da plataforma")
            .build();

        when(this.userTypeService.update(1L, userTypeDto)).thenReturn(userTypeUpdated);
        
        this.mockMvc.perform(MockMvcRequestBuilders.put("/user-type/{id}", 1)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(this.objectMapper.writeValueAsString(userTypeDto))
            )
            .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(userTypeUpdated.getName())))
            .andExpect(MockMvcResultMatchers.status().isOk());
        
        verify(this.userTypeService, times(1)).update(any(), any());

    }

     @Test
    void update_when_dtoMissingValue_then_returnBadRequest() throws Exception {

        UserTypeDto userTypeDto = UserTypeDto.builder()
            .id(1L)
            .name("")
            .description("")
            .build();

        this.mockMvc.perform(MockMvcRequestBuilders.put("/user-type/{id}", 1)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(this.objectMapper.writeValueAsString(userTypeDto))
            )
            .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("[name=não pode ser nulo ou vazio, description=não pode ser nulo ou vazio]")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.httpStatus", Matchers.is("BAD_REQUEST")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode", Matchers.is(400)))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
        
        verify(this.userTypeService, times(0)).update(any(), any());

    }

    @Test
    void deleteById_when_getId2_then_returnNoContent() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/user-type/{id}", 2))
            .andExpect(MockMvcResultMatchers.status().isNoContent());
        
        verify(this.userTypeService, times(1)).deleteById(2L);

    }

}
