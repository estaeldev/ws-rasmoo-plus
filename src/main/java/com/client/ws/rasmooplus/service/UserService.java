package com.client.ws.rasmooplus.service;

import java.util.UUID;

import com.client.ws.rasmooplus.dto.UserDto;

public interface UserService extends JpaRepositoryModel<UserDto> {
    UserDto findById(UUID id);
}
