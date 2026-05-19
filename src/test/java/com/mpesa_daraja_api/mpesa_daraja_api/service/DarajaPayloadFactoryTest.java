package com.mpesa_daraja_api.mpesa_daraja_api.service;

import com.mpesa_daraja_api.mpesa_daraja_api.config.DarajaProperties;
import com.mpesa_daraja_api.mpesa_daraja_api.dto.request.StkPushRequest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Clock;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DarajaPayloadFactoryTest {

    @Test
    void stkPushPayloadUsesConfiguredDefaults() {
        DarajaProperties properties = new DarajaProperties();
        properties.setShortcode("600984");
        properties.setLipaNaMpesaShortcode("174379");
        properties.setPasskey("passkey");
        properties.setCallbackUrl("https://example.com/stk");
        DarajaPayloadFactory factory = new DarajaPayloadFactory(properties, Clock.systemDefaultZone());

        var payload = factory.stkPush(new StkPushRequest(
                "254700000000",
                new BigDecimal("100.40"),
                "LOAN-123",
                "Loan payment",
                null,
                null,
                null
        ));

        assertEquals("174379", payload.businessShortCode());
        assertEquals("CustomerPayBillOnline", payload.transactionType());
        assertEquals("100", payload.amount());
        assertEquals("254700000000", payload.partyA());
        assertEquals("174379", payload.partyB());
        assertEquals("https://example.com/stk", payload.callbackUrl());
        assertEquals("LOAN-123", payload.accountReference());
    }
}
