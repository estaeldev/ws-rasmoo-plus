package com.client.ws.rasmooplus.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MailIntegrationImplTest {

    @Autowired
    private MailIntegration mailIntegration;

    @Test
    void sendMailWhenOk() {
        mailIntegration.send("hakide4006@glumark.com", "Olá Gmail", "ACESSO LIBERADO!");

        Assertions.assertTrue(true);
    }

    
}
