package com.grainindustries.mpesa_c2b_api.dto;

import java.util.UUID;

public class MpesaCallbackResponse {
    
    private String resultCode;
    private String resultDescription;
    private String message;
    private UUID transactionId;
    private Long timestamp;
    
    public MpesaCallbackResponse() {}
    
    public MpesaCallbackResponse(String resultCode, String resultDescription, String message) {
        this.resultCode = resultCode;
        this.resultDescription = resultDescription;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }
    
    public MpesaCallbackResponse(String resultCode, String resultDescription, String message, UUID transactionId) {
        this(resultCode, resultDescription, message);
        this.transactionId = transactionId;
    }
    
    public String getResultCode() {
        return resultCode;
    }
    
    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }
    
    public String getResultDescription() {
        return resultDescription;
    }
    
    public void setResultDescription(String resultDescription) {
        this.resultDescription = resultDescription;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public UUID getTransactionId() {
        return transactionId;
    }
    
    public void setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
    }
    
    public Long getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
