package com.mpesa_daraja_api.mpesa_daraja_api.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record B2cPaymentRequest(
        String initiatorName,
        String securityCredential,
        String commandId,
        @DecimalMin(value = "1.0") BigDecimal amount,
        String partyA,
        @NotBlank String partyB,
        String remarks,
        String queueTimeoutUrl,
        String resultUrl,
        String occasion
) {
}
