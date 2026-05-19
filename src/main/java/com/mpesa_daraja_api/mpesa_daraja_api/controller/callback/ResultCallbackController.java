package com.mpesa_daraja_api.mpesa_daraja_api.controller.callback;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mpesa_daraja_api.mpesa_daraja_api.dto.response.AsyncCallbackResult;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/mpesa/results")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ResultCallbackController {

    private static final Logger logger = LoggerFactory.getLogger(ResultCallbackController.class);

    private final ObjectMapper objectMapper;

    @PostMapping("/stk")
    public ResponseEntity<Map<String, Object>> handleStkResult(@RequestBody String payload) {
        try {
            AsyncCallbackResult result = objectMapper.readValue(payload, AsyncCallbackResult.class);
            AsyncCallbackResult.StkCallback cb = result.getBody() != null ? result.getBody().getStkCallback() : null;
            if (cb != null) {
                logger.info("STK result received — checkout: {}, code: {}, desc: {}",
                        cb.getCheckoutRequestId(), cb.getResultCode(), cb.getResultDesc());
            } else {
                logger.warn("STK result received with unexpected structure");
                logger.debug("STK result payload: {}", payload);
            }
        } catch (Exception e) {
            logger.error("Failed to parse STK result payload", e);
        }
        return ResponseEntity.ok(Map.of("ResultCode", 0, "ResultDesc", "Accepted"));
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> handleAsyncResult(@RequestBody String payload) {
        try {
            AsyncCallbackResult result = objectMapper.readValue(payload, AsyncCallbackResult.class);
            AsyncCallbackResult.ResultBody body = result.getResult();
            if (body != null) {
                logger.info("Async result received — txn: {}, code: {}, desc: {}",
                        body.getTransactionId(), body.getResultCode(), body.getResultDesc());
            } else {
                logger.warn("Async result received with unexpected structure");
                logger.debug("Async result payload: {}", payload);
            }
        } catch (Exception e) {
            logger.error("Failed to parse async result payload", e);
        }
        return ResponseEntity.ok(Map.of("ResultCode", 0, "ResultDesc", "Accepted"));
    }

    @PostMapping("/timeout")
    public ResponseEntity<Map<String, Object>> handleTimeout(@RequestBody String payload) {
        try {
            AsyncCallbackResult result = objectMapper.readValue(payload, AsyncCallbackResult.class);
            AsyncCallbackResult.ResultBody body = result.getResult();
            if (body != null) {
                logger.warn("Queue timeout — txn: {}, code: {}, desc: {}",
                        body.getTransactionId(), body.getResultCode(), body.getResultDesc());
            } else {
                logger.warn("Queue timeout received with unexpected structure");
                logger.debug("Timeout payload: {}", payload);
            }
        } catch (Exception e) {
            logger.error("Failed to parse timeout payload", e);
        }
        return ResponseEntity.ok(Map.of("ResultCode", 0, "ResultDesc", "Accepted"));
    }
}
