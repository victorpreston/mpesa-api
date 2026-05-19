package com.mpesa_daraja_api.mpesa_daraja_api.service;

import com.mpesa_daraja_api.mpesa_daraja_api.dto.response.MpesaCallbackRequest;
import com.mpesa_daraja_api.mpesa_daraja_api.service.c2b.MpesaTransactionService;
import com.mpesa_daraja_api.mpesa_daraja_api.dto.response.MpesaCallbackResponse;
import com.mpesa_daraja_api.mpesa_daraja_api.entity.MpesaTransaction;
import com.mpesa_daraja_api.mpesa_daraja_api.repository.MpesaTransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
class MpesaTransactionServiceTest {

    @Autowired
    private MpesaTransactionService transactionService;

    @Autowired
    private MpesaTransactionRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    void testProcessCallback_SuccessfullyProcessesValidRequest() {
        MpesaCallbackRequest request = createValidCallbackRequest("TXN001");
        String rawPayload = "{\"test\": \"payload\"}";

        MpesaCallbackResponse response = transactionService.processCallback(request, rawPayload);

        assertEquals("0", response.getResultCode());
        assertEquals("Success", response.getResultDescription());
        assertNotNull(response.getTransactionId());

        Optional<MpesaTransaction> saved = repository.findByTransactionId("TXN001");
        assertTrue(saved.isPresent());
        assertEquals("254708374149", saved.get().getMsisdn());
        assertEquals("100", saved.get().getTransAmount());
    }

    @Test
    void testProcessCallback_DetectsDuplicates() {
        MpesaCallbackRequest request = createValidCallbackRequest("TXN002");
        String rawPayload = "{\"test\": \"payload\"}";

        MpesaCallbackResponse response1 = transactionService.processCallback(request, rawPayload);
        assertEquals("0", response1.getResultCode());

        MpesaCallbackResponse response2 = transactionService.processCallback(request, rawPayload);

        assertEquals("0", response2.getResultCode());
        assertEquals("Transaction Already Exists", response2.getResultDescription());
        assertEquals(1, repository.findAll().size());
    }

    @Test
    void testProcessCallback_StoresAllFields() {
        MpesaCallbackRequest request = new MpesaCallbackRequest();
        request.setTransactionId("TXN003");
        request.setTransactionType("Pay Bill Online");
        request.setTransAmount("500");
        request.setTransTime("20230110120000");
        request.setBusinessShortcode("600984");
        request.setBillRefNumber("BILL001");
        request.setInvoiceNumber("INV001");
        request.setMsisdn("254722000000");
        request.setFirstName("Alice");
        request.setMiddleName("M");
        request.setLastName("Johnson");
        request.setOrgAccountBalance("49500");
        request.setThirdPartyTransId("EXT123");

        MpesaCallbackResponse response = transactionService.processCallback(request, "raw_payload");

        MpesaTransaction transaction = repository.findByTransactionId("TXN003").get();
        assertEquals("Pay Bill Online", transaction.getTransactionType());
        assertEquals("500", transaction.getTransAmount());
        assertEquals("600984", transaction.getBusinessShortcode());
        assertEquals("BILL001", transaction.getBillRefNumber());
        assertEquals("Alice", transaction.getFirstName());
        assertEquals("Johnson", transaction.getLastName());
    }

    @Test
    void testGetTransactionById_ReturnsExistingTransaction() {
        MpesaCallbackRequest request = createValidCallbackRequest("TXN004");
        transactionService.processCallback(request, "payload");

        Optional<MpesaTransaction> result = transactionService.getTransactionById("TXN004");

        assertTrue(result.isPresent());
        assertEquals("254708374149", result.get().getMsisdn());
    }

    @Test
    void testGetTransactionById_ReturnsEmptyForNonexistent() {
        Optional<MpesaTransaction> result = transactionService.getTransactionById("NONEXISTENT");
        assertFalse(result.isPresent());
    }

    @Test
    void testGetTransactionsByPhoneNumber_ReturnsFiltredResults() {
        transactionService.processCallback(createValidCallbackRequest("TXN005"), "payload");
        transactionService.processCallback(createCallbackRequestWithPhone("TXN006", "254700000000"), "payload");
        transactionService.processCallback(createValidCallbackRequest("TXN007"), "payload");

        var results = transactionService.getTransactionsByPhoneNumber("254708374149");

        assertEquals(2, results.size());
        assertTrue(results.stream().allMatch(t -> t.getMsisdn().equals("254708374149")));
    }

    @Test
    void testGetTransactionsByPhoneNumber_ReturnsEmptyForUnknownPhone() {
        var results = transactionService.getTransactionsByPhoneNumber("254799999999");
        assertTrue(results.isEmpty());
    }

    @Test
    void testGetTransactionsByBusinessShortcode_ReturnsFilteredResults() {
        transactionService.processCallback(createValidCallbackRequest("TXN008"), "payload");
        transactionService.processCallback(createCallbackRequestWithShortcode("TXN009", "600985"), "payload");

        var results = transactionService.getTransactionsByBusinessShortcode("600984");

        assertEquals(1, results.size());
        assertEquals("600984", results.get(0).getBusinessShortcode());
    }

    @Test
    void testGetAllTransactions_ReturnsAllTransactions() {
        transactionService.processCallback(createValidCallbackRequest("TXN010"), "payload");
        transactionService.processCallback(createValidCallbackRequest("TXN011"), "payload");
        transactionService.processCallback(createValidCallbackRequest("TXN012"), "payload");

        var results = transactionService.getAllTransactions();
        assertEquals(3, results.size());
    }

    @Test
    void testProcessCallback_StoresRawPayload() {
        MpesaCallbackRequest request = createValidCallbackRequest("TXN013");
        String rawPayload = "{\"TransID\": \"TXN013\", \"TransAmount\": \"100\"}";

        transactionService.processCallback(request, rawPayload);

        MpesaTransaction transaction = repository.findByTransactionId("TXN013").get();
        assertEquals(rawPayload, transaction.getRawPayload());
    }

    // Helper methods
    private MpesaCallbackRequest createValidCallbackRequest(String transactionId) {
        MpesaCallbackRequest request = new MpesaCallbackRequest();
        request.setTransactionId(transactionId);
        request.setTransactionType("Pay Bill Online");
        request.setTransAmount("100");
        request.setTransTime("20230110120000");
        request.setBusinessShortcode("600984");
        request.setBillRefNumber("BILL001");
        request.setMsisdn("254708374149");
        request.setFirstName("John");
        request.setLastName("Doe");
        return request;
    }

    private MpesaCallbackRequest createCallbackRequestWithPhone(String transactionId, String phone) {
        MpesaCallbackRequest request = createValidCallbackRequest(transactionId);
        request.setMsisdn(phone);
        return request;
    }

    private MpesaCallbackRequest createCallbackRequestWithShortcode(String transactionId, String shortcode) {
        MpesaCallbackRequest request = createValidCallbackRequest(transactionId);
        request.setBusinessShortcode(shortcode);
        return request;
    }
}
