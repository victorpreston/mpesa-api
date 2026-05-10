package com.grainindustries.mpesa_c2b_api.controller;

import com.grainindustries.mpesa_c2b_api.dto.DarajaRegisterUrlResponse;
import com.grainindustries.mpesa_c2b_api.dto.DarajaSimulateResponse;
import com.grainindustries.mpesa_c2b_api.dto.requests.C2bRegistrationCommand;
import com.grainindustries.mpesa_c2b_api.dto.requests.C2bSimulationCommand;
import com.grainindustries.mpesa_c2b_api.sdk.DarajaSdk;
import com.grainindustries.mpesa_c2b_api.service.MpesaUrlRegistrationService;
import com.grainindustries.mpesa_c2b_api.service.MpesaSimulationService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/mpesa/admin")
@CrossOrigin(origins = "*")
public class MpesaAdminController {
    
    private static final Logger logger = LoggerFactory.getLogger(MpesaAdminController.class);
    
    @Autowired
    private MpesaUrlRegistrationService urlRegistrationService;
    
    @Autowired
    private MpesaSimulationService simulationService;

    @Autowired
    private DarajaSdk darajaSdk;
    
    @PostMapping("/register-urls")
    public ResponseEntity<?> registerCallbackUrls() {
        try {
            logger.info("Registering callback URLs");
            DarajaRegisterUrlResponse response = urlRegistrationService.registerCallbackUrls();
            
            if ("0".equals(response.getResponseCode())) {
                logger.info("URLs registered successfully");
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                logger.warn("URL registration failed: {}", response.getResponseDescription());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            logger.error("Error registering callback URLs", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new DarajaRegisterUrlResponse(
                    null,
                    "1",
                    "Failed to register URLs: " + e.getMessage()
                ));
        }
    }
    
    @PostMapping("/simulate")
    public ResponseEntity<?> simulateTransaction(
            @RequestParam String commandID,
            @RequestParam String amount,
            @RequestParam String phoneNumber,
            @RequestParam(required = false) String billRefNumber) {
        try {
            logger.info("Simulating transaction - CommandID: {}, Phone: {}", commandID, phoneNumber);
            DarajaSimulateResponse response = simulationService.simulateTransaction(
                commandID,
                amount,
                phoneNumber,
                billRefNumber
            );
            
            if ("0".equals(response.getResponseCode())) {
                logger.info("Transaction simulation successful");
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                logger.warn("Transaction simulation failed: {}", response.getResponseDescription());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (IllegalArgumentException e) {
            logger.warn("Validation error in simulation request: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new DarajaSimulateResponse(
                    null,
                    "1",
                    "Validation error: " + e.getMessage()
                ));
        } catch (Exception e) {
            logger.error("Error simulating transaction", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new DarajaSimulateResponse(
                    null,
                    "1",
                    "Failed to simulate transaction: " + e.getMessage()
                ));
        }
    }

    @PostMapping("/register-urls/json")
    public ResponseEntity<?> registerCallbackUrlsJson(@RequestBody C2bRegistrationCommand request) {
        return ResponseEntity.ok(darajaSdk.registerC2bUrls(request));
    }

    @PostMapping("/simulate/json")
    public ResponseEntity<?> simulateTransactionJson(@Valid @RequestBody C2bSimulationCommand request) {
        return ResponseEntity.ok(darajaSdk.simulateC2b(request));
    }
}
