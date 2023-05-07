package com.client.ws.rasmooplus.mapper.wsraspay;

import com.client.ws.rasmooplus.dto.wsraspay.CreditCardDto;
import com.client.ws.rasmooplus.dto.wsraspay.OrderDto;
import com.client.ws.rasmooplus.dto.wsraspay.PaymentDto;

public class PaymentMapper {
    
    private PaymentMapper() {}

    public static PaymentDto build(CreditCardDto creditCardDto, OrderDto orderDto) {
        return PaymentDto.builder()
            .creditCard(creditCardDto)
            .customerId(orderDto.getCustomerId())
            .orderId(orderDto.getId())
            .build();
    }

}
