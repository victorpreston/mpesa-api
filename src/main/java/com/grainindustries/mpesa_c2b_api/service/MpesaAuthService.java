package com.grainindustries.mpesa_c2b_api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grainindustries.mpesa_c2b_api.dto.DarajaTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

@Service
public class MpesaAuthService {
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Value("${mpesa.consumer.key}")
    private String consumerKey;
    
    @Value("${mpesa.consumer.secret}")
    private String consumerSecret;
    
    @Value("${mpesa.auth.url}")
    private String authUrl;
    
    private DarajaTokenResponse cachedToken;
    private Long tokenExpiryTime;
    
    public String generateAccessToken() {
        if (isCachedTokenValid()) {
            return cachedToken.getAccessToken();
        }
        
        try {
            String credentials = consumerKey + ":" + consumerSecret;
            String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());
            
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Basic " + encodedCredentials);
            
            HttpEntity<String> request = new HttpEntity<>(headers);
            
            ResponseEntity<String> response = restTemplate.exchange(
                authUrl,
                HttpMethod.GET,
                request,
                String.class
            );
            
            DarajaTokenResponse tokenResponse = objectMapper.readValue(
                response.getBody(),
                DarajaTokenResponse.class
            );
            
            this.cachedToken = tokenResponse;
            this.tokenExpiryTime = System.currentTimeMillis() + (tokenResponse.getExpiresIn() * 1000);
            
            return tokenResponse.getAccessToken();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate access token: " + e.getMessage(), e);
        }
    }
    
    private boolean isCachedTokenValid() {
        if (cachedToken == null || tokenExpiryTime == null) {
            return false;
        }
        
        long currentTime = System.currentTimeMillis();
        long bufferTime = 60 * 1000;
        
        return currentTime < (tokenExpiryTime - bufferTime);
    }
    
    public void clearCache() {
        this.cachedToken = null;
        this.tokenExpiryTime = null;
    }
}
