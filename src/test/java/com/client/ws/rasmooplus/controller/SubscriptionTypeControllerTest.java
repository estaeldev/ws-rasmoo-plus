package com.client.ws.rasmooplus.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

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

import com.client.ws.rasmooplus.controller.impl.SubscriptionTypeController;
import com.client.ws.rasmooplus.dto.SubscriptionTypeDto;
import com.client.ws.rasmooplus.service.SubscriptionTypeService;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureDataJpa
@AutoConfigureTestDatabase
@WebMvcTest(SubscriptionTypeController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class SubscriptionTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SubscriptionTypeService subscriptionTypeService;

    @Test
    void findAll_then_returnAllSubscriptionType() throws Exception {
        
        this.mockMvc.perform(MockMvcRequestBuilders.get("/subscription-type"))
            .andExpect(status().isOk());
    }

    @Test
    void findById_when_getId2_then_returnSubscriptionType() throws Exception {

        SubscriptionTypeDto subscriptionTypeDto = SubscriptionTypeDto.builder()
            .id(2L)
            .name("VITALICIO")
            .accessMonths(null)
            .price(BigDecimal.valueOf(997))
            .productKey("FOREVER2022")
            .build();
        
        when(this.subscriptionTypeService.findById(subscriptionTypeDto.getId()))
            .thenReturn(subscriptionTypeDto);

        this.mockMvc.perform(get("/subscription-type/2"))
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id", Matchers.is(2)))
            .andExpect(jsonPath("$.name", Matchers.is(subscriptionTypeDto.getName())))
            .andExpect(jsonPath("$.productKey", Matchers.is(subscriptionTypeDto.getProductKey())))
            .andExpect(status().isOk());
        

    }

    @Test
    void deleteById_when_getId2_then_returnNoContent() throws Exception {

        this.mockMvc.perform(delete("/subscription-type/{id}", 2))
            .andExpect(status().isNoContent());
        
        verify(this.subscriptionTypeService, times(1)).deleteById(2L);

    }

    @Test
    void create_when_dtoIsOk_then_return() throws Exception {

        SubscriptionTypeDto subscriptionTypeDto = SubscriptionTypeDto.builder()
            .id(null)
            .name("VITALICIO")
            .accessMonths(null)
            .price(BigDecimal.valueOf(997))
            .productKey("FOREVER2022")
            .build();
        
        SubscriptionTypeDto subscriptionTypeDto2 = SubscriptionTypeDto.builder()
            .id(1L)
            .name(subscriptionTypeDto.getName())
            .accessMonths(subscriptionTypeDto.getAccessMonths())
            .price(subscriptionTypeDto.getPrice())
            .productKey(subscriptionTypeDto.getProductKey())
            .build();

        when(this.subscriptionTypeService.create(subscriptionTypeDto))
            .thenReturn(subscriptionTypeDto2);

        this.mockMvc.perform(post("/subscription-type")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(this.objectMapper.writeValueAsString(subscriptionTypeDto))
            )
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id", Matchers.is(1)))
            .andExpect(status().isCreated());

    }

    @Test
    void create_when_dtoIsMissingValue_then_returnBadRequest() throws Exception {

        SubscriptionTypeDto subscriptionTypeDto = SubscriptionTypeDto.builder()
            .id(null)
            .name("TE")
            .accessMonths(13)
            .price(null)
            .productKey("FO")
            .build();

        this.mockMvc.perform(post("/subscription-type")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(this.objectMapper.writeValueAsString(subscriptionTypeDto))
            )
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.message", Matchers.is("[price=não pode ser nulo ou vazio, accessMonths=não pode ser maior que 12, name=deve ter tamanho entre 5 e 30, productKey=deve ter tamanho entre 5 e 15]")))
            .andExpect(jsonPath("$.httpStatus", Matchers.is("BAD_REQUEST")))
            .andExpect(jsonPath("$.statusCode", Matchers.is(400)))
            .andExpect(status().isBadRequest());
        
        verify(this.subscriptionTypeService, times(0)).create(any());

    }

     @Test
    void update_when_dtoIsOk_then_returnSubscriptionTypeUpdated() throws Exception {

        SubscriptionTypeDto subscriptionTypeDto = SubscriptionTypeDto.builder()
            .id(1L)
            .name("VITALICIO")
            .accessMonths(null)
            .price(BigDecimal.valueOf(997))
            .productKey("FOREVER2022")
            .build();
        
        SubscriptionTypeDto subscriptionTypeDto2 = SubscriptionTypeDto.builder()
            .id(1L)
            .name(subscriptionTypeDto.getName())
            .accessMonths(subscriptionTypeDto.getAccessMonths())
            .price(subscriptionTypeDto.getPrice())
            .productKey(subscriptionTypeDto.getProductKey())
            .build();

        when(this.subscriptionTypeService.update(1L, subscriptionTypeDto))
            .thenReturn(subscriptionTypeDto2);

        this.mockMvc.perform(put("/subscription-type/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(this.objectMapper.writeValueAsString(subscriptionTypeDto))
            )
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id", Matchers.is(1)))
            .andExpect(status().isOk());

    }

    @Test
    void update_when_dtoIsMissingValue_then_returnBadRequest() throws Exception {

        SubscriptionTypeDto subscriptionTypeDto = SubscriptionTypeDto.builder()
            .id(null)
            .name("")
            .accessMonths(13)
            .price(null)
            .productKey("FO")
            .build();

        this.mockMvc.perform(put("/subscription-type/{id}", 2)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(this.objectMapper.writeValueAsString(subscriptionTypeDto))
            )
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.message", Matchers.is("[price=não pode ser nulo ou vazio, accessMonths=não pode ser maior que 12, name=deve ter tamanho entre 5 e 30, productKey=deve ter tamanho entre 5 e 15]")))
            .andExpect(jsonPath("$.httpStatus", Matchers.is("BAD_REQUEST")))
            .andExpect(jsonPath("$.statusCode", Matchers.is(400)))
            .andExpect(status().isBadRequest());
        
        verify(this.subscriptionTypeService, times(0)).update(any(), any());

    }

    @Test
    void update_when_dtoIsNull_then_returnBadRequest() throws Exception {

        SubscriptionTypeDto subscriptionTypeDto = SubscriptionTypeDto.builder()
            .id(null)
            .name("TE")
            .accessMonths(13)
            .price(null)
            .productKey("FO")
            .build();

        this.mockMvc.perform(put("/subscription-type/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(this.objectMapper.writeValueAsString(subscriptionTypeDto))
            )
            .andExpect(status().isNotFound());
        
        verify(this.subscriptionTypeService, times(0)).update(any(), any());

    }

}
