package com.client.ws.rasmooplus.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.client.ws.rasmooplus.dto.UserDto;
import com.client.ws.rasmooplus.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    
    @PostMapping
    public ResponseEntity<UserDto> create(@Valid @RequestBody final UserDto userDto) {
        UserDto userDtoCreated = this.userService.create(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDtoCreated);
    }   
    
    @PostMapping("/send-recovery-code")
    public ResponseEntity<?> sendRecoveryCode(@RequestBody Object email) {
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.sendRecoveryCode(null));
    }

}
