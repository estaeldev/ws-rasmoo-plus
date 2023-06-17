package com.client.ws.rasmooplus.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.client.ws.rasmooplus.service.impl.UserDetailsServiceImpl;

@SpringBootTest
class UserServiceTest {
    
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;


    @Test
    void contextLoads() {
        this.userDetailsServiceImpl.sendRecoveryCode("f79ccfef48@mymaily.lol");
        Assertions.assertTrue(true);
    }
    

}
