package com.mpesa_daraja_api.mpesa_daraja_api.dto.requests;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record StkPushRequest(
        @NotBlank String phoneNumber,
        @DecimalMin(value = "1.0") BigDecimal amount,
        String accountReference,
        String transactionDescription,
        String transactionType,
        String shortcode,
        String callbackUrl
) {
}
