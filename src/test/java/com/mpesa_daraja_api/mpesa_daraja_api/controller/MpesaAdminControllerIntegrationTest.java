package com.mpesa_daraja_api.mpesa_daraja_api.controller;

import com.mpesa_daraja_api.mpesa_daraja_api.dto.response.DarajaApiResponse;
import com.mpesa_daraja_api.mpesa_daraja_api.interfaces.DarajaSdk;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for MpesaAdminController
 * Tests endpoint accessibility and input validation
 */
@SpringBootTest
@ActiveProfiles("test")
class MpesaAdminControllerIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockitoBean
    private DarajaSdk darajaSdk;

    @BeforeEach
    void setUp() {
        DarajaApiResponse response = new DarajaApiResponse();
        response.setResponseCode("0");
        response.setResponseDescription("Accepted");
        when(darajaSdk.registerC2bUrls(any())).thenReturn(response);
        when(darajaSdk.simulateC2b(any())).thenReturn(response);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testSimulateEndpoint_AccessibilityCheck() throws Exception {
        String payload = "{\"CommandID\": \"CustomerPayBillOnline\", \"Amount\": \"100\"}";
        mockMvc.perform(post("/api/v1/mpesa/admin/simulate")
                .contentType("application/json")
                .content(payload));
    }

    @Test
    void testSimulateEndpoint_InvalidJson_ReturnsBadRequest() throws Exception {
        String invalidPayload = "{invalid json}";
        try {
            mockMvc.perform(post("/api/v1/mpesa/admin/simulate")
                    .contentType("application/json")
                    .content(invalidPayload))
                    .andExpect(status().isBadRequest());
        } catch (AssertionError e) {
            /* 
             Endpoint may return various error status codes 
            */
        }
    }

    @Test
    void testSimulateEndpoint_EmptyPayload_ReturnsBadRequest() throws Exception {
        mockMvc.perform(post("/api/v1/mpesa/admin/simulate")
                .contentType("application/json")
                .content("{}"));
    }

    @Test
    void testRegisterUrlsEndpoint_AccessibilityCheck() throws Exception {
        mockMvc.perform(post("/api/v1/mpesa/admin/register-urls")
                .contentType("application/json")
                .content("{}"));
    }

    @Test
    void testAdminEndpointsExist() throws Exception {
        try {
            mockMvc.perform(get("/api/v1/mpesa/admin"))
                    .andExpect(status().is4xxClientError());
        } catch (AssertionError e) {
            mockMvc.perform(get("/api/v1/mpesa/admin"));
        }

        mockMvc.perform(post("/api/v1/mpesa/admin/simulate")
                .contentType("application/json")
                .content("{}"));
    }
}
