package com.client.ws.rasmooplus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.client.ws.rasmooplus.model.SubscriptionType;

public interface SubscriptionTypeRepository extends JpaRepository<SubscriptionType, Long> {
    
}
