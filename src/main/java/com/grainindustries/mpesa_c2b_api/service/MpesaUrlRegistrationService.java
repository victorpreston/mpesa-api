package com.grainindustries.mpesa_c2b_api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grainindustries.mpesa_c2b_api.dto.DarajaRegisterUrlRequest;
import com.grainindustries.mpesa_c2b_api.dto.DarajaRegisterUrlResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MpesaUrlRegistrationService {
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private MpesaAuthService mpesaAuthService;
    
    @Value("${mpesa.shortcode}")
    private String shortCode;
    
    @Value("${mpesa.response.type}")
    private String responseType;
    
    @Value("${mpesa.confirmation.url}")
    private String confirmationUrl;
    
    @Value("${mpesa.validation.url}")
    private String validationUrl;
    
    @Value("${mpesa.register.url}")
    private String registerUrl;
    
    public DarajaRegisterUrlResponse registerCallbackUrls() {
        try {
            String accessToken = mpesaAuthService.generateAccessToken();
            
            DarajaRegisterUrlRequest request = new DarajaRegisterUrlRequest(
                shortCode,
                responseType,
                confirmationUrl,
                validationUrl
            );
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + accessToken);
            
            String requestBody = objectMapper.writeValueAsString(request);
            HttpEntity<String> httpEntity = new HttpEntity<>(requestBody, headers);
            
            ResponseEntity<String> response = restTemplate.postForEntity(
                registerUrl,
                httpEntity,
                String.class
            );
            
            DarajaRegisterUrlResponse urlResponse = objectMapper.readValue(
                response.getBody(),
                DarajaRegisterUrlResponse.class
            );
            
            return urlResponse;
        } catch (Exception e) {
            throw new RuntimeException("Failed to register callback URLs: " + e.getMessage(), e);
        }
    }
}
