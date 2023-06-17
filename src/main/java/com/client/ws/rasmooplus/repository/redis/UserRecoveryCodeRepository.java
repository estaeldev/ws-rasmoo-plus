package com.client.ws.rasmooplus.repository.redis;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.client.ws.rasmooplus.model.redis.UserRecoveryCode;


public interface UserRecoveryCodeRepository extends CrudRepository<UserRecoveryCode, UUID> {
    
    Optional<UserRecoveryCode> findByEmail(String email);

}
