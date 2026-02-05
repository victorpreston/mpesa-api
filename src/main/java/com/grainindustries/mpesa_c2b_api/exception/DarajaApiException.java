package com.grainindustries.mpesa_c2b_api.exception;

public class DarajaApiException extends RuntimeException {
    
    private String errorCode;
    
    public DarajaApiException(String message) {
        super(message);
        this.errorCode = "UNKNOWN";
    }
    
    public DarajaApiException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
    
    public DarajaApiException(String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
    
    public String getErrorCode() {
        return errorCode;
    }
}
