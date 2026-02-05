package com.grainindustries.mpesa_c2b_api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grainindustries.mpesa_c2b_api.dto.MpesaCallbackRequest;
import com.grainindustries.mpesa_c2b_api.dto.MpesaCallbackResponse;
import com.grainindustries.mpesa_c2b_api.entity.MpesaTransaction;
import com.grainindustries.mpesa_c2b_api.repository.MpesaTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MpesaTransactionService {
    
    @Autowired
    private MpesaTransactionRepository mpesaTransactionRepository;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    public MpesaCallbackResponse processCallback(MpesaCallbackRequest request, String rawPayload) {
        try {
            if (request.getTransactionId() != null && 
                mpesaTransactionRepository.findByTransactionId(request.getTransactionId()).isPresent()) {
                return new MpesaCallbackResponse("0", "Transaction Already Exists", 
                    "Duplicate transaction detected");
            }
            
            MpesaTransaction transaction = mapRequestToEntity(request, rawPayload);
            MpesaTransaction savedTransaction = mpesaTransactionRepository.save(transaction);
            
            return new MpesaCallbackResponse(
                "0",
                "Success",
                "Transaction captured successfully",
                savedTransaction.getId()
            );
        } catch (Exception e) {
            return new MpesaCallbackResponse(
                "1",
                "Error",
                "Failed to process transaction: " + e.getMessage()
            );
        }
    }
    
    private MpesaTransaction mapRequestToEntity(MpesaCallbackRequest request, String rawPayload) {
        MpesaTransaction transaction = new MpesaTransaction();
        
        transaction.setTransactionId(request.getTransactionId());
        transaction.setTransAmount(request.getTransAmount());
        transaction.setTransTime(request.getTransTime());
        transaction.setPhoneNumber(request.getPhoneNumber());
        transaction.setMpesaReceiptNumber(request.getMpesaReceiptNumber());
        transaction.setBusinessShortcode(request.getBusinessShortcode());
        transaction.setResultCode(request.getResultCode());
        transaction.setResultDescription(request.getResultDescription());
        transaction.setMerchantRequestId(request.getMerchantRequestId());
        transaction.setCheckoutRequestId(request.getCheckoutRequestId());
        transaction.setOrganizationBalance(request.getOrganizationBalance());
        transaction.setThirdPartyTransId(request.getThirdPartyTransId());
        transaction.setRawPayload(rawPayload);
        
        return transaction;
    }
    
    public Optional<MpesaTransaction> getTransactionById(Long id) {
        return mpesaTransactionRepository.findById(id);
    }
    
    public Optional<MpesaTransaction> getTransactionByTransactionId(String transactionId) {
        return mpesaTransactionRepository.findByTransactionId(transactionId);
    }
    
    public List<MpesaTransaction> getTransactionsByPhoneNumber(String phoneNumber) {
        return mpesaTransactionRepository.findByPhoneNumber(phoneNumber);
    }
    
    public List<MpesaTransaction> getTransactionsByBusinessShortcode(String shortcode) {
        return mpesaTransactionRepository.findByBusinessShortcode(shortcode);
    }
    
    public List<MpesaTransaction> getTransactionsByResultCode(String resultCode) {
        return mpesaTransactionRepository.findByResultCode(resultCode);
    }
    
    public List<MpesaTransaction> getAllTransactions() {
        return mpesaTransactionRepository.findAll();
    }
}
