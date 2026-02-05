package com.grainindustries.mpesa_c2b_api.dto;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MpesaCallbackRequest {
    
    private String transactionId;
    private String transAmount;
    private String transTime;
    private String phoneNumber;
    private String mpesaReceiptNumber;
    private String businessShortcode;
    private String resultCode;
    private String resultDescription;
    private String merchantRequestId;
    private String checkoutRequestId;
    private String organizationBalance;
    private String thirdPartyTransId;
    
    private Map<String, Object> additionalFields = new HashMap<>();
    
    @JsonAnySetter
    public void setAdditionalProperty(String key, Object value) {
        additionalFields.put(key, value);
    }
    
    public MpesaCallbackRequest() {}
    
    public String getTransactionId() {
        return transactionId;
    }
    
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
    
    public String getTransAmount() {
        return transAmount;
    }
    
    public void setTransAmount(String transAmount) {
        this.transAmount = transAmount;
    }
    
    public String getTransTime() {
        return transTime;
    }
    
    public void setTransTime(String transTime) {
        this.transTime = transTime;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public String getMpesaReceiptNumber() {
        return mpesaReceiptNumber;
    }
    
    public void setMpesaReceiptNumber(String mpesaReceiptNumber) {
        this.mpesaReceiptNumber = mpesaReceiptNumber;
    }
    
    public String getBusinessShortcode() {
        return businessShortcode;
    }
    
    public void setBusinessShortcode(String businessShortcode) {
        this.businessShortcode = businessShortcode;
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
    
    public String getMerchantRequestId() {
        return merchantRequestId;
    }
    
    public void setMerchantRequestId(String merchantRequestId) {
        this.merchantRequestId = merchantRequestId;
    }
    
    public String getCheckoutRequestId() {
        return checkoutRequestId;
    }
    
    public void setCheckoutRequestId(String checkoutRequestId) {
        this.checkoutRequestId = checkoutRequestId;
    }
    
    public String getOrganizationBalance() {
        return organizationBalance;
    }
    
    public void setOrganizationBalance(String organizationBalance) {
        this.organizationBalance = organizationBalance;
    }
    
    public String getThirdPartyTransId() {
        return thirdPartyTransId;
    }
    
    public void setThirdPartyTransId(String thirdPartyTransId) {
        this.thirdPartyTransId = thirdPartyTransId;
    }
    
    public Map<String, Object> getAdditionalFields() {
        return additionalFields;
    }
    
    public void setAdditionalFields(Map<String, Object> additionalFields) {
        this.additionalFields = additionalFields;
    }
}
