package com.mpesa_daraja_api.mpesa_daraja_api.enums;

public enum Environment {

    SANDBOX("sandbox", "https://sandbox.safaricom.co.ke"),
    PRODUCTION("production", "https://api.safaricom.co.ke");

    private final String key;
    private final String baseUrl;

    Environment(String key, String baseUrl) {
        this.key = key;
        this.baseUrl = baseUrl;
    }

    public String getKey() {
        return key;
    }

    public String getBaseUrl() {
        return baseUrl;
    }
}
