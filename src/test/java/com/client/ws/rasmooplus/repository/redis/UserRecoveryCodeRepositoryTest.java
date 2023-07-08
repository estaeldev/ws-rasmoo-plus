package com.client.ws.rasmooplus.repository.redis;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.AutoConfigureDataRedis;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;

import com.client.ws.rasmooplus.model.redis.UserRecoveryCode;

@AutoConfigureDataRedis
@AutoConfigureTestDatabase
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(UserRecoveryCodeRepository.class)
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
class UserRecoveryCodeRepositoryTest {

    @Autowired
    private UserRecoveryCodeRepository userRecoveryCodeRepository;

    @BeforeAll
    void loadDatas() {

        List<UserRecoveryCode> userRecoveryCodeList = new ArrayList<>();

        UserRecoveryCode userRecoveryCode1 = UserRecoveryCode.builder()
            .email("usuario1@gmail.com")
            .code("111111")
            .build();
        
        UserRecoveryCode userRecoveryCode2 = UserRecoveryCode.builder()
            .email("usuario2@gmail.com")
            .code("222222")
            .build();
        
        UserRecoveryCode userRecoveryCode3 = UserRecoveryCode.builder()
            .email("usuario3@gmail.com")
            .code("333333")
            .build();

        userRecoveryCodeList.add(userRecoveryCode1);
        userRecoveryCodeList.add(userRecoveryCode2);
        userRecoveryCodeList.add(userRecoveryCode3);


        this.userRecoveryCodeRepository.saveAll(userRecoveryCodeList);

    }


    @Test
    void testFindByEmail_when_getByEmail_then_returnUserRecoveryCode() {

        Assertions.assertEquals("111111", 
            userRecoveryCodeRepository.findByEmail("usuario1@gmail.com").get().getCode());
        
        Assertions.assertEquals("222222", 
            userRecoveryCodeRepository.findByEmail("usuario2@gmail.com").get().getCode());

        Assertions.assertEquals("333333", 
            userRecoveryCodeRepository.findByEmail("usuario3@gmail.com").get().getCode());

    }


}
