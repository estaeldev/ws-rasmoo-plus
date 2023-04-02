package com.client.ws.rasmooplus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.client.ws.rasmooplus.model.UserType;

public interface UserTypeRepository extends JpaRepository<UserType, Long> {
    
}
