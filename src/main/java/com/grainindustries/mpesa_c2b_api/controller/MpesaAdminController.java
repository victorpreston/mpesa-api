package com.grainindustries.mpesa_c2b_api.controller;

import com.grainindustries.mpesa_c2b_api.dto.DarajaRegisterUrlResponse;
import com.grainindustries.mpesa_c2b_api.service.MpesaUrlRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/mpesa/admin")
@CrossOrigin(origins = "*")
public class MpesaAdminController {
    
    @Autowired
    private MpesaUrlRegistrationService urlRegistrationService;
    
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
}
