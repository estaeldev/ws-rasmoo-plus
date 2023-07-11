package com.client.ws.rasmooplus.service;

import java.io.IOException;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.client.ws.rasmooplus.dto.UserDto;

public interface UserService extends JpaRepositoryModel<UserDto> {

    UserDto findById(UUID id);
    UserDto uploadPhoto(UUID id, MultipartFile file) throws IOException;

}
