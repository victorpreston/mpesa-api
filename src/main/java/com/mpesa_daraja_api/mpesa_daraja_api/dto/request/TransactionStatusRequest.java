package com.mpesa_daraja_api.mpesa_daraja_api.dto.request;

import jakarta.validation.constraints.NotBlank;

public record TransactionStatusRequest(
        String initiatorName,
        String securityCredential,
        @NotBlank String transactionId,
        String partyA,
        String identifierType,
        String resultUrl,
        String queueTimeoutUrl,
        String remarks,
        String occasion
) {
}
