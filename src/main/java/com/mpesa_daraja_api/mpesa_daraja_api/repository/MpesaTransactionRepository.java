package com.mpesa_daraja_api.mpesa_daraja_api.repository;

import com.mpesa_daraja_api.mpesa_daraja_api.entity.MpesaTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MpesaTransactionRepository extends JpaRepository<MpesaTransaction, UUID> {
    
    Optional<MpesaTransaction> findByTransactionId(String transactionId);
    
    List<MpesaTransaction> findByMsisdn(String msisdn);
    
    List<MpesaTransaction> findByBusinessShortcode(String businessShortcode);
    
    @Query("SELECT m FROM MpesaTransaction m WHERE m.createdAt BETWEEN :startDate AND :endDate")
    List<MpesaTransaction> findByDateRange(@Param("startDate") LocalDateTime startDate, 
                                           @Param("endDate") LocalDateTime endDate);
}
