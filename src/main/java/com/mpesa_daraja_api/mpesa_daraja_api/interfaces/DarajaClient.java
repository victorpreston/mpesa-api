package com.mpesa_daraja_api.mpesa_daraja_api.interfaces;

public interface DarajaClient {

    <T> T post(String endpointPath, Object payload, Class<T> responseType);
}
