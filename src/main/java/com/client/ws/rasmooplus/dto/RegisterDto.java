package com.client.ws.rasmooplus.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {
    
    @NotBlank
    private String username;

    @NotBlank
    private String password;
    
    @NotNull
    @Min(value = 1)
    @Max(value = 3)
    private Long userTypeId;
    
}
