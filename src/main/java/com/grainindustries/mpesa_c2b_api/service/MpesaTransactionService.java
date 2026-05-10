package com.grainindustries.mpesa_c2b_api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grainindustries.mpesa_c2b_api.dto.MpesaCallbackRequest;
import com.grainindustries.mpesa_c2b_api.dto.MpesaCallbackResponse;
import com.grainindustries.mpesa_c2b_api.entity.MpesaTransaction;
import com.grainindustries.mpesa_c2b_api.repository.MpesaTransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MpesaTransactionService {
    
    private static final Logger logger = LoggerFactory.getLogger(MpesaTransactionService.class);
    
    @Autowired
    private MpesaTransactionRepository mpesaTransactionRepository;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    public MpesaCallbackResponse processCallback(MpesaCallbackRequest request, String rawPayload) {
        try {
            logger.info("Processing C2B callback for transaction: {}", 
                request.getTransactionId());

            if (!hasRequiredTransactionFields(request)) {
                logger.warn("Rejected incomplete C2B callback payload");
                return new MpesaCallbackResponse(
                        "1",
                        "Invalid Request",
                        "Required C2B transaction fields are missing"
                );
            }
            
            if (request.getTransactionId() != null && 
                mpesaTransactionRepository.findByTransactionId(request.getTransactionId()).isPresent()) {
                logger.warn("Duplicate transaction detected: {}", request.getTransactionId());
                return new MpesaCallbackResponse("0", "Transaction Already Exists", 
                    "Duplicate transaction detected");
            }
            
            MpesaTransaction transaction = mapRequestToEntity(request, rawPayload);
            MpesaTransaction savedTransaction = mpesaTransactionRepository.save(transaction);
            
            logger.info("Transaction saved successfully with ID: {}", savedTransaction.getId());
            
            return new MpesaCallbackResponse(
                "0",
                "Success",
                "Transaction captured successfully",
                savedTransaction.getId()
            );
        } catch (Exception e) {
            logger.error("Error processing transaction callback", e);
            return new MpesaCallbackResponse(
                "1",
                "Error",
                "Failed to process transaction: " + e.getMessage()
            );
        }
    }

    private boolean hasRequiredTransactionFields(MpesaCallbackRequest request) {
        return StringUtils.hasText(request.getTransactionId())
                && StringUtils.hasText(request.getTransAmount())
                && StringUtils.hasText(request.getTransTime())
                && StringUtils.hasText(request.getBusinessShortcode())
                && StringUtils.hasText(request.getMsisdn());
    }
    
    private MpesaTransaction mapRequestToEntity(MpesaCallbackRequest request, String rawPayload) {
        MpesaTransaction transaction = new MpesaTransaction();
        
        transaction.setTransactionId(request.getTransactionId());
        transaction.setTransactionType(request.getTransactionType());
        transaction.setTransAmount(request.getTransAmount());
        transaction.setTransTime(request.getTransTime());
        transaction.setBusinessShortcode(request.getBusinessShortcode());
        transaction.setBillRefNumber(request.getBillRefNumber());
        transaction.setInvoiceNumber(request.getInvoiceNumber());
        transaction.setMsisdn(request.getMsisdn());
        transaction.setFirstName(request.getFirstName());
        transaction.setMiddleName(request.getMiddleName());
        transaction.setLastName(request.getLastName());
        transaction.setOrgAccountBalance(request.getOrgAccountBalance());
        transaction.setThirdPartyTransId(request.getThirdPartyTransId());
        transaction.setRawPayload(rawPayload);
        
        return transaction;
    }
    
    public Optional<MpesaTransaction> getTransactionById(String transactionId) {
        logger.debug("Fetching transaction by ID: {}", transactionId);
        return mpesaTransactionRepository.findByTransactionId(transactionId);
    }
    
    public List<MpesaTransaction> getTransactionsByPhoneNumber(String msisdn) {
        logger.debug("Fetching transactions for phone number: {}", msisdn);
        return mpesaTransactionRepository.findByMsisdn(msisdn);
    }
    
    public List<MpesaTransaction> getTransactionsByBusinessShortcode(String shortcode) {
        logger.debug("Fetching transactions for shortcode: {}", shortcode);
        return mpesaTransactionRepository.findByBusinessShortcode(shortcode);
    }
    
    public List<MpesaTransaction> getAllTransactions() {
        logger.debug("Fetching all transactions");
        return mpesaTransactionRepository.findAll();
    }
}
