package com.client.ws.rasmooplus.controller.openapi;

import java.io.IOException;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.client.ws.rasmooplus.configuration.OpenApiConfig;
import com.client.ws.rasmooplus.dto.UserDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = OpenApiConfig.USER)
public interface UserOpenApi {
    
    @Operation(summary = "Cria um novo usuario")
    public ResponseEntity<UserDto> create(@Valid @RequestBody final UserDto userDto);

    @Operation(summary = "Upload da foto do usuario")
    public ResponseEntity<UserDto> uploadPhoto(@PathVariable("id") final UUID id, @RequestPart("file") final MultipartFile file) 
        throws IOException;

    @Operation(summary = "Download da foto do usuario")
    public ResponseEntity<byte[]> downloadPhoto(@PathVariable("id") final UUID id);
    
}
