package com.client.ws.rasmooplus.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.client.ws.rasmooplus.dto.SubscriptionTypeDto;
import com.client.ws.rasmooplus.service.SubscriptionTypeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/subscription-type")
@RequiredArgsConstructor
public class SubscriptionTypeController {

    private final SubscriptionTypeService subscriptionTypeService;

    @GetMapping
    public ResponseEntity<List<SubscriptionTypeDto>> findAll() {
        List<SubscriptionTypeDto> subscriptionTypeDtoList = this.subscriptionTypeService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(subscriptionTypeDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionTypeDto> findById(@PathVariable("id") final Long id) {
        SubscriptionTypeDto subscriptionTypeDto = this.subscriptionTypeService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(subscriptionTypeDto);
    }
    
    @PostMapping
    public ResponseEntity<SubscriptionTypeDto> create(@Valid @RequestBody final SubscriptionTypeDto subscriptionTypeDto) {
        SubscriptionTypeDto subscriptionTypeCreate = this.subscriptionTypeService.create(subscriptionTypeDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(subscriptionTypeCreate);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubscriptionTypeDto> update(@PathVariable("id") final Long id, @RequestBody final SubscriptionTypeDto dto ) {
        SubscriptionTypeDto subscriptionTypeDtoUpdate = this.subscriptionTypeService.update(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(subscriptionTypeDtoUpdate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") final Long id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(this.subscriptionTypeService.deleteById(id));
    }

}
