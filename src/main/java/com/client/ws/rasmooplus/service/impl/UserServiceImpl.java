package com.client.ws.rasmooplus.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.client.ws.rasmooplus.dto.UserDto;
import com.client.ws.rasmooplus.exception.BadRequestException;
import com.client.ws.rasmooplus.exception.NotFoundException;
import com.client.ws.rasmooplus.mapper.UserMapper;
import com.client.ws.rasmooplus.model.jpa.User;
import com.client.ws.rasmooplus.model.jpa.UserType;
import com.client.ws.rasmooplus.repository.jpa.UserRepository;
import com.client.ws.rasmooplus.repository.jpa.UserTypeRepository;
import com.client.ws.rasmooplus.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserTypeRepository userTypeRepository;
    
    @Override
    public List<UserDto> findAll() {
        List<User> userList = this.userRepository.findAll();
        return userList.stream().map(UserMapper::fromEntityToDto).toList();
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

        Optional<UserType> userTypeOpt = this.userTypeRepository.findById(modelDto.getUserTypeId());

        return userTypeOpt.map(userType -> {
            User user = UserMapper.fromDtoToEntity(modelDto, userType, null);
            this.userRepository.save(user);
            return UserMapper.fromEntityToDto(user);

        }).orElseThrow(() -> new NotFoundException("UserType não encontrado."));

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
