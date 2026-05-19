package com.mpesa_daraja_api.mpesa_daraja_api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mpesa_daraja_api.mpesa_daraja_api.dto.DarajaRegisterUrlRequest;
import com.mpesa_daraja_api.mpesa_daraja_api.dto.DarajaRegisterUrlResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    
    private static final Logger logger = LoggerFactory.getLogger(MpesaUrlRegistrationService.class);
    
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
            logger.info("Registering callback URLs for shortcode: {}", shortCode);
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
            
            logger.debug("Sending URL registration request to Daraja");
            ResponseEntity<String> response = restTemplate.postForEntity(
                registerUrl,
                httpEntity,
                String.class
            );
            
            DarajaRegisterUrlResponse urlResponse = objectMapper.readValue(
                response.getBody(),
                DarajaRegisterUrlResponse.class
            );
            
            logger.info("URL registration response: Code={}, Message={}", 
                urlResponse.getResponseCode(), urlResponse.getResponseDescription());
            
            return urlResponse;
        } catch (Exception e) {
            logger.error("Failed to register callback URLs", e);
            throw new RuntimeException("Failed to register callback URLs: " + e.getMessage(), e);
        }
    }
}
