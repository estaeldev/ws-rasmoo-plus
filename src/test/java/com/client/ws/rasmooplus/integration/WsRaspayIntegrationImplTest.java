package com.client.ws.rasmooplus.integration;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.client.ws.rasmooplus.dto.wsraspay.CreditCardDto;
import com.client.ws.rasmooplus.dto.wsraspay.CustomerDto;
import com.client.ws.rasmooplus.dto.wsraspay.OrderDto;
import com.client.ws.rasmooplus.dto.wsraspay.PaymentDto;

@SpringBootTest
class WsRaspayIntegrationImplTest {
    
    @Autowired
    private WsRaspayIntegration wsRaspayIntegration ;

    @Test
    void createCustomerWhenDtoOk() {
        CustomerDto customerDto = new CustomerDto("20228920035", "teste@teste.com", "Estael", null, "Meireles");
        CustomerDto customerCreate = this.wsRaspayIntegration.createCustomer(customerDto);
        System.out.println(customerCreate);
        Assertions.assertNotNull(customerCreate.getId());
    }

    @Test
    void createOrderWhenDtoOk() {
        OrderDto orderDto = new OrderDto("643dbe8146ed7735da5cccd7", BigDecimal.ZERO, null, "MONTH22");
        OrderDto createOrder = this.wsRaspayIntegration.createOrder(orderDto);
        Assertions.assertNotNull(createOrder.getId());
    }
    
    @Test
    void processPaymentWhenDtoOk() {
        CreditCardDto creditCardDto = new CreditCardDto(123, 
                                                        "20228920035", 
                                                        0,
                                                        06, 
                                                        "1234123412341234", 
                                                        2025);

        PaymentDto paymentDto = new PaymentDto(creditCardDto, 
                                                "643dbe8146ed7735da5cccd7", 
                                                "643dbfbc46ed7735da5cccd8");

        Boolean processPaymentResult = this.wsRaspayIntegration.processPayment(paymentDto);
        Assertions.assertTrue(processPaymentResult);
    }
    

}
