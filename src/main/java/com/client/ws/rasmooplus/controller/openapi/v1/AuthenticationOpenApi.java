package com.client.ws.rasmooplus.controller.openapi.v1;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.client.ws.rasmooplus.dto.AuthenticationResponseDto;
import com.client.ws.rasmooplus.dto.LoginDto;
import com.client.ws.rasmooplus.dto.RegisterDto;
import com.client.ws.rasmooplus.dto.redis.UserRecoveryCodeDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;

@Tag(name = "Authentication")
public interface AuthenticationOpenApi {
    
    @Operation(summary = "Autentica um usuario no sistema")
    public ResponseEntity<AuthenticationResponseDto> authenticate(@Valid @RequestBody final LoginDto loginDto);

    @Operation(summary = "Registra um usuario no sistema")
    public ResponseEntity<AuthenticationResponseDto> register(@Valid @RequestBody final RegisterDto registerDto);

    @Operation(summary = "Envia o codigo de autenticação")
    public ResponseEntity<Void> sendRecoveryCode(@PathParam("email") final String email);

    @Operation(summary = "Valida o codigo de autenticação")
    public ResponseEntity<Boolean> recoveryCodeIsValid(@Valid @RequestBody final UserRecoveryCodeDto recoveryCodeDto);

    @Operation(summary = "Atualiza a senha do usuario pelo codigo de autenticação")
    public ResponseEntity<Void> updatePasswordByRecoveryCode(@Valid @RequestBody final UserRecoveryCodeDto dto);
    
}
