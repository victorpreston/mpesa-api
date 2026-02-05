package com.grainindustries.mpesa_c2b_api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grainindustries.mpesa_c2b_api.dto.DarajaSimulateRequest;
import com.grainindustries.mpesa_c2b_api.dto.DarajaSimulateResponse;
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
public class MpesaSimulationService {
    
    private static final Logger logger = LoggerFactory.getLogger(MpesaSimulationService.class);
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private MpesaAuthService mpesaAuthService;
    
    @Value("${mpesa.shortcode}")
    private String defaultShortCode;
    
    @Value("${mpesa.simulate.url}")
    private String simulateUrl;
    
    public DarajaSimulateResponse simulateTransaction(String commandID, String amount, 
                                                      String phoneNumber, String billRefNumber) {
        return simulateTransaction(defaultShortCode, commandID, amount, phoneNumber, billRefNumber);
    }
    
    public DarajaSimulateResponse simulateTransaction(String shortCode, String commandID, 
                                                      String amount, String phoneNumber, 
                                                      String billRefNumber) {
        try {
            logger.info("Simulating transaction - CommandID: {}, Amount: {}, Phone: {}", 
                commandID, amount, phoneNumber);
            
            validateInput(commandID, amount, phoneNumber);
            
            String accessToken = mpesaAuthService.generateAccessToken();
            
            DarajaSimulateRequest request = new DarajaSimulateRequest(
                shortCode,
                commandID,
                amount,
                phoneNumber,
                billRefNumber != null ? billRefNumber : ""
            );
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + accessToken);
            
            String requestBody = objectMapper.writeValueAsString(request);
            HttpEntity<String> httpEntity = new HttpEntity<>(requestBody, headers);
            
            logger.debug("Sending simulation request to Daraja");
            ResponseEntity<String> response = restTemplate.postForEntity(
                simulateUrl,
                httpEntity,
                String.class
            );
            
            DarajaSimulateResponse simulateResponse = objectMapper.readValue(
                response.getBody(),
                DarajaSimulateResponse.class
            );
            
            logger.info("Simulation response: Code={}, Message={}, ConversationID={}", 
                simulateResponse.getResponseCode(), 
                simulateResponse.getResponseDescription(),
                simulateResponse.getOriginatorConversationID());
            
            return simulateResponse;
        } catch (Exception e) {
            logger.error("Failed to simulate transaction", e);
            throw new RuntimeException("Failed to simulate transaction: " + e.getMessage(), e);
        }
    }
    
    private void validateInput(String commandID, String amount, String phoneNumber) {
        if (!isValidCommandID(commandID)) {
            throw new IllegalArgumentException(
                "Invalid CommandID. Must be CustomerPayBillOnline or CustomerBuyGoodsOnline"
            );
        }
        
        if (!isValidAmount(amount)) {
            throw new IllegalArgumentException("Amount must be a valid number greater than 0");
        }
        
        if (!isValidPhoneNumber(phoneNumber)) {
            throw new IllegalArgumentException(
                "Invalid phone number. Must start with 254 and be 12 digits long"
            );
        }
    }
    
    private boolean isValidCommandID(String commandID) {
        return commandID != null && (
            commandID.equals("CustomerPayBillOnline") || 
            commandID.equals("CustomerBuyGoodsOnline")
        );
    }
    
    private boolean isValidAmount(String amount) {
        try {
            double value = Double.parseDouble(amount);
            return value > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && 
               phoneNumber.matches("^254\\d{9}$");
    }
}
