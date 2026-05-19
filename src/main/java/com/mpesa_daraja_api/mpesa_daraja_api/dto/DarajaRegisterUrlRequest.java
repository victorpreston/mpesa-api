package com.mpesa_daraja_api.mpesa_daraja_api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DarajaRegisterUrlRequest {
    
    @JsonProperty("ShortCode")
    private String shortCode;
    
    @JsonProperty("ResponseType")
    private String responseType;
    
    @JsonProperty("ConfirmationURL")
    private String confirmationURL;
    
    @JsonProperty("ValidationURL")
    private String validationURL;
    
    public DarajaRegisterUrlRequest() {}
    
    public DarajaRegisterUrlRequest(String shortCode, String responseType, 
                                     String confirmationURL, String validationURL) {
        this.shortCode = shortCode;
        this.responseType = responseType;
        this.confirmationURL = confirmationURL;
        this.validationURL = validationURL;
    }
    
    public String getShortCode() {
        return shortCode;
    }
    
    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }
    
    public String getResponseType() {
        return responseType;
    }
    
    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }
    
    public String getConfirmationURL() {
        return confirmationURL;
    }
    
    public void setConfirmationURL(String confirmationURL) {
        this.confirmationURL = confirmationURL;
    }
    
    public String getValidationURL() {
        return validationURL;
    }
    
    public void setValidationURL(String validationURL) {
        this.validationURL = validationURL;
    }
}
