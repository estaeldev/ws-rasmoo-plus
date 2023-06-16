package com.client.ws.rasmooplus.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceTest {
    
    @Autowired
    private UserService userService;


    @Test
    void contextLoads() {
        this.userService.sendRecoveryCode("");
        Assertions.assertTrue(true);
    }
    

}
