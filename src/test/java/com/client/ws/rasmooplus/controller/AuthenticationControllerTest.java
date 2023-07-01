package com.client.ws.rasmooplus.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

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

import com.client.ws.rasmooplus.dto.AuthenticationResponseDto;
import com.client.ws.rasmooplus.dto.LoginDto;
import com.client.ws.rasmooplus.dto.RegisterDto;
import com.client.ws.rasmooplus.dto.redis.UserRecoveryCodeDto;
import com.client.ws.rasmooplus.exception.BadRequestException;
import com.client.ws.rasmooplus.service.AuthenticationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureDataJpa
@AutoConfigureTestDatabase
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(AuthenticationController.class)
@ActiveProfiles("test")
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthenticationService authenticationService;


    @Test
    void testAuthenticate_when_dtoIsOk_then_returnToken() throws Exception {

        AuthenticationResponseDto authenticationResponseDto = AuthenticationResponseDto.builder()
            .token("123456")
            .build();

        LoginDto loginDto = LoginDto.builder()
            .username("tael@gmail.com")
            .password("123")
            .build();

        Mockito.when(this.authenticationService.authenticate(loginDto)).thenReturn(authenticationResponseDto);
        
        this.mockMvc.perform(MockMvcRequestBuilders.post("/auth/authenticate")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(this.objectMapper.writeValueAsString(loginDto))
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.token", Matchers.is("123456")))
            .andExpect(MockMvcResultMatchers.status().isOk());
            
        Mockito.verify(this.authenticationService, times(1)).authenticate(any());

    }

    @Test
    void testAuthenticate_when_dtoIsMissingValue_then_returnBadRequest() throws Exception {

        LoginDto loginDto = LoginDto.builder()
            .username("")
            .password("")
            .build();

        this.mockMvc.perform(MockMvcRequestBuilders.post("/auth/authenticate")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(this.objectMapper.writeValueAsString(loginDto))
            )
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
            
        Mockito.verify(this.authenticationService, times(0)).authenticate(any());

    }

    @Test
    void testRecoveryCodeIsValid_when_dtoIsOk_then_returnTrue() throws Exception {

        UserRecoveryCodeDto userRecoveryCodeDto = UserRecoveryCodeDto.builder()
            .email("tael@gmail.com")
            .code("123456")
            .password("tael123")
            .build();

        Mockito.when(this.authenticationService.recoveryCodeIsValid(userRecoveryCodeDto)).thenReturn(Boolean.TRUE);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/auth/recovery-code/isValid")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(this.objectMapper.writeValueAsString(userRecoveryCodeDto))
            )
            .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(this.authenticationService, times(1)).recoveryCodeIsValid(any());

    }

    @Test
    void testRecoveryCodeIsValid_when_dtoIsMissingValue_then_returnBadRequest() throws Exception {

        UserRecoveryCodeDto userRecoveryCodeDto = UserRecoveryCodeDto.builder()
            .email("")
            .code("")
            .password("")
            .build();

        this.mockMvc.perform(MockMvcRequestBuilders.post("/auth/recovery-code/isValid")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(this.objectMapper.writeValueAsString(userRecoveryCodeDto))
            )
            .andExpect(MockMvcResultMatchers.status().isBadRequest());

        Mockito.verify(this.authenticationService, times(0)).recoveryCodeIsValid(any());

    }

    @Test
    void testRegister_when_dtoIsOk_then_returnToken() throws JsonProcessingException, Exception {

        AuthenticationResponseDto authenticationResponseDto = AuthenticationResponseDto.builder()
            .token("123456")
            .build();

        RegisterDto registerDto = RegisterDto.builder()
            .username("tael@gmail.com")
            .password("123456")
            .userTypeId(1L)
            .build();
        
        Mockito.when(this.authenticationService.register(registerDto)).thenReturn(authenticationResponseDto);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(this.objectMapper.writeValueAsString(registerDto))
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.token", Matchers.is("123456")))
            .andExpect(MockMvcResultMatchers.status().isCreated());
            
        Mockito.verify(this.authenticationService, times(1)).register(any());

    }

    @Test
    void testRegister_when_dtoIsMissingValue_then_returnBadRequest() throws JsonProcessingException, Exception {

        RegisterDto registerDto = RegisterDto.builder()
            .username("")
            .password("")
            .userTypeId(null)
            .build();

        this.mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(this.objectMapper.writeValueAsString(registerDto))
            )
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
            
        Mockito.verify(this.authenticationService, times(0)).register(any());

    }

    @Test
    void testSendRecoveryCode_when_emailIsValid_then_returnVoid() throws Exception {
        String email = "tael@gmail.com";

        Mockito.doNothing().when(this.authenticationService).sendRecoveryCode(email);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/auth/recovery-code/send")
            .param("email", email)
            )
            .andExpect(MockMvcResultMatchers.status().isNoContent());

        Mockito.verify(this.authenticationService, times(1)).sendRecoveryCode(email);

    }

    @Test
    void testSendRecoveryCode_when_emailIsNotValid_then_returnBadRequest() throws Exception {
        String email = "tael@gmail.com";

        Mockito.doThrow(BadRequestException.class).when(this.authenticationService).sendRecoveryCode(email);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/auth/recovery-code/send")
            .param("email", email)
            )
            .andExpect(MockMvcResultMatchers.status().isBadRequest());

        Mockito.verify(this.authenticationService, times(1)).sendRecoveryCode(email);

    }


    @Test
    void testUpdatePasswordByRecoveryCode_when_dtoIsValid_then_returnVoid() throws Exception {

        UserRecoveryCodeDto userRecoveryCodeDto = UserRecoveryCodeDto.builder()
            .email("tael@gmail.com")
            .code("123456")
            .password("tael123")
            .build();
        
        Mockito.doNothing().when(this.authenticationService).updatePasswordByRecoveryCode(userRecoveryCodeDto);

        this.mockMvc.perform(MockMvcRequestBuilders.patch("/auth/recovery-code/password")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(this.objectMapper.writeValueAsString(userRecoveryCodeDto))
            )
            .andExpect(MockMvcResultMatchers.status().isNoContent());        

        Mockito.verify(this.authenticationService, times(1)).updatePasswordByRecoveryCode(any());

    }

    @Test
    void testUpdatePasswordByRecoveryCode_when_dtoIsMissingValue_then_returnBadRequest() throws Exception {

        UserRecoveryCodeDto userRecoveryCodeDto = UserRecoveryCodeDto.builder()
            .email("")
            .code("")
            .password("")
            .build();

        this.mockMvc.perform(MockMvcRequestBuilders.patch("/auth/recovery-code/password")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(this.objectMapper.writeValueAsString(userRecoveryCodeDto))
            )
            .andExpect(MockMvcResultMatchers.status().isBadRequest());        

        Mockito.verify(this.authenticationService, times(0)).updatePasswordByRecoveryCode(any());

    }


}
