package com.client.ws.rasmooplus.model.redis;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.redis.core.RedisHash;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@RedisHash("recovery_code")
public class RecoveryCode implements Serializable {
    
    @Id
    private UUID id;

    private String email;

    private String code;

    @Builder.Default
    private LocalDateTime createDate = LocalDateTime.now(); 

}
