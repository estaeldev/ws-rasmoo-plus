package com.client.ws.rasmooplus.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.client.ws.rasmooplus.model.UserCredentials;


public interface UserCredentialsRepository extends JpaRepository<UserCredentials, UUID> {

    Optional<UserCredentials> findByUsername(String username);

}
