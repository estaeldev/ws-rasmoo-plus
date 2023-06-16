package com.client.ws.rasmooplus.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.client.ws.rasmooplus.model.jpa.UserPaymentInfo;

public interface UserPaymentInfoRepository extends JpaRepository<UserPaymentInfo, Long> {
    
}
