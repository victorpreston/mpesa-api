package com.grainindustries.mpesa_c2b_api.sdk;

public interface DarajaClient {

    <T> T post(String endpointPath, Object payload, Class<T> responseType);
}
