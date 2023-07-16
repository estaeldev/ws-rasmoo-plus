package com.client.ws.rasmooplus.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.io.FileInputStream;
import java.util.UUID;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.client.ws.rasmooplus.controller.impl.UserController;
import com.client.ws.rasmooplus.dto.UserDto;
import com.client.ws.rasmooplus.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureDataJpa
@AutoConfigureTestDatabase
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(UserController.class)
@ActiveProfiles("test")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;


    @Test
    void create_when_dtoIsOk_then_returnUserDto() throws Exception {

        UserDto userDto = UserDto.builder()
            .id(UUID.randomUUID())
            .name("Estael")
            .email("tael@gmail.com")
            .phone("11122233311")
            .cpf("03270811209")
            .userTypeId(1L)
            .build();

        when(this.userService.create(userDto)).thenReturn(userDto);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/users")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(this.objectMapper.writeValueAsString(userDto))
            )
            .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.notNullValue()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.subscriptionTypeId", Matchers.nullValue()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.userTypeId", Matchers.is(1)))
            .andExpect(MockMvcResultMatchers.status().isCreated());


        Mockito.verify(this.userService, times(1)).create(any());

    }

    @Test
    void create_when_dtoMissingValue_then_returnBadRequest() throws Exception {

        UserDto userDto = UserDto.builder()
            .id(null)
            .name("")
            .email("")
            .phone("")
            .cpf("")
            .userTypeId(null)
            .build();

        this.mockMvc.perform(MockMvcRequestBuilders.post("/users")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(this.objectMapper.writeValueAsString(userDto))
            )
            .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());


        Mockito.verify(this.userService, times(0)).create(any());

    }

    @Test
    void testUploadPhoto_when_receiveMultPartFile_then_return200OK() throws Exception {
        UUID uuid = UUID.randomUUID();
        FileInputStream file = new FileInputStream("src/test/resources/static/avatar.png");

        MockMultipartFile multipartFile = new MockMultipartFile(
            "file", "avatar.pgn", MediaType.MULTIPART_FORM_DATA_VALUE, file);
        
        Mockito.when(this.userService.uploadPhoto(uuid, multipartFile)).thenReturn(new UserDto());
        
        MockMultipartHttpServletRequestBuilder builder = 
            MockMvcRequestBuilders.multipart("/users/{id}/upload-photo", uuid).file(multipartFile);

        builder.with(request -> {
                request.setMethod(HttpMethod.PATCH.name());
                return request;
        });

        this.mockMvc.perform(builder)
            .andExpect(MockMvcResultMatchers.status().isOk());
        
        Mockito.verify(this.userService, times(1)).uploadPhoto(any(), any());
        
    }

    @Test
    void testDownloadPhoto_when_thereIsPhotoInDatabase_then_return200OK() throws Exception {
        UUID uuid = UUID.randomUUID();

        this.mockMvc.perform(MockMvcRequestBuilders.get("/users/{id}/photo", uuid)
            .contentType(MediaType.IMAGE_PNG)
            .contentType(MediaType.IMAGE_JPEG)
            )
            .andExpect(MockMvcResultMatchers.status().isOk());

        
    }

}
