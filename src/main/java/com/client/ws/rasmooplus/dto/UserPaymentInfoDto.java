package com.client.ws.rasmooplus.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPaymentInfoDto {
   
    private Long id;

    @Size(min = 16, max = 16, message = "deve conter 16 caracteres")
    private String cardNumber;

    @Min(value = 1)
    @Max(value = 12)
    private Integer cardExpirationMonth;

    private Integer cardExpirationYear;

    @Size(min = 3, max = 3, message = "deve conter 3 caracteres")
    private String cardSecurityCode;

    private BigDecimal price;

    private Integer instalments;

    @Default
    private LocalDate dtPayment = LocalDate.now();

    @NotNull(message = "userId deve ser informado")
    private UUID userId;

}
