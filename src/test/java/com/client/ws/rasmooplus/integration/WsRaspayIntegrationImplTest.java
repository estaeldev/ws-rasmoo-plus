package com.client.ws.rasmooplus.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.client.ws.rasmooplus.dto.wsraspay.CustomerDto;

@SpringBootTest
class WsRaspayIntegrationImplTest {
    
    @Autowired
    private WsRaspayIntegration wsRaspayIntegration ;

    @Test
    void createCustomerWhenDtoOk() {
        CustomerDto customerDto = new CustomerDto("20228920035", "teste@teste.com", "Estael", null, "Meireles");
        CustomerDto customerCreate = wsRaspayIntegration.createCustomer(customerDto);
        Assertions.assertNotNull(customerCreate.getId());
    }

}
