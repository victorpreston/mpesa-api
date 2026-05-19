package com.mpesa_daraja_api.mpesa_daraja_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mpesa_daraja_api.mpesa_daraja_api.dto.MpesaCallbackRequest;
import com.mpesa_daraja_api.mpesa_daraja_api.dto.MpesaCallbackResponse;
import com.mpesa_daraja_api.mpesa_daraja_api.entity.MpesaTransaction;
import com.mpesa_daraja_api.mpesa_daraja_api.service.MpesaTransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
public class MpesaC2bController {
    
    private static final Logger logger = LoggerFactory.getLogger(MpesaC2bController.class);
    
    @Autowired
    private MpesaTransactionService mpesaTransactionService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @PostMapping({
            "/api/v1/mpesa/c2b/callback",
            "/api/v1/mpesa/c2b/confirmation",
            "/api/v1/callback"
    })
    public ResponseEntity<MpesaCallbackResponse> handleCallback(@RequestBody String payload) {
        try {
            logger.info("Received C2B callback");
            logger.debug("Callback payload: {}", payload);
            
            MpesaCallbackRequest request = objectMapper.readValue(payload, MpesaCallbackRequest.class);
            MpesaCallbackResponse response = mpesaTransactionService.processCallback(request, payload);
            
            if ("0".equals(response.getResultCode())) {
                logger.info("Callback processed successfully");
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                logger.warn("Callback processing returned error: {}", response.getResultDescription());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        } catch (Exception e) {
            logger.error("Error processing callback", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new MpesaCallbackResponse("1", "Invalid Request", 
                    "Failed to parse request: " + e.getMessage()));
        }
    }

    @PostMapping("/api/v1/mpesa/c2b/validation")
    public ResponseEntity<Map<String, Object>> validatePayment(@RequestBody(required = false) String payload) {
        logger.info("Accepted C2B validation request");
        logger.debug("Validation payload: {}", payload);
        return ResponseEntity.ok(Map.of(
                "ResultCode", 0,
                "ResultDesc", "Accepted"
        ));
    }
    
    @GetMapping("/api/v1/mpesa/c2b/transaction/{transactionId}")
    public ResponseEntity<?> getTransaction(@PathVariable String transactionId) {
        logger.debug("Fetching transaction: {}", transactionId);
        Optional<MpesaTransaction> transaction = mpesaTransactionService.getTransactionById(transactionId);
        
        if (transaction.isPresent()) {
            logger.debug("Transaction found: {}", transactionId);
            return ResponseEntity.ok(transaction.get());
        } else {
            logger.warn("Transaction not found: {}", transactionId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new MpesaCallbackResponse("1", "Not Found", 
                    "Transaction not found"));
        }
    }
    
    @GetMapping("/api/v1/mpesa/c2b/msisdn/{msisdn}")
    public ResponseEntity<?> getTransactionsByMsisdn(@PathVariable String msisdn) {
        logger.debug("Fetching transactions for MSISDN: {}", msisdn);
        List<MpesaTransaction> transactions = mpesaTransactionService.getTransactionsByPhoneNumber(msisdn);
        
        if (!transactions.isEmpty()) {
            return ResponseEntity.ok(transactions);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new MpesaCallbackResponse("1", "Not Found", 
                    "No transactions found for this phone number"));
        }
    }
    
    @GetMapping("/api/v1/mpesa/c2b/shortcode/{shortcode}")
    public ResponseEntity<?> getTransactionsByShortcode(@PathVariable String shortcode) {
        logger.debug("Fetching transactions for shortcode: {}", shortcode);
        List<MpesaTransaction> transactions = mpesaTransactionService.getTransactionsByBusinessShortcode(shortcode);
        
        if (!transactions.isEmpty()) {
            logger.debug("Found {} transactions for shortcode: {}", transactions.size(), shortcode);
            return ResponseEntity.ok(transactions);
        } else {
            logger.warn("No transactions found for shortcode: {}", shortcode);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new MpesaCallbackResponse("1", "Not Found", 
                    "No transactions found for this shortcode"));
        }
    }
    
    @GetMapping("/api/v1/mpesa/c2b/all")
    public ResponseEntity<?> getAllTransactions() {
        logger.debug("Fetching all transactions");
        List<MpesaTransaction> transactions = mpesaTransactionService.getAllTransactions();
        logger.debug("Retrieved {} transactions", transactions.size());
        return ResponseEntity.ok(transactions);
    }
    
    @GetMapping("/api/v1/mpesa/c2b/health")
    public ResponseEntity<?> health() {
        logger.debug("Health check requested");
        return ResponseEntity.ok(new MpesaCallbackResponse("0", "OK", "M-Pesa C2B API is running"));
    }
}
