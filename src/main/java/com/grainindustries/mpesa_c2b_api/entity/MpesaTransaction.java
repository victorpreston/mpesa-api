package com.grainindustries.mpesa_c2b_api.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "mpesa_transactions")
public class MpesaTransaction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "transaction_id", unique = true, nullable = false)
    private String transactionId;
    
    @Column(name = "trans_amount", nullable = false)
    private String transAmount;
    
    @Column(name = "trans_time", nullable = false)
    private String transTime;
    
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;
    
    @Column(name = "mpesa_receipt_number")
    private String mpesaReceiptNumber;
    
    @Column(name = "business_shortcode")
    private String businessShortcode;
    
    @Column(name = "result_code")
    private String resultCode;
    
    @Column(name = "result_description")
    private String resultDescription;
    
    @Column(name = "merchant_request_id")
    private String merchantRequestId;
    
    @Column(name = "checkout_request_id")
    private String checkoutRequestId;
    
    @Column(name = "organization_balance")
    private String organizationBalance;
    
    @Column(name = "third_party_trans_id")
    private String thirdPartyTransId;
    
    @Column(columnDefinition = "TEXT")
    private String rawPayload;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    public MpesaTransaction() {}
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
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
    
    public String getRawPayload() {
        return rawPayload;
    }
    
    public void setRawPayload(String rawPayload) {
        this.rawPayload = rawPayload;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
