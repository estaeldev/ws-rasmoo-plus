package com.client.ws.rasmooplus.controller;

import java.io.IOException;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.client.ws.rasmooplus.dto.UserDto;
import com.client.ws.rasmooplus.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> create(@Valid @RequestBody final UserDto userDto) {
        UserDto userDtoCreated = this.userService.create(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDtoCreated);
    }   

    @PatchMapping(value = "/{id}/upload-photo", 
                produces = MediaType.APPLICATION_JSON_VALUE, 
                consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserDto> uploadPhoto(@PathVariable("id") final UUID id, @RequestPart("file") final MultipartFile file) throws IOException {
        UserDto userDto = this.userService.uploadPhoto(id, file);
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }
    
    @GetMapping(value = "/{id}/photo", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public ResponseEntity<byte[]> downloadPhoto(@PathVariable("id") final UUID id) {
        byte[] photo = this.userService.downloadPhoto(id);
        return ResponseEntity.status(HttpStatus.OK).body(photo);
    }
    

}
