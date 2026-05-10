package com.grainindustries.mpesa_c2b_api.dto.requests;

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
