package com.client.ws.rasmooplus.controller.openapi.v1;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.client.ws.rasmooplus.dto.UserTypeDto;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "UserType")
public interface UserTypeOpenApi {

    @Operation(summary = "Retorna todos os UserTypes")
    public ResponseEntity<List<UserTypeDto>> findAll();
    
    @Operation(summary = "Retorna o UserType por Id")
    public ResponseEntity<UserTypeDto> findById(@PathVariable("id") final Long id);
    
    @Hidden
    public ResponseEntity<UserTypeDto> create(@Valid @RequestBody final UserTypeDto userTypeDto);

    @Hidden
    public ResponseEntity<UserTypeDto> update(@PathVariable("id") final Long id, @Valid @RequestBody final UserTypeDto dto );
    
    @Hidden
    public <T extends ResponseEntity<T>> ResponseEntity<?> deleteById(@PathVariable("id") final Long id);
    
}
