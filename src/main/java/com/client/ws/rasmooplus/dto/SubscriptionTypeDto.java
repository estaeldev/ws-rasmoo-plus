package com.client.ws.rasmooplus.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubscriptionTypeDto extends RepresentationModel<SubscriptionTypeDto> implements Serializable {
    
    private Long id;

    @NotBlank(message = "não pode ser nulo ou vazio")
    @Size(min = 5, max = 30, message = "deve ter tamanho entre 5 e 30")
    private String name;

    @Max(value = 12, message = "não pode ser maior que 12")
    @Min(value = 1, message = "não pode ser menor que 1")
    private Integer accessMonths;

    @NotNull(message = "não pode ser nulo ou vazio")
    private BigDecimal price;
    
    @NotBlank(message = "não pode ser nulo ou vazio")
    @Size(min = 5, max = 15, message = "deve ter tamanho entre 5 e 15")
    private String productKey;
    
}
