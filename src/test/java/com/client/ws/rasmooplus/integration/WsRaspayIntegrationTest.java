package com.client.ws.rasmooplus.integration;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import com.client.ws.rasmooplus.dto.wsraspay.CustomerDto;
import com.client.ws.rasmooplus.exception.WsRaspayException;
import com.client.ws.rasmooplus.integration.impl.WsRaspayIntegrationImpl;

@ExtendWith(MockitoExtension.class)
class WsRaspayIntegrationTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private WsRaspayIntegrationImpl wsRaspayIntegration;

    private static HttpHeaders headers;
    
    @BeforeAll
    static void loadDatas() {
        headers = getHttpHeaders();
    }

    @Test
    void createCustomer_when_apiResponseIs201Created_then_returnCustomerDto() {
        ReflectionTestUtils.setField(wsRaspayIntegration, "raspayHost", "http://localhost:8080");
        ReflectionTestUtils.setField(wsRaspayIntegration, "customerUrl", "/customer");

        CustomerDto customerDto = CustomerDto.builder()
            .id(UUID.randomUUID().toString())
            .cpf("12345678911")
            .build();

        HttpEntity<CustomerDto> request = new HttpEntity<>(customerDto, headers);

        when(this.restTemplate.exchange("http://localhost:8080/customer", 
            HttpMethod.POST, request, CustomerDto.class)).thenReturn(ResponseEntity.of(Optional.of(customerDto)));

        Assertions.assertEquals(customerDto, this.wsRaspayIntegration.createCustomer(customerDto));

        verify(this.restTemplate, times(1))
            .exchange("http://localhost:8080/customer", HttpMethod.POST, request, CustomerDto.class);

    }

    @Test
    void createCustomer_when_apiResponseIs400BadRequest_then_returnNull() {
        ReflectionTestUtils.setField(wsRaspayIntegration, "raspayHost", "http://localhost:8080");
        ReflectionTestUtils.setField(wsRaspayIntegration, "customerUrl", "/customer");

        CustomerDto customerDto = CustomerDto.builder()
            .id(UUID.randomUUID().toString())
            .cpf("12345678911")
            .build();

        HttpEntity<CustomerDto> request = new HttpEntity<>(customerDto, headers);

        when(this.restTemplate.exchange("http://localhost:8080/customer", 
            HttpMethod.POST, request, CustomerDto.class)).thenReturn(ResponseEntity.badRequest().build());

        Assertions.assertNull(this.wsRaspayIntegration.createCustomer(customerDto));
                
        verify(this.restTemplate, times(1))
            .exchange("http://localhost:8080/customer", HttpMethod.POST, request, CustomerDto.class);

    }

    @Test
    void createCustomer_when_apiResponseGetThrows_then_returnWsRaspayException() {
        ReflectionTestUtils.setField(wsRaspayIntegration, "raspayHost", "http://localhost:8080");
        ReflectionTestUtils.setField(wsRaspayIntegration, "customerUrl", "/customer");

        CustomerDto customerDto = CustomerDto.builder()
            .id(UUID.randomUUID().toString())
            .cpf("12345678911")
            .build();

        HttpEntity<CustomerDto> request = new HttpEntity<>(customerDto, headers);

        when(this.restTemplate.exchange("http://localhost:8080/customer", 
            HttpMethod.POST, request, CustomerDto.class)).thenThrow(WsRaspayException.class);

        Assertions.assertThrows(WsRaspayException.class,
            () -> this.wsRaspayIntegration.createCustomer(customerDto));
        
        verify(this.restTemplate, times(1))
            .exchange("http://localhost:8080/customer", HttpMethod.POST, request, CustomerDto.class);

    }

    private static HttpHeaders getHttpHeaders() {
        String crendential = "rasmooplus:r@sm00";
        String base64 = Base64.encodeBase64String(crendential.getBytes());
        HttpHeaders headersField = new HttpHeaders();
        headersField.add("Authorization", "Basic " + base64);
        return headersField;
    }

}
