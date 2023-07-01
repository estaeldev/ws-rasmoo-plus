package com.client.ws.rasmooplus.repository.jpa;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

import com.client.ws.rasmooplus.model.jpa.SubscriptionType;

@AutoConfigureDataJpa
@AutoConfigureTestDatabase
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(SubscriptionTypeRepository.class)
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
class SubscriptionTypeRepositoryTest {

    @Autowired
    private SubscriptionTypeRepository subscriptionTypeRepository;

    @BeforeAll
    void loadDatas() {
        List<SubscriptionType> subscriptionTypeList = new ArrayList<>();

        SubscriptionType subscriptionType1 = SubscriptionType.builder()
            .id(null)
            .name("VITALICIO")
            .accessMonths(null)
            .price(BigDecimal.valueOf(997))
            .productKey("VITALICIO2023")
            .build();

        SubscriptionType subscriptionType2 = SubscriptionType.builder()
            .id(null)
            .name("ANUAL")
            .accessMonths(12)
            .price(BigDecimal.valueOf(297))
            .productKey("ANUAL2023")
            .build();

        SubscriptionType subscriptionType3 = SubscriptionType.builder()
            .id(null)
            .name("MENSAL")
            .accessMonths(1)
            .price(BigDecimal.valueOf(35))
            .productKey("MENSAL2023")
            .build();

        subscriptionTypeList.add(subscriptionType1);
        subscriptionTypeList.add(subscriptionType2);
        subscriptionTypeList.add(subscriptionType3);

        subscriptionTypeRepository.saveAll(subscriptionTypeList);

    }


    @Test
    void testFindByProductKey_when_getProductKey_then_returnSubscriptionType() {
        
        Assertions.assertEquals("VITALICIO", 
            subscriptionTypeRepository.findByProductKey("VITALICIO2023").get().getName());
        
        Assertions.assertEquals("ANUAL", 
            subscriptionTypeRepository.findByProductKey("ANUAL2023").get().getName());

        Assertions.assertEquals("MENSAL", 
            subscriptionTypeRepository.findByProductKey("MENSAL2023").get().getName());

    }


}
