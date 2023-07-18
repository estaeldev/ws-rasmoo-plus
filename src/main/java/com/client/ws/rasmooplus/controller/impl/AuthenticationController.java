package com.client.ws.rasmooplus.controller.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.client.ws.rasmooplus.controller.openapi.AuthenticationOpenApi;
import com.client.ws.rasmooplus.dto.AuthenticationResponseDto;
import com.client.ws.rasmooplus.dto.LoginDto;
import com.client.ws.rasmooplus.dto.RegisterDto;
import com.client.ws.rasmooplus.dto.redis.UserRecoveryCodeDto;
import com.client.ws.rasmooplus.service.AuthenticationService;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController implements AuthenticationOpenApi{

    private final AuthenticationService authenticationService;
    
    @PostMapping(value = "/authenticate", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthenticationResponseDto> authenticate(@Valid @RequestBody final LoginDto loginDto) {
        return ResponseEntity.status(HttpStatus.OK).body(this.authenticationService.authenticate(loginDto));
    }

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthenticationResponseDto> register(@Valid @RequestBody final RegisterDto registerDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.authenticationService.register(registerDto));
    }

    @GetMapping("/recovery-code/send")
    public ResponseEntity<Void> sendRecoveryCode(@PathParam("email") final String email) {
        this.authenticationService.sendRecoveryCode(email);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @PostMapping(value = "/recovery-code/isValid", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> recoveryCodeIsValid(@Valid @RequestBody final UserRecoveryCodeDto recoveryCodeDto) {
        return ResponseEntity.status(HttpStatus.OK).body(this.authenticationService.recoveryCodeIsValid(recoveryCodeDto));
    }

    @PatchMapping(value = "/recovery-code/password", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updatePasswordByRecoveryCode(@Valid @RequestBody final UserRecoveryCodeDto dto) {
        this.authenticationService.updatePasswordByRecoveryCode(dto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

}
