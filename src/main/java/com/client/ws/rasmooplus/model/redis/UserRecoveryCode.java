package com.client.ws.rasmooplus.model.redis;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

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
public class UserRecoveryCode implements Serializable {
    
    @Id
    private UUID id;

    @Indexed
    private String email;

    private String code;

    @Builder.Default
    private LocalDateTime createDate = LocalDateTime.now(); 

}
