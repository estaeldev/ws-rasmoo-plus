package com.client.ws.rasmooplus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.client.ws.rasmooplus.dto.SubscriptionTypeDto;
import com.client.ws.rasmooplus.service.SubscriptionTypeService;
import com.fasterxml.jackson.databind.JsonMappingException;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/subscription-type")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SubscriptionTypeController {

    private final SubscriptionTypeService subscriptionTypeService;

    @GetMapping
    public ResponseEntity<List<SubscriptionTypeDto>> findAll() throws JsonMappingException {
        List<SubscriptionTypeDto> subscriptionTypeDtoList = this.subscriptionTypeService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(subscriptionTypeDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionTypeDto> findById(@PathVariable("id") final Long id) throws JsonMappingException {
        SubscriptionTypeDto subscriptionTypeDto = this.subscriptionTypeService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(subscriptionTypeDto);
    }
    
    @PostMapping
    public ResponseEntity<SubscriptionTypeDto> create(@RequestBody final SubscriptionTypeDto subscriptionTypeDto) 
    throws JsonMappingException {
        
        SubscriptionTypeDto subscriptionTypeCreate = this.subscriptionTypeService.create(subscriptionTypeDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(subscriptionTypeCreate);
    }

}
