package com.client.ws.rasmooplus.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.client.ws.rasmooplus.dto.UserTypeDto;
import com.client.ws.rasmooplus.service.UserTypeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user-type")
@RequiredArgsConstructor
public class UserTypeController {

    private final UserTypeService userTypeService;

    @GetMapping
    public ResponseEntity<List<UserTypeDto>> findAll() {
        List<UserTypeDto> userTypeDto = this.userTypeService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(userTypeDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserTypeDto> findById(@PathVariable("id") final Long id) {
        UserTypeDto userTypeDto = this.userTypeService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(userTypeDto);
    }
    
    @PostMapping
    public ResponseEntity<UserTypeDto> create(@Valid @RequestBody final UserTypeDto userTypeDto) {
        UserTypeDto userType = this.userTypeService.create(userTypeDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userType);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserTypeDto> update(@PathVariable("id") final Long id, @RequestBody final UserTypeDto dto ) {
        UserTypeDto userTypeDto = this.userTypeService.update(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(userTypeDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") final Long id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(this.userTypeService.deleteById(id));
    }
    
}
