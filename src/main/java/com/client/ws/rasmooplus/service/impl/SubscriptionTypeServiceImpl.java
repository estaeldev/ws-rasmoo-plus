package com.client.ws.rasmooplus.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.client.ws.rasmooplus.dto.SubscriptionTypeDto;
import com.client.ws.rasmooplus.exception.BadRequestException;
import com.client.ws.rasmooplus.exception.NotFoundException;
import com.client.ws.rasmooplus.mapper.SubscriptionTypeMapper;
import com.client.ws.rasmooplus.model.SubscriptionType;
import com.client.ws.rasmooplus.repository.SubscriptionTypeRepository;
import com.client.ws.rasmooplus.service.SubscriptionTypeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubscriptionTypeServiceImpl implements SubscriptionTypeService {

    private final SubscriptionTypeRepository subscriptionTypeRepository;

    @Override
    public List<SubscriptionTypeDto> findAll() {
        List<SubscriptionType> subscriptionTypeList = this.subscriptionTypeRepository.findAll();
        return subscriptionTypeList.stream().map(SubscriptionTypeMapper::fromEntityToDto).toList();
    }

    @Override
    public SubscriptionTypeDto findById(Long id) {
        Optional<SubscriptionType> subscriptionTypeOptional = this.subscriptionTypeRepository.findById(id);
        return subscriptionTypeOptional.map(SubscriptionTypeMapper::fromEntityToDto)
            .orElseThrow(() -> new NotFoundException("Error! Subscription Type: Id não encontrado."));
    }

    @Override
    public SubscriptionTypeDto create(SubscriptionTypeDto modelDto) {
        if(Objects.nonNull(modelDto.getId())) {
            throw new BadRequestException("Error! Subscription Type: Id deve ser nulo.");
        }
        SubscriptionType saved = this.subscriptionTypeRepository.save(SubscriptionTypeMapper.fromDtoToEntity(modelDto));
        return SubscriptionTypeMapper.fromEntityToDto(saved);
    }

    @Override
    public SubscriptionTypeDto update(Long id, SubscriptionTypeDto modelDto) {
        if(Objects.nonNull(findById(id))) {
            modelDto.setId(id);
            SubscriptionType subscriptionTypeUpdate = this.subscriptionTypeRepository
                .save(SubscriptionTypeMapper.fromDtoToEntity(modelDto));
            return SubscriptionTypeMapper.fromEntityToDto(subscriptionTypeUpdate);
        }
        return null;
    }

    @Override
    public Void deleteById(Long id) {
        if(Objects.nonNull(findById(id))) {
            this.subscriptionTypeRepository.deleteById(id);
        }

        return null;
    }
    
    
}
