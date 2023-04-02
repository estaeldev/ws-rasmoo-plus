package com.client.ws.rasmooplus.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.client.ws.rasmooplus.dto.SubscriptionTypeDto;
import com.client.ws.rasmooplus.exception.NotFoundException;
import com.client.ws.rasmooplus.model.SubscriptionType;
import com.client.ws.rasmooplus.repository.SubscriptionTypeRepository;
import com.client.ws.rasmooplus.service.SubscriptionTypeService;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SubscriptionTypeServiceImpl implements SubscriptionTypeService {

    private final SubscriptionTypeRepository subscriptionTypeRepository;
    private final ObjectMapper objectMapper;

    @Override
    public List<SubscriptionTypeDto> findAll() throws JsonMappingException {
        List<SubscriptionType> subscriptionTypeList = this.subscriptionTypeRepository.findAll();
        return this.objectMapper.updateValue(new ArrayList<SubscriptionTypeDto>(), subscriptionTypeList);
    }

    @Override
    public SubscriptionTypeDto findById(Long id) throws JsonMappingException {
        Optional<SubscriptionType> subscriptionTypeOptional = this.subscriptionTypeRepository.findById(id);
        SubscriptionTypeDto subscriptionTypeDto = new SubscriptionTypeDto();
        return subscriptionTypeOptional.map(subscriptionType -> {
            try {
                return this.objectMapper.updateValue(subscriptionTypeDto, subscriptionType);
            } catch (JsonMappingException e) {
                e.printStackTrace();
            }
            return subscriptionTypeDto;

        }).orElseThrow(() -> new NotFoundException("Subscription Type não encontrado"));
    }

    @Override
    public SubscriptionTypeDto create(SubscriptionTypeDto modelDto) throws JsonMappingException {
        SubscriptionType subscriptionType = new SubscriptionType();
        SubscriptionType saved = this.subscriptionTypeRepository.save(this.objectMapper.updateValue(subscriptionType, modelDto));
        return this.objectMapper.updateValue(modelDto, saved);
    }

    @Override
    public SubscriptionTypeDto update(Long id, SubscriptionTypeDto modelDto) {
        return null;
    }

    @Override
    public Void deleteById(Long id) {
        return null;
    }
    
    
}
