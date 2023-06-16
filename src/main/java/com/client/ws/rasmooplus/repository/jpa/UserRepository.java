package com.client.ws.rasmooplus.repository.jpa;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.client.ws.rasmooplus.model.jpa.User;

public interface UserRepository extends JpaRepository<User, UUID> {
    
}
