package com.client.ws.rasmooplus.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.client.ws.rasmooplus.dto.UserDto;
import com.client.ws.rasmooplus.exception.BadRequestException;
import com.client.ws.rasmooplus.exception.NotFoundException;
import com.client.ws.rasmooplus.mapper.UserMapper;
import com.client.ws.rasmooplus.model.jpa.User;
import com.client.ws.rasmooplus.model.jpa.UserType;
import com.client.ws.rasmooplus.repository.jpa.UserRepository;
import com.client.ws.rasmooplus.repository.jpa.UserTypeRepository;
import com.client.ws.rasmooplus.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String PNG = ".png";
    private static final String JPEG = ".jpeg";

    private final UserRepository userRepository;
    private final UserTypeRepository userTypeRepository;
    
    @Override
    public List<UserDto> findAll() {
        List<User> userList = this.userRepository.findAll();
        return userList.stream().map(UserMapper::fromEntityToDto).toList();
    }

    @Override
    public UserDto findById(Long id) {
        return null;

    }

    @Override
    public UserDto findById(UUID id) {
        Optional<User> userOptional = this.userRepository.findById(id);
        return userOptional.map(UserMapper::fromEntityToDto)
            .orElseThrow(() -> new NotFoundException("Error! User: usuario não encontrado."));
    }

    @Override
    public UserDto create(UserDto modelDto) {

        if(Objects.nonNull(modelDto.getId())) {
            throw new BadRequestException("Error! User: id deve ser nulo.");
        }

        Optional<UserType> userTypeOpt = this.userTypeRepository.findById(modelDto.getUserTypeId());

        return userTypeOpt.map(userType -> {
            User user = UserMapper.fromDtoToEntity(modelDto, userType, null);
            this.userRepository.save(user);
            return UserMapper.fromEntityToDto(user);

        }).orElseThrow(() -> new NotFoundException("UserType não encontrado."));

    }
    
    @Override
    public UserDto update(Long id, UserDto modelDto) {
        return null;
        
    }

    @Override
    public Void deleteById(Long id) {
        return null;
    }

    @Override
    public UserDto uploadPhoto(UUID id, MultipartFile file) throws IOException  {
        String fileName = file.getOriginalFilename();

        if(Objects.nonNull(fileName)) {
            String extention = fileName.substring(fileName.indexOf("."));

            if(extention.startsWith(JPEG) || extention.startsWith(PNG)) {
                Optional<User> userOpt = this.userRepository.findById(id);
                
                if(userOpt.isPresent()) {
                    userOpt.get().setPhoto(file.getBytes());
                    userOpt.get().setPhotoName(fileName);
                    User userSaved = this.userRepository.save(userOpt.get());
                    return UserMapper.fromEntityToDto(userSaved);
                } else {
                    throw new BadRequestException("Error! User: usuario não encontrado.");
                }
                
            }

        }

        throw new BadRequestException("Extensão inválida! Deve possuir formato PNG ou JPEG");

    }

    @Override
    public byte[] downloadPhoto(UUID id) {
        UserDto userDto = findById(id);
        
        if(Objects.isNull(userDto.getPhoto())) {
            throw new BadRequestException("Usuario não possui foto");
        }

        return userDto.getPhoto();
    }


}
