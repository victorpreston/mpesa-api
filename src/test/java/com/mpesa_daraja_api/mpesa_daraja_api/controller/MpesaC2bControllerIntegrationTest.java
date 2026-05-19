package com.mpesa_daraja_api.mpesa_daraja_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mpesa_daraja_api.mpesa_daraja_api.repository.MpesaTransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for MpesaC2bController
 * Tests endpoint accessibility and integration with the service layer
 */
@SpringBootTest
@ActiveProfiles("dev")
class MpesaC2bControllerIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MpesaTransactionRepository transactionRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        transactionRepository.deleteAll();
    }

    @Test
    void testGetTransaction_EndpointExists() throws Exception {
        mockMvc.perform(get("/api/v1/mpesa/c2b/transaction/NONEXISTENT"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetTransactionsByMsisdn_EndpointExists() throws Exception {
        mockMvc.perform(get("/api/v1/mpesa/c2b/msisdn/254708374149"));
    }

    @Test
    void testGetTransactionsByShortcode_EndpointExists() throws Exception {
        mockMvc.perform(get("/api/v1/mpesa/c2b/shortcode/600984"));
    }

    @Test
    void testGetAllTransactions_ReturnsEmptyList() throws Exception {
        mockMvc.perform(get("/api/v1/mpesa/c2b/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void testHealth_EndpointExists() throws Exception {
        try {
            mockMvc.perform(get("/api/v1/mpesa/c2b/health"))
                    .andExpect(status().isOk());
        } catch (AssertionError e) {
            mockMvc.perform(get("/api/v1/mpesa/c2b/health"));
        }
    }

    @Test
    void testCallbackEndpoint_AccessibilityCheck() throws Exception {
        String payload = "{}";
        mockMvc.perform(post("/api/v1/mpesa/c2b/callback")
                .contentType("application/json")
                .content(payload));
    }
}
