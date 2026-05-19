package com.mpesa_daraja_api.mpesa_daraja_api.service;

import com.mpesa_daraja_api.mpesa_daraja_api.config.DarajaProperties;
import com.mpesa_daraja_api.mpesa_daraja_api.dto.DarajaTokenResponse;
import com.mpesa_daraja_api.mpesa_daraja_api.exception.DarajaApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.time.Instant;
import java.util.Base64;

@Service
public class MpesaAuthService {
    
    private static final Logger logger = LoggerFactory.getLogger(MpesaAuthService.class);

    private final RestClient restClient;
    private final DarajaProperties properties;
    private final Clock clock;

    private DarajaTokenResponse cachedToken;
    private Instant tokenExpiryTime;

    @Autowired
    public MpesaAuthService(RestClient restClient, DarajaProperties properties) {
        this(restClient, properties, Clock.systemUTC());
    }

    MpesaAuthService(RestClient restClient, DarajaProperties properties, Clock clock) {
        this.restClient = restClient;
        this.properties = properties;
        this.clock = clock;
    }

    public synchronized String generateAccessToken() {
        if (isCachedTokenValid()) {
            logger.debug("Using cached access token");
            return cachedToken.getAccessToken();
        }

        if (!StringUtils.hasText(properties.getConsumerKey()) || !StringUtils.hasText(properties.getConsumerSecret())) {
            throw new DarajaApiException("Daraja consumer key and secret must be configured", "DARAJA_CREDENTIALS_MISSING");
        }

        try {
            logger.info("Generating new access token from Daraja");
            String credentials = properties.getConsumerKey() + ":" + properties.getConsumerSecret();
            String encodedCredentials = Base64.getEncoder()
                    .encodeToString(credentials.getBytes(StandardCharsets.UTF_8));

            DarajaTokenResponse tokenResponse = restClient.get()
                    .uri(authUrl())
                    .headers(headers -> headers.setBasicAuth(encodedCredentials))
                    .retrieve()
                    .body(DarajaTokenResponse.class);

            if (tokenResponse == null || !StringUtils.hasText(tokenResponse.getAccessToken())) {
                throw new DarajaApiException("Daraja returned an empty access token response", "DARAJA_TOKEN_EMPTY");
            }

            this.cachedToken = tokenResponse;
            this.tokenExpiryTime = Instant.now(clock).plusSeconds(tokenResponse.getExpiresIn());

            logger.info("Access token generated successfully, expires in {} seconds", tokenResponse.getExpiresIn());
            return tokenResponse.getAccessToken();
        } catch (RestClientException e) {
            throw new DarajaApiException("Failed to generate access token", "DARAJA_TOKEN_REQUEST_FAILED", e);
        }
    }

    private boolean isCachedTokenValid() {
        if (cachedToken == null || tokenExpiryTime == null) {
            return false;
        }

        return Instant.now(clock).isBefore(tokenExpiryTime.minusSeconds(60));
    }

    private String authUrl() {
        return UriComponentsBuilder.fromUriString(properties.baseUrl())
                .path(properties.getEndpoints().getOauth())
                .build()
                .toUriString();
    }

    public synchronized void clearCache() {
        this.cachedToken = null;
        this.tokenExpiryTime = null;
    }
}
