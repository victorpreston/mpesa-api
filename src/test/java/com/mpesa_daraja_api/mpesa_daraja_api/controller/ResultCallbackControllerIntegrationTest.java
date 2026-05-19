package com.mpesa_daraja_api.mpesa_daraja_api.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
class ResultCallbackControllerIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void stkResultEndpoint_ReturnsAccepted() throws Exception {
        String stkPayload = """
                {
                  "Body": {
                    "stkCallback": {
                      "MerchantRequestID": "MR001",
                      "CheckoutRequestID": "CR001",
                      "ResultCode": 0,
                      "ResultDesc": "The service request is processed successfully."
                    }
                  }
                }
                """;

        mockMvc.perform(post("/api/v1/mpesa/results/stk")
                        .contentType("application/json")
                        .content(stkPayload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ResultCode").value(0));
    }

    @Test
    void asyncResultEndpoint_ReturnsAccepted() throws Exception {
        String resultPayload = """
                {
                  "Result": {
                    "ResultType": 0,
                    "ResultCode": "0",
                    "ResultDesc": "The service request is processed successfully.",
                    "OriginatorConversationID": "OC001",
                    "ConversationID": "CV001",
                    "TransactionID": "TXN001"
                  }
                }
                """;

        mockMvc.perform(post("/api/v1/mpesa/results")
                        .contentType("application/json")
                        .content(resultPayload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ResultCode").value(0));
    }

    @Test
    void timeoutEndpoint_ReturnsAccepted() throws Exception {
        String timeoutPayload = """
                {
                  "Result": {
                    "ResultCode": "1032",
                    "ResultDesc": "Request cancelled by user",
                    "TransactionID": "TXN002"
                  }
                }
                """;

        mockMvc.perform(post("/api/v1/mpesa/results/timeout")
                        .contentType("application/json")
                        .content(timeoutPayload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ResultCode").value(0));
    }

    @Test
    void stkResultEndpoint_MalformedPayload_StillReturnsOk() throws Exception {
        mockMvc.perform(post("/api/v1/mpesa/results/stk")
                        .contentType("application/json")
                        .content("{}"))
                .andExpect(status().isOk());
    }
}
