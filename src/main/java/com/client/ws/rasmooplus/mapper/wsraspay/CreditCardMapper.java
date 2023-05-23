package com.client.ws.rasmooplus.mapper.wsraspay;

import com.client.ws.rasmooplus.dto.UserPaymentInfoDto;
import com.client.ws.rasmooplus.dto.wsraspay.CreditCardDto;

public class CreditCardMapper {
    
    private CreditCardMapper() {}

    public static CreditCardDto build(UserPaymentInfoDto dto, String cpf) {
        return CreditCardDto.builder()
            .cvv(Integer.parseInt(dto.getCardSecurityCode()))
            .documentNumber(cpf)
            .installments(dto.getInstallments())
            .month(dto.getCardExpirationMonth())
            .number(dto.getCardNumber())
            .year(dto.getCardExpirationYear())
            .build();
    }

}
