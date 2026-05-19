package com.mpesa_daraja_api.mpesa_daraja_api.sdk;

import com.mpesa_daraja_api.mpesa_daraja_api.config.DarajaProperties;
import com.mpesa_daraja_api.mpesa_daraja_api.dto.response.DarajaApiResponse;
import com.mpesa_daraja_api.mpesa_daraja_api.exception.DarajaApiException;
import com.mpesa_daraja_api.mpesa_daraja_api.service.auth.MpesaAuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

class DefaultDarajaClientTest {

    private MockRestServiceServer server;
    private DefaultDarajaClient client;
    private MpesaAuthService authService;

    @BeforeEach
    void setUp() {
        RestClient.Builder builder = RestClient.builder();
        server = MockRestServiceServer.bindTo(builder).build();
        authService = mock(MpesaAuthService.class);
        when(authService.generateAccessToken()).thenReturn("test-token");

        DarajaProperties properties = new DarajaProperties();
        client = new DefaultDarajaClient(builder.build(), properties, authService);
    }

    @Test
    void postSendsAuthorizationHeader() {
        server.expect(requestTo("https://sandbox.safaricom.co.ke/mpesa/stkpush/v1/processrequest"))
                .andExpect(header("Authorization", "Bearer test-token"))
                .andRespond(withSuccess("{\"ResponseCode\":\"0\",\"ResponseDescription\":\"Success\"}", MediaType.APPLICATION_JSON));

        DarajaApiResponse response = client.post("/mpesa/stkpush/v1/processrequest", "{}", DarajaApiResponse.class);

        assertNotNull(response);
        assertEquals("0", response.getResponseCode());
        server.verify();
    }

    @Test
    void postWithAbsoluteUrlDoesNotPrependBaseUrl() {
        server.expect(requestTo("https://custom.example.com/callback"))
                .andRespond(withSuccess("{\"ResponseCode\":\"0\"}", MediaType.APPLICATION_JSON));

        DarajaApiResponse response = client.post("https://custom.example.com/callback", "{}", DarajaApiResponse.class);

        assertNotNull(response);
        server.verify();
    }

    @Test
    void postThrowsDarajaApiExceptionOnClientError() {
        server.expect(requestTo("https://sandbox.safaricom.co.ke/mpesa/stkpush/v1/processrequest"))
                .andRespond(withServerError());

        assertThrows(DarajaApiException.class, () ->
                client.post("/mpesa/stkpush/v1/processrequest", "{}", DarajaApiResponse.class));
    }

    @Test
    void postCallsGenerateAccessTokenForEachRequest() {
        server.expect(requestTo("https://sandbox.safaricom.co.ke/mpesa/test"))
                .andRespond(withSuccess("{}", MediaType.APPLICATION_JSON));
        server.expect(requestTo("https://sandbox.safaricom.co.ke/mpesa/test"))
                .andRespond(withSuccess("{}", MediaType.APPLICATION_JSON));

        client.post("/mpesa/test", "{}", DarajaApiResponse.class);
        client.post("/mpesa/test", "{}", DarajaApiResponse.class);

        verify(authService, times(2)).generateAccessToken();
        server.verify();
    }
}
