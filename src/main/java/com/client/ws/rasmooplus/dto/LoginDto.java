package com.client.ws.rasmooplus.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

    @NotBlank(message = "não pode ser nulo ou vazio")
    private String username;

    @NotBlank(message = "não pode ser nulo ou vazio")
    private String password;

}
