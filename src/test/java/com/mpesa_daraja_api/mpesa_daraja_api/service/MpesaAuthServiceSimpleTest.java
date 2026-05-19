package com.mpesa_daraja_api.mpesa_daraja_api.service;

import com.mpesa_daraja_api.mpesa_daraja_api.config.DarajaProperties;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

class MpesaAuthServiceSimpleTest {

    @Test
    void generateAccessTokenReturnsToken() {
        RestClient.Builder builder = RestClient.builder();
        MockRestServiceServer server = MockRestServiceServer.bindTo(builder).build();
        MpesaAuthService authService = new MpesaAuthService(builder.build(), properties());
        String expectedAuthorization = "Basic " + Base64.getEncoder()
                .encodeToString(("test-key" + ":" + "test-secret").getBytes(StandardCharsets.UTF_8));

        server.expect(requestTo("https://sandbox.safaricom.co.ke/oauth/v1/generate?grant_type=client_credentials"))
                .andExpect(method(org.springframework.http.HttpMethod.GET))
                .andExpect(header("Authorization", expectedAuthorization))
                .andRespond(withSuccess("{\"access_token\":\"sandbox-token\",\"expires_in\":3600}", MediaType.APPLICATION_JSON));

        String token = authService.generateAccessToken();

        assertNotNull(token);
        assertEquals("sandbox-token", token);
        server.verify();
    }

    @Test
    void generateAccessTokenCachesToken() {
        RestClient.Builder builder = RestClient.builder();
        MockRestServiceServer server = MockRestServiceServer.bindTo(builder).build();
        MpesaAuthService authService = new MpesaAuthService(builder.build(), properties());

        server.expect(requestTo("https://sandbox.safaricom.co.ke/oauth/v1/generate?grant_type=client_credentials"))
                .andRespond(withSuccess("{\"access_token\":\"cached-token\",\"expires_in\":3600}", MediaType.APPLICATION_JSON));

        assertEquals("cached-token", authService.generateAccessToken());
        assertEquals("cached-token", authService.generateAccessToken());
        server.verify();
    }

    private DarajaProperties properties() {
        DarajaProperties properties = new DarajaProperties();
        properties.setConsumerKey("test-key");
        properties.setConsumerSecret("test-secret");
        return properties;
    }
}
