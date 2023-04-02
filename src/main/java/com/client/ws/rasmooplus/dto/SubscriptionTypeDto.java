package com.client.ws.rasmooplus.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionTypeDto {
   
    private Long id;
    private String name;
    private Integer accessMonths;
    private BigDecimal price;
    private String productKey;
    
}
