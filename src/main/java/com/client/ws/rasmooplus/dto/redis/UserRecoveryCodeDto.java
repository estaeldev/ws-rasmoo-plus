package com.client.ws.rasmooplus.dto.redis;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRecoveryCodeDto {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String code;

}
