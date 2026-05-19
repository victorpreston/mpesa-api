package com.mpesa_daraja_api.mpesa_daraja_api.dto.request;

public record AccountBalanceRequest(
        String initiatorName,
        String securityCredential,
        String partyA,
        String identifierType,
        String resultUrl,
        String queueTimeoutUrl,
        String remarks
) {
}
