package com.client.ws.rasmooplus.repository.redis;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.client.ws.rasmooplus.model.redis.RecoveryCode;


public interface RecoveryCodeRepository extends CrudRepository<RecoveryCode, UUID> {
    
    Optional<RecoveryCode> findByEmail(String email);

}
