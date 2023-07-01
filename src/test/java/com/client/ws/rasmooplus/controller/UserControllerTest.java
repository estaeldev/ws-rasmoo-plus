package com.client.ws.rasmooplus.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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

import com.client.ws.rasmooplus.dto.UserDto;
import com.client.ws.rasmooplus.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureDataJpa
@AutoConfigureTestDatabase
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(UserController.class)
@ActiveProfiles("test")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;


    @Test
    void create_when_dtoIsOk_then_returnUserDto() throws Exception {

        UserDto userDto = UserDto.builder()
            .id(UUID.randomUUID())
            .name("Estael")
            .email("tael@gmail.com")
            .phone("11122233311")
            .cpf("03270811209")
            .userTypeId(1L)
            .build();

        when(this.userService.create(userDto)).thenReturn(userDto);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/users")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(this.objectMapper.writeValueAsString(userDto))
            )
            .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.notNullValue()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.subscriptionTypeId", Matchers.nullValue()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.userTypeId", Matchers.is(1)))
            .andExpect(MockMvcResultMatchers.status().isCreated());


        Mockito.verify(this.userService, times(1)).create(any());

    }

    @Test
    void create_when_dtoMissingValue_then_returnBadRequest() throws Exception {

        UserDto userDto = UserDto.builder()
            .id(null)
            .name("")
            .email("")
            .phone("")
            .cpf("")
            .userTypeId(null)
            .build();

        this.mockMvc.perform(MockMvcRequestBuilders.post("/users")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(this.objectMapper.writeValueAsString(userDto))
            )
            .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());


        Mockito.verify(this.userService, times(0)).create(any());

    }



}
