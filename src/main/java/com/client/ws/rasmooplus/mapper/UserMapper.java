package com.client.ws.rasmooplus.mapper;

import java.util.Objects;

import com.client.ws.rasmooplus.dto.UserDto;
import com.client.ws.rasmooplus.model.jpa.SubscriptionType;
import com.client.ws.rasmooplus.model.jpa.User;
import com.client.ws.rasmooplus.model.jpa.UserType;

public class UserMapper {

    private UserMapper() {}
    
    public static User fromDtoToEntity(UserDto userDto, UserType userType, SubscriptionType subscriptionType) {
        return User.builder()
            .id(userDto.getId())
            .name(userDto.getName())
            .email(userDto.getEmail())
            .phone(userDto.getPhone())
            .cpf(userDto.getCpf())
            .photo(userDto.getPhoto())
            .photoName(userDto.getPhotoName())
            .dtExpiration(userDto.getDtExpiration())
            .dtSubscription(userDto.getDtSubscription())
            .userType(userType)
            .subscriptionType(subscriptionType)
            .build();
    }
    
    public static UserDto fromEntityToDto(User user) {
        Long subscriptionTypeId = null;
        if(Objects.nonNull(user.getSubscriptionType())) {
            subscriptionTypeId = user.getSubscriptionType().getId();
        }
        
        return UserDto.builder()
            .id(user.getId())
            .name(user.getName())
            .email(user.getEmail())
            .phone(user.getPhone())
            .cpf(user.getCpf())
            .photo(user.getPhoto())
            .photoName(user.getPhotoName())
            .dtExpiration(user.getDtExpiration())
            .dtSubscription(user.getDtSubscription())
            .userTypeId(user.getUserType().getId())
            .subscriptionTypeId(subscriptionTypeId)
            .build();
    }
    

}
