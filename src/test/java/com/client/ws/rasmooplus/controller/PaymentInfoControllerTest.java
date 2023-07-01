package com.client.ws.rasmooplus.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

import java.math.BigDecimal;

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

import com.client.ws.rasmooplus.dto.PaymentProcessDto;
import com.client.ws.rasmooplus.dto.UserPaymentInfoDto;
import com.client.ws.rasmooplus.service.PaymentInfoService;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureDataJpa
@AutoConfigureTestDatabase
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(PaymentInfoController.class)
@ActiveProfiles("test")
class PaymentInfoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PaymentInfoService paymentInfoService;

    @Test
    void process_when_dtoIsOk_then_returnTrue() throws Exception {

        UserPaymentInfoDto userPaymentInfoDto = UserPaymentInfoDto.builder()
            .id(1L)
            .build();

        PaymentProcessDto paymentProcessDto = PaymentProcessDto.builder()
            .productKey("YAER")
            .discount(BigDecimal.valueOf(5L))
            .userPaymentInfoDto(userPaymentInfoDto)
            .build();

        Mockito.when(this.paymentInfoService.process(paymentProcessDto)).thenReturn(Boolean.TRUE);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/payment/process")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(this.objectMapper.writeValueAsString(paymentProcessDto))
            )
            .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(this.paymentInfoService, times(1)).process(any());

    }

    @Test
    void process_when_dtoIsMissingValue_then_returnBadRequest() throws Exception {

        PaymentProcessDto paymentProcessDto = PaymentProcessDto.builder()
            .productKey("")
            .discount(null)
            .userPaymentInfoDto(null)
            .build();

        this.mockMvc.perform(MockMvcRequestBuilders.post("/payment/process")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(this.objectMapper.writeValueAsString(paymentProcessDto))
            )
            .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());

        Mockito.verify(this.paymentInfoService, times(0)).process(any());

    }

}
