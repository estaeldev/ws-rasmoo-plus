package com.client.ws.rasmooplus.mapper;

import com.client.ws.rasmooplus.dto.UserTypeDto;
import com.client.ws.rasmooplus.model.jpa.UserType;

public class UserTypeMapper {

    private UserTypeMapper() {}
    
    public static UserTypeDto fromEntityToDto(UserType userType) {
        return UserTypeDto.builder()
            .id(userType.getId())
            .name(userType.getName())
            .description(userType.getDescription())
            .build();
    }

    public static UserType fromDtoToEntity(UserTypeDto dto) {
        return UserType.builder()
            .id(dto.getId())
            .name(dto.getName())
            .description(dto.getDescription())
            .build();
    }


}
