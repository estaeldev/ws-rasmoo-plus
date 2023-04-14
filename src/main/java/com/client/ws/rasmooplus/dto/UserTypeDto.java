package com.client.ws.rasmooplus.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserTypeDto {
    
    private Long id;

    @NotBlank(message = "não pode ser nulo ou vazio")
    private String name;

    @NotBlank(message = "não pode ser nulo ou vazio")
    private String description;

}
