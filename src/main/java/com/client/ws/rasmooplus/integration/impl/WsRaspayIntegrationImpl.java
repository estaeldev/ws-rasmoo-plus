package com.client.ws.rasmooplus.integration.impl;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.client.ws.rasmooplus.dto.wsraspay.CustomerDto;
import com.client.ws.rasmooplus.dto.wsraspay.OrderDto;
import com.client.ws.rasmooplus.dto.wsraspay.PaymentDto;
import com.client.ws.rasmooplus.integration.WsRaspayIntegration;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class WsRaspayIntegrationImpl implements WsRaspayIntegration {

    private static final String WS_RASPAY_URL = "https://ws-raspay.herokuapp.com/ws-raspay";
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public CustomerDto createCustomer(CustomerDto dto) {
        try {
            HttpHeaders headers = getHttpHeaders();
            HttpEntity<CustomerDto> request = new HttpEntity<>(dto, headers);
            ResponseEntity<CustomerDto> response = this.restTemplate.exchange(WS_RASPAY_URL+"/v1/customer", 
                HttpMethod.POST, request, CustomerDto.class);
            return response.getBody();
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public OrderDto createOrder(OrderDto dto) {
       
        throw new UnsupportedOperationException("Unimplemented method 'createOrder'");
    }

    @Override
    public Boolean processPayment(PaymentDto dto) {
       
        throw new UnsupportedOperationException("Unimplemented method 'processPayment'");
    }

    
    private HttpHeaders getHttpHeaders() {
        String crendential = "rasmooplus:r@sm00";
        String base64 = Base64.encodeBase64String(crendential.getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64);
        return headers;
    }
    
}
