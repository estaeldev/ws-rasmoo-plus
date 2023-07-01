package com.client.ws.rasmooplus.repository.jpa;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;

import com.client.ws.rasmooplus.model.jpa.UserCredentials;
import com.client.ws.rasmooplus.model.jpa.UserType;

@AutoConfigureTestDatabase
@AutoConfigureDataJpa
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(UserCredentialsRepository.class)
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
class UserCredentialsRepositoryTest {

    @Autowired
    private UserCredentialsRepository userCredentialsRepository;

    @Autowired
    private UserTypeRepository userTypeRepository;

    @BeforeAll
    void loadDatas() {

        UserType userType = UserType.builder()
            .id(null)
            .name("Aluno")
            .description("Aluno da plataforma")
            .build();

        this.userTypeRepository.save(userType);

        UserCredentials userCredentials = UserCredentials.builder()
            .id(null)
            .username("tael@gmail.com")
            .password("tael123")
            .userType(userType)
            .build();
        
        this.userCredentialsRepository.save(userCredentials);
    }

    @Test
    void testFindByUsername_when_getUsername_then_returnUserCredentials() {

        Assertions.assertNotNull(this.userCredentialsRepository.findByUsername("tael@gmail.com").get().getId());

        Assertions.assertEquals("tael@gmail.com", 
            this.userCredentialsRepository.findByUsername("tael@gmail.com").get().getUsername());

        Assertions.assertEquals("Aluno", 
            this.userCredentialsRepository.findByUsername("tael@gmail.com").get().getUserType().getName());
        
    }


}
