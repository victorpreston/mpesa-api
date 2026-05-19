package com.mpesa_daraja_api.mpesa_daraja_api.service;

import com.mpesa_daraja_api.mpesa_daraja_api.config.DarajaProperties;
import com.mpesa_daraja_api.mpesa_daraja_api.dto.response.DarajaApiResponse;
import com.mpesa_daraja_api.mpesa_daraja_api.interfaces.DarajaSdk;
import com.mpesa_daraja_api.mpesa_daraja_api.service.c2b.MpesaSimulationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MpesaSimulationServiceTest {

    private DarajaSdk darajaSdk;
    private MpesaSimulationService service;

    @BeforeEach
    void setUp() {
        darajaSdk = mock(DarajaSdk.class);
        DarajaProperties properties = new DarajaProperties();
        properties.setShortcode("600984");
        service = new MpesaSimulationService(darajaSdk, properties);
    }

    @Test
    void simulateTransaction_ValidInput_DelegatestoSdk() {
        DarajaApiResponse expected = new DarajaApiResponse();
        expected.setResponseCode("0");
        when(darajaSdk.simulateC2b(any())).thenReturn(expected);

        DarajaApiResponse result = service.simulateTransaction(
                "CustomerPayBillOnline", "100", "254700000000", "BILL001");

        assertEquals("0", result.getResponseCode());
        verify(darajaSdk).simulateC2b(any());
    }

    @Test
    void simulateTransaction_InvalidCommandId_ThrowsIllegalArgument() {
        assertThrows(IllegalArgumentException.class, () ->
                service.simulateTransaction("INVALID_CMD", "100", "254700000000", null));
        verifyNoInteractions(darajaSdk);
    }

    @Test
    void simulateTransaction_ZeroAmount_ThrowsIllegalArgument() {
        assertThrows(IllegalArgumentException.class, () ->
                service.simulateTransaction("CustomerPayBillOnline", "0", "254700000000", null));
    }

    @Test
    void simulateTransaction_NegativeAmount_ThrowsIllegalArgument() {
        assertThrows(IllegalArgumentException.class, () ->
                service.simulateTransaction("CustomerPayBillOnline", "-50", "254700000000", null));
    }

    @Test
    void simulateTransaction_NonNumericAmount_ThrowsIllegalArgument() {
        assertThrows(IllegalArgumentException.class, () ->
                service.simulateTransaction("CustomerPayBillOnline", "abc", "254700000000", null));
    }

    @Test
    void simulateTransaction_InvalidPhone_TooShort_ThrowsIllegalArgument() {
        assertThrows(IllegalArgumentException.class, () ->
                service.simulateTransaction("CustomerPayBillOnline", "100", "07001234567", null));
    }

    @Test
    void simulateTransaction_InvalidPhone_WrongPrefix_ThrowsIllegalArgument() {
        assertThrows(IllegalArgumentException.class, () ->
                service.simulateTransaction("CustomerPayBillOnline", "100", "255700000000", null));
    }

    @Test
    void simulateTransaction_CustomerBuyGoodsOnline_IsValid() {
        DarajaApiResponse expected = new DarajaApiResponse();
        expected.setResponseCode("0");
        when(darajaSdk.simulateC2b(any())).thenReturn(expected);

        assertDoesNotThrow(() ->
                service.simulateTransaction("CustomerBuyGoodsOnline", "500", "254712345678", null));
        verify(darajaSdk).simulateC2b(any());
    }

    @Test
    void simulateTransaction_NullBillRefNumber_IsAllowed() {
        DarajaApiResponse expected = new DarajaApiResponse();
        expected.setResponseCode("0");
        when(darajaSdk.simulateC2b(any())).thenReturn(expected);

        assertDoesNotThrow(() ->
                service.simulateTransaction("CustomerPayBillOnline", "100", "254700000000", null));
    }
}
