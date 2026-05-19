package com.mpesa_daraja_api.mpesa_daraja_api.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record ReversalRequest(
        String initiatorName,
        String securityCredential,
        @NotBlank String transactionId,
        @DecimalMin(value = "1.0") BigDecimal amount,
        String receiverParty,
        String receiverIdentifierType,
        String resultUrl,
        String queueTimeoutUrl,
        String remarks,
        String occasion
) {
}
