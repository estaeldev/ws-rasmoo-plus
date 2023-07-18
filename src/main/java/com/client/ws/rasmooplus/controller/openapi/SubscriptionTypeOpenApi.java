package com.client.ws.rasmooplus.controller.openapi;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.client.ws.rasmooplus.configuration.OpenApiConfig;
import com.client.ws.rasmooplus.dto.SubscriptionTypeDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = OpenApiConfig.SUBSCRIPTION_TYPE)
public interface SubscriptionTypeOpenApi {

    @Operation(summary = "Busca todos os SubscriptionTypes")
    public ResponseEntity<List<SubscriptionTypeDto>> findAll();

    @Operation(summary = "Busca o SubscriptionType por Id")
    public ResponseEntity<SubscriptionTypeDto> findById(@PathVariable("id") final Long id);

    @Operation(summary = "Criar um novo Subscription Type")
    public ResponseEntity<SubscriptionTypeDto> create(@Valid @RequestBody final SubscriptionTypeDto subscriptionTypeDto);

    @Operation(summary = "Atualização completa de um SubscriptionType")
    public ResponseEntity<SubscriptionTypeDto> update(@PathVariable("id") final Long id, 
        @Valid @RequestBody final SubscriptionTypeDto dto );
    
    @Operation(summary = "Excluir um SubscriptionType")
    public ResponseEntity<Void> deleteById(@PathVariable("id") final Long id);
    
}
