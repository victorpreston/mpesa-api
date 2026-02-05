package com.grainindustries.mpesa_c2b_api.controller;

import com.grainindustries.mpesa_c2b_api.dto.DarajaRegisterUrlResponse;
import com.grainindustries.mpesa_c2b_api.dto.DarajaSimulateResponse;
import com.grainindustries.mpesa_c2b_api.service.MpesaUrlRegistrationService;
import com.grainindustries.mpesa_c2b_api.service.MpesaSimulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/mpesa/admin")
@CrossOrigin(origins = "*")
public class MpesaAdminController {
    
    @Autowired
    private MpesaUrlRegistrationService urlRegistrationService;
    
    @Autowired
    private MpesaSimulationService simulationService;
    
    @PostMapping("/register-urls")
    public ResponseEntity<?> registerCallbackUrls() {
        try {
            DarajaRegisterUrlResponse response = urlRegistrationService.registerCallbackUrls();
            
            if ("0".equals(response.getResponseCode())) {
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
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
            DarajaSimulateResponse response = simulationService.simulateTransaction(
                commandID,
                amount,
                phoneNumber,
                billRefNumber
            );
            
            if ("0".equals(response.getResponseCode())) {
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new DarajaSimulateResponse(
                    null,
                    "1",
                    "Validation error: " + e.getMessage()
                ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new DarajaSimulateResponse(
                    null,
                    "1",
                    "Failed to simulate transaction: " + e.getMessage()
                ));
        }
    }
}
