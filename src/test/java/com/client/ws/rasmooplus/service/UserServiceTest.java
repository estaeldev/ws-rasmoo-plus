package com.client.ws.rasmooplus.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceTest {
    
    @Autowired
    private AuthenticationService authenticationService;


    @Test
    void contextLoads() {
        this.authenticationService.sendRecoveryCode("kohen45178@bodeem.com");
        Assertions.assertTrue(true);
    }
    

}
