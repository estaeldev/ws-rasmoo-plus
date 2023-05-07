package com.client.ws.rasmooplus.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.client.ws.rasmooplus.dto.UserDto;
import com.client.ws.rasmooplus.dto.UserTypeDto;
import com.client.ws.rasmooplus.exception.BadRequestException;
import com.client.ws.rasmooplus.exception.NotFoundException;
import com.client.ws.rasmooplus.mapper.UserMapper;
import com.client.ws.rasmooplus.mapper.UserTypeMapper;
import com.client.ws.rasmooplus.model.User;
import com.client.ws.rasmooplus.repository.UserRepository;
import com.client.ws.rasmooplus.service.UserService;
import com.client.ws.rasmooplus.service.UserTypeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserTypeService userTypeService;
    
    @Override
    public List<UserDto> findAll() {
        
        return Collections.emptyList();
    }

    @Override
    public UserDto findById(Long id) {
        return null;
        
    }

    @Override
    public UserDto findById(UUID id) {
        Optional<User> userOptional = this.userRepository.findById(id);
        return userOptional.map(UserMapper::fromEntityToDto)
            .orElseThrow(() -> new NotFoundException("Error! User: usuario não encontrado."));
    }

    @Override
    public UserDto create(UserDto modelDto) {
        if(Objects.nonNull(modelDto.getId())) {
            throw new BadRequestException("Error! User: id deve ser nulo.");
        }
        UserTypeDto userTypeDto = userTypeService.findById(modelDto.getUserTypeId());

        User user = UserMapper.fromDtoToEntity(modelDto, UserTypeMapper.fromDtoToEntity(userTypeDto), null);
        this.userRepository.save(user);
        return UserMapper.fromEntityToDto(user);
    }
    
    @Override
    public UserDto update(Long id, UserDto modelDto) {
        return null;
        
    }

    @Override
    public Void deleteById(Long id) {
        return null;
    }

    

    
}
