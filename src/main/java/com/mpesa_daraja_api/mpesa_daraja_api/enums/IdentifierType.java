package com.mpesa_daraja_api.mpesa_daraja_api.enums;

public enum IdentifierType {

    MSISDN("1"),
    TILL_NUMBER("2"),
    PAYBILL("4");

    private final String code;

    IdentifierType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return code;
    }
}
