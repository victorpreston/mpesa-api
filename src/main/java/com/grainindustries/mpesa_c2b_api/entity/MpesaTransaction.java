package com.grainindustries.mpesa_c2b_api.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "mpesa_transactions")
public class MpesaTransaction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "uuid")
    private UUID id;
    
    @Column(name = "transaction_id", unique = true, nullable = false)
    private String transactionId;
    
    @Column(name = "transaction_type")
    private String transactionType;
    
    @Column(name = "trans_amount", nullable = false)
    private String transAmount;
    
    @Column(name = "trans_time", nullable = false)
    private String transTime;
    
    @Column(name = "business_shortcode", nullable = false)
    private String businessShortcode;
    
    @Column(name = "bill_ref_number")
    private String billRefNumber;
    
    @Column(name = "invoice_number")
    private String invoiceNumber;
    
    @Column(name = "msisdn", nullable = false)
    private String msisdn;
    
    @Column(name = "first_name")
    private String firstName;
    
    @Column(name = "middle_name")
    private String middleName;
    
    @Column(name = "last_name")
    private String lastName;
    
    @Column(name = "org_account_balance")
    private String orgAccountBalance;
    
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
    
    public UUID getId() {
        return id;
    }
    
    public void setId(UUID id) {
        this.id = id;
    }
    
    public String getTransactionId() {
        return transactionId;
    }
    
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
    
    public String getTransactionType() {
        return transactionType;
    }
    
    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
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
    
    public String getBusinessShortcode() {
        return businessShortcode;
    }
    
    public void setBusinessShortcode(String businessShortcode) {
        this.businessShortcode = businessShortcode;
    }
    
    public String getBillRefNumber() {
        return billRefNumber;
    }
    
    public void setBillRefNumber(String billRefNumber) {
        this.billRefNumber = billRefNumber;
    }
    
    public String getInvoiceNumber() {
        return invoiceNumber;
    }
    
    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }
    
    public String getMsisdn() {
        return msisdn;
    }
    
    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getMiddleName() {
        return middleName;
    }
    
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getOrgAccountBalance() {
        return orgAccountBalance;
    }
    
    public void setOrgAccountBalance(String orgAccountBalance) {
        this.orgAccountBalance = orgAccountBalance;
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
