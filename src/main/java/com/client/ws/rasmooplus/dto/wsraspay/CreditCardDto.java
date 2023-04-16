package com.client.ws.rasmooplus.dto.wsraspay;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditCardDto {

    private Integer cvv;
    private String documentNumber;
    private Integer installments;	
    private Integer month;	
    private String number;	
    private Integer year;	
    
}
