package com.client.ws.rasmooplus.controller.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.client.ws.rasmooplus.controller.openapi.v1.PaymentInfoOpenApi;
import com.client.ws.rasmooplus.dto.PaymentProcessDto;
import com.client.ws.rasmooplus.service.PaymentInfoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentInfoController implements PaymentInfoOpenApi{

    private final PaymentInfoService paymentInfoService;

    @PostMapping(value = "/process", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> process(@Valid @RequestBody PaymentProcessDto dto) {
        return ResponseEntity.status(HttpStatus.OK).body(this.paymentInfoService.process(dto));
    }

}
