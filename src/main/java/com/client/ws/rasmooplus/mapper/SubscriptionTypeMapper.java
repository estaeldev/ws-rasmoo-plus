package com.client.ws.rasmooplus.mapper;

import com.client.ws.rasmooplus.dto.SubscriptionTypeDto;
import com.client.ws.rasmooplus.model.SubscriptionType;

public class SubscriptionTypeMapper {

    private SubscriptionTypeMapper() {}
    
    public static SubscriptionType fromDtoToEntity(SubscriptionTypeDto dto) {
        return SubscriptionType.builder()
            .id(dto.getId())
            .name(dto.getName())
            .accessMonths(dto.getAccessMonths())
            .price(dto.getPrice())
            .productKey(dto.getProductKey())
            .build();
    }

    public static SubscriptionTypeDto fromEntityToDto(SubscriptionType model) {
        return SubscriptionTypeDto.builder()
            .id(model.getId())
            .name(model.getName())
            .accessMonths(model.getAccessMonths())
            .price(model.getPrice())
            .productKey(model.getProductKey())
            .build();
    }

}
