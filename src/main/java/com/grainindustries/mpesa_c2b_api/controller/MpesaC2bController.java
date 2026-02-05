package com.grainindustries.mpesa_c2b_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grainindustries.mpesa_c2b_api.dto.MpesaCallbackRequest;
import com.grainindustries.mpesa_c2b_api.dto.MpesaCallbackResponse;
import com.grainindustries.mpesa_c2b_api.entity.MpesaTransaction;
import com.grainindustries.mpesa_c2b_api.service.MpesaTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/mpesa/c2b")
@CrossOrigin(origins = "*")
public class MpesaC2bController {
    
    @Autowired
    private MpesaTransactionService mpesaTransactionService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @PostMapping("/callback")
    public ResponseEntity<MpesaCallbackResponse> handleCallback(@RequestBody String payload) {
        try {
            MpesaCallbackRequest request = objectMapper.readValue(payload, MpesaCallbackRequest.class);
            MpesaCallbackResponse response = mpesaTransactionService.processCallback(request, payload);
            
            if ("0".equals(response.getResultCode())) {
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new MpesaCallbackResponse("1", "Invalid Request", 
                    "Failed to parse request: " + e.getMessage()));
        }
    }
    
    @GetMapping("/transaction/{transactionId}")
    public ResponseEntity<?> getTransaction(@PathVariable String transactionId) {
        Optional<MpesaTransaction> transaction = mpesaTransactionService.getTransactionById(transactionId);
        
        if (transaction.isPresent()) {
            return ResponseEntity.ok(transaction.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new MpesaCallbackResponse("1", "Not Found", 
                    "Transaction not found"));
        }
    }
    
    @GetMapping("/msisdn/{msisdn}")
    public ResponseEntity<?> getTransactionsByMsisdn(@PathVariable String msisdn) {
        List<MpesaTransaction> transactions = mpesaTransactionService.getTransactionsByPhoneNumber(msisdn);
        
        if (!transactions.isEmpty()) {
            return ResponseEntity.ok(transactions);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new MpesaCallbackResponse("1", "Not Found", 
                    "No transactions found for this phone number"));
        }
    }
    
    @GetMapping("/shortcode/{shortcode}")
    public ResponseEntity<?> getTransactionsByShortcode(@PathVariable String shortcode) {
        List<MpesaTransaction> transactions = mpesaTransactionService.getTransactionsByBusinessShortcode(shortcode);
        
        if (!transactions.isEmpty()) {
            return ResponseEntity.ok(transactions);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new MpesaCallbackResponse("1", "Not Found", 
                    "No transactions found for this shortcode"));
        }
    }
    
    @GetMapping("/all")
    public ResponseEntity<?> getAllTransactions() {
        List<MpesaTransaction> transactions = mpesaTransactionService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }
    
    @GetMapping("/health")
    public ResponseEntity<?> health() {
        return ResponseEntity.ok(new MpesaCallbackResponse("0", "OK", "M-Pesa C2B API is running"));
    }
}
