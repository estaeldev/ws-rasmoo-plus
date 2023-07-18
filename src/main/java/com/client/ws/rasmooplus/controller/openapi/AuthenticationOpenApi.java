package com.client.ws.rasmooplus.controller.openapi;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.client.ws.rasmooplus.configuration.OpenApiConfig;
import com.client.ws.rasmooplus.dto.AuthenticationResponseDto;
import com.client.ws.rasmooplus.dto.LoginDto;
import com.client.ws.rasmooplus.dto.RegisterDto;
import com.client.ws.rasmooplus.dto.redis.UserRecoveryCodeDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;

@Tag(name = OpenApiConfig.AUTENTICACAO)
public interface AuthenticationOpenApi {
    
    @Operation(summary = "Realiza a autenticação do usuario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario e senha validados com sucesso"), 
        @ApiResponse(responseCode = "400", description = "Usuario ou senha inválido"),
        @ApiResponse(responseCode = "404", description = "Usuario não foi encontrado")
    })
    public ResponseEntity<AuthenticationResponseDto> authenticate(@Valid @RequestBody final LoginDto loginDto);
    

    @Operation(summary = "Registra um usuario no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuario criado com sucesso"), 
        @ApiResponse(responseCode = "400", description = "Dados inválido"),
        @ApiResponse(responseCode = "404", description = "Algum dado não foi encontrado")
    })
    public ResponseEntity<AuthenticationResponseDto> register(@Valid @RequestBody final RegisterDto registerDto);


    @Operation(summary = "Envia o codigo de autenticação para o email do usuario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Codigo de autenticação enviado com sucesso"), 
        @ApiResponse(responseCode = "400", description = "Dados inválido"),
        @ApiResponse(responseCode = "404", description = "Algum dado não foi encontrado")
    })
    public ResponseEntity<Void> sendRecoveryCode(@PathParam("email") final String email);


    @Operation(summary = "Valida o codigo de autenticação")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Codigo de autenticação validado com sucesso"), 
        @ApiResponse(responseCode = "400", description = "Dados inválido"),
        @ApiResponse(responseCode = "404", description = "Algum dado não foi encontrado")
    })
    public ResponseEntity<Boolean> recoveryCodeIsValid(@Valid @RequestBody final UserRecoveryCodeDto recoveryCodeDto);


    @Operation(summary = "Atualiza a senha do usuario pelo codigo de autenticação")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Senha atualizada com sucesso"), 
        @ApiResponse(responseCode = "400", description = "Dados inválido"),
        @ApiResponse(responseCode = "404", description = "Algum dado não foi encontrado")
    })
    public ResponseEntity<Void> updatePasswordByRecoveryCode(@Valid @RequestBody final UserRecoveryCodeDto dto);
    
}
