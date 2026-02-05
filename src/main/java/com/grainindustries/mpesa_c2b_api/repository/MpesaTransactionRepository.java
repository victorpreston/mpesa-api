package com.grainindustries.mpesa_c2b_api.repository;

import com.grainindustries.mpesa_c2b_api.entity.MpesaTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MpesaTransactionRepository extends JpaRepository<MpesaTransaction, Long> {
    
    Optional<MpesaTransaction> findByTransactionId(String transactionId);
    
    Optional<MpesaTransaction> findByMpesaReceiptNumber(String mpesaReceiptNumber);
    
    List<MpesaTransaction> findByPhoneNumber(String phoneNumber);
    
    List<MpesaTransaction> findByBusinessShortcode(String businessShortcode);
    
    @Query("SELECT m FROM MpesaTransaction m WHERE m.createdAt BETWEEN :startDate AND :endDate")
    List<MpesaTransaction> findByDateRange(@Param("startDate") LocalDateTime startDate, 
                                           @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT m FROM MpesaTransaction m WHERE m.resultCode = :resultCode")
    List<MpesaTransaction> findByResultCode(@Param("resultCode") String resultCode);
}
