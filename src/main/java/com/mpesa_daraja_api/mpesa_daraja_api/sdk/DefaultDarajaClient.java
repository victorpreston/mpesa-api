package com.mpesa_daraja_api.mpesa_daraja_api.sdk;

import com.mpesa_daraja_api.mpesa_daraja_api.config.DarajaProperties;
import com.mpesa_daraja_api.mpesa_daraja_api.exception.DarajaApiException;
import com.mpesa_daraja_api.mpesa_daraja_api.interfaces.DarajaClient;
import com.mpesa_daraja_api.mpesa_daraja_api.service.auth.MpesaAuthService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class DefaultDarajaClient implements DarajaClient {

    private final RestClient restClient;
    private final DarajaProperties properties;
    private final MpesaAuthService authService;

    public DefaultDarajaClient(RestClient restClient, DarajaProperties properties, MpesaAuthService authService) {
        this.restClient = restClient;
        this.properties = properties;
        this.authService = authService;
    }

    @Override
    public <T> T post(String endpointPath, Object payload, Class<T> responseType) {
        try {
            return restClient.post()
                    .uri(resolveUrl(endpointPath))
                    .contentType(MediaType.APPLICATION_JSON)
                    .headers(headers -> headers.setBearerAuth(authService.generateAccessToken()))
                    .body(payload)
                    .retrieve()
                    .body(responseType);
        } catch (RestClientException ex) {
            throw new DarajaApiException("Daraja request failed", "DARAJA_REQUEST_FAILED", ex);
        }
    }

    private String resolveUrl(String endpointPath) {
        if (endpointPath.startsWith("http://") || endpointPath.startsWith("https://")) {
            return endpointPath;
        }
        return UriComponentsBuilder.fromUriString(properties.baseUrl())
                .path(endpointPath)
                .build()
                .toUriString();
    }
}
