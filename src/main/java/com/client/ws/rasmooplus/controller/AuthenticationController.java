package com.client.ws.rasmooplus.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.client.ws.rasmooplus.dto.AuthenticationResponseDto;
import com.client.ws.rasmooplus.dto.LoginDto;
import com.client.ws.rasmooplus.dto.RegisterDto;
import com.client.ws.rasmooplus.service.AuthenticationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseDto> authenticate(@Valid @RequestBody final LoginDto loginDto) {
        return ResponseEntity.status(HttpStatus.OK).body(this.authenticationService.authenticate(loginDto));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDto> register(@Valid @RequestBody final RegisterDto registerDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.authenticationService.register(registerDto));
    }

}
