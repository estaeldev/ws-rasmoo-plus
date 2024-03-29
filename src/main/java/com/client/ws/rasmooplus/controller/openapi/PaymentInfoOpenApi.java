package com.client.ws.rasmooplus.controller.openapi;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.client.ws.rasmooplus.configuration.OpenApiConfig;
import com.client.ws.rasmooplus.dto.PaymentProcessDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = OpenApiConfig.PAYMENT_INFO)
public interface PaymentInfoOpenApi {

    @Operation(summary = "Processa o pagamento")
    public ResponseEntity<Boolean> process(@Valid @RequestBody PaymentProcessDto dto);
    
}
