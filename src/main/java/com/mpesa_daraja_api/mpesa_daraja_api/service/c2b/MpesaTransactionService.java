package com.mpesa_daraja_api.mpesa_daraja_api.service.c2b;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mpesa_daraja_api.mpesa_daraja_api.dto.response.MpesaCallbackRequest;
import com.mpesa_daraja_api.mpesa_daraja_api.dto.response.MpesaCallbackResponse;
import com.mpesa_daraja_api.mpesa_daraja_api.entity.MpesaTransaction;
import com.mpesa_daraja_api.mpesa_daraja_api.repository.MpesaTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MpesaTransactionService {

    private static final Logger logger = LoggerFactory.getLogger(MpesaTransactionService.class);

    private final MpesaTransactionRepository mpesaTransactionRepository;
    private final ObjectMapper objectMapper;

    public MpesaCallbackResponse processCallback(MpesaCallbackRequest request, String rawPayload) {
        try {
            logger.info("Processing C2B callback for transaction: {}", request.getTransactionId());

            if (!hasRequiredTransactionFields(request)) {
                logger.warn("Rejected incomplete C2B callback payload");
                return new MpesaCallbackResponse("1", "Invalid Request", "Required C2B transaction fields are missing");
            }

            if (request.getTransactionId() != null &&
                mpesaTransactionRepository.findByTransactionId(request.getTransactionId()).isPresent()) {
                logger.warn("Duplicate transaction detected: {}", request.getTransactionId());
                return new MpesaCallbackResponse("0", "Transaction Already Exists", "Duplicate transaction detected");
            }

            MpesaTransaction transaction = mapRequestToEntity(request, rawPayload);
            MpesaTransaction savedTransaction = mpesaTransactionRepository.save(transaction);

            logger.info("Transaction saved successfully with ID: {}", savedTransaction.getId());
            return new MpesaCallbackResponse("0", "Success", "Transaction captured successfully", savedTransaction.getId());
        } catch (Exception e) {
            logger.error("Error processing transaction callback", e);
            return new MpesaCallbackResponse("1", "Error", "Failed to process transaction: " + e.getMessage());
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
        return mpesaTransactionRepository.findByTransactionId(transactionId);
    }

    public List<MpesaTransaction> getTransactionsByPhoneNumber(String msisdn) {
        return mpesaTransactionRepository.findByMsisdn(msisdn);
    }

    public List<MpesaTransaction> getTransactionsByBusinessShortcode(String shortcode) {
        return mpesaTransactionRepository.findByBusinessShortcode(shortcode);
    }

    public List<MpesaTransaction> getAllTransactions() {
        return mpesaTransactionRepository.findAll();
    }
}
