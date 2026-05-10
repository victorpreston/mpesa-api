package com.grainindustries.mpesa_c2b_api.dto.requests;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record B2bPaymentRequest(
        String initiatorName,
        String securityCredential,
        String commandId,
        String senderIdentifierType,
        String receiverIdentifierType,
        @DecimalMin(value = "1.0") BigDecimal amount,
        String partyA,
        @NotBlank String partyB,
        String accountReference,
        String remarks,
        String queueTimeoutUrl,
        String resultUrl
) {
}
