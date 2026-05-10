package com.grainindustries.mpesa_c2b_api.dto.requests;

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
