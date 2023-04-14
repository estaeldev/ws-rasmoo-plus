package com.client.ws.rasmooplus.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.client.ws.rasmooplus.dto.UserTypeDto;
import com.client.ws.rasmooplus.exception.NotFoundException;
import com.client.ws.rasmooplus.mapper.UserTypeMapper;
import com.client.ws.rasmooplus.model.UserType;
import com.client.ws.rasmooplus.repository.UserTypeRepository;
import com.client.ws.rasmooplus.service.UserTypeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserTypeServiceImpl implements UserTypeService {

    private final UserTypeRepository userTypeRepository;

    @Override
    public List<UserTypeDto> findAll() {
        List<UserType> userTypeList = this.userTypeRepository.findAll();
        return userTypeList.stream().map(UserTypeMapper::fromEntityToDto).toList();
    }

    @Override
    public UserTypeDto findById(Long id) {
        Optional<UserType> userTypeOptional = this.userTypeRepository.findById(id);
        return userTypeOptional.map(UserTypeMapper::fromEntityToDto)
            .orElseThrow(() -> new NotFoundException("Error! UserType: userType não encontrado."));
    }

    @Override
    public UserTypeDto create(UserTypeDto modelDto) {
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    public UserTypeDto update(Long id, UserTypeDto modelDto) {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public Void deleteById(Long id) {
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }
    
}
